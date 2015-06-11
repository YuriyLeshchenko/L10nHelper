package com.mycompany.l10nhelper;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class LocalizationAnalyzer implements Serializable {

    @Inject
    private LocalizationTable localizationTable;
    private Language selectedLanguage = Language.EN;
    private SearchType selectedSearchType = SearchType.IDENTITIES;
    private Map<String, List<LocalizationTableRow>> identicalValues = new HashMap<>();
    private List<LocalizationTableRow> filteredIdenticalValues;
    private List<LocalizationTableRow> foundRows;
    private final String pathToProject = "/home/yuriy/work/mcweb/src";
    private boolean isUsedKey;

    public void analyze() {
        LanguageFile languageFile = localizationTable.getLocalizationFileForLanguage(selectedLanguage);
        switch (selectedSearchType) {
            case IDENTITIES:
                findIdenticalValues();
                break;
            case SPACES:
                searchSpaces();
                break;
            case UNUSED_KEYS:
                findUnusedKeys();
        }
    }

    private void findIdenticalValues() {
        identicalValues = new HashMap<>();
        List<LocalizationTableRow> rows = localizationTable.getLocalizationTableRows();
        int rowsCount = rows.size();
        for (int rowNum = 0; rowNum < rowsCount; rowNum++) {
            LocalizationTableRow row = rows.get(rowNum);
            String comparableValue = row.getValueByLanguage(selectedLanguage);
            if (comparableValue == null) {
                continue;
            }
            if (identicalValues.containsKey(comparableValue.toLowerCase())) {
                continue;
            }
            List<LocalizationTableRow> equalValues = new ArrayList<>();
            boolean sameExists = false;
            for (int v = rowNum + 1; v < rowsCount; v++) {
                String toCompare = rows.get(v).getValueByLanguage(selectedLanguage);
                if (toCompare == null) {
                    continue;
                }
                if (comparableValue.toLowerCase().equals(toCompare.toLowerCase())) {
                    if (!sameExists) {
                        equalValues.add(row);
                        sameExists = true;
                    }
                    equalValues.add(rows.get(v));
                }
            }
            if (!equalValues.isEmpty()) {
                identicalValues.put(comparableValue.toLowerCase(), equalValues);
            }
        }
    }

    private void searchSpaces() {
        foundRows = new ArrayList<>();
        for (LocalizationTableRow row : localizationTable.getLocalizationTableRows()) {
            String comparableValue = row.getValueByLanguage(selectedLanguage);
            if (comparableValue != null
                    && (comparableValue.contains("  ") || comparableValue.startsWith(" ") || comparableValue.endsWith(" "))) {
                String enValue = null;
                String esValue = null;
                String ruValue = null;
                String arValue = null;
                switch (selectedLanguage) {
                    case EN:
                        enValue = row.getEnValue();
                    case ES:
                        esValue = row.getEsValue();
                    case RU:
                        ruValue = row.getRuValue();
                    case AR:
                        arValue = row.getArValue();
                }
                foundRows.add(new LocalizationTableRow(row.getKey(), enValue, esValue, ruValue, arValue));
            }
        }
    }

    private void findUnusedKeys() {
        foundRows = new ArrayList<>();
        for (LocalizationTableRow row : localizationTable.getLocalizationTableRows()) {
            if (!row.getKey().contains(".") && !row.getKey().startsWith("menu_") && !row.getKey().startsWith("email_")) {
                try {
                    isUsedKey = false;
                    searchUnusedKeys(pathToProject, row.getKey());
                    if (!isUsedKey) {
                        foundRows.add(row);
                        System.out.println(row.getKey());
                    }
                } catch (IOException ex) {
                    System.out.println("Error in findUnusedKeys();");
                }
            }
        }
    }

    private void searchUnusedKeys(String path, String key) throws IOException {
        File mainDir = new File(path);
        File[] list = mainDir.listFiles();
        if (list != null) {
            for (File f : list) {
                if (f.isFile() && (f.getName().endsWith(".java") || f.getName().endsWith(".xhtml"))) {
                    String fileContent = new String(Files.readAllBytes(Paths.get(f.getCanonicalPath())), "UTF-8");
                    if (fileContent.contains(key)) {
                        isUsedKey = true;
                    }
                } else {
                    searchUnusedKeys(f.getCanonicalPath(), key);
                }
                if (isUsedKey) {
                    return;
                }
            }
        }
    }

    public List<String> getIdenticalValuesKeys() {
        Set<String> sortedSet = new TreeSet<>(identicalValues.keySet());
        return new ArrayList<>(sortedSet);
    }

    public void resetAnalyzedData() {
        identicalValues = identicalValues = new HashMap<>();
        filteredIdenticalValues = null;
        foundRows = null;
    }

    public List<LocalizationTableRow> getRowByKey(String key) {
        return identicalValues.get(key);
    }

    public Language getSelectedLanguage() {
        return selectedLanguage;
    }

    public void setSelectedLanguage(Language selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
    }

    public SearchType getSelectedSearchType() {
        return selectedSearchType;
    }

    public void setSelectedSearchType(SearchType selectedSearchType) {
        this.selectedSearchType = selectedSearchType;
    }

    public Map<String, List<LocalizationTableRow>> getIdenticalValues() {
        return identicalValues;
    }

    public List<LocalizationTableRow> getFilteredIdenticalValues() {
        return filteredIdenticalValues;
    }

    public void setFilteredIdenticalValues(List<LocalizationTableRow> filteredIdenticalValues) {
        this.filteredIdenticalValues = filteredIdenticalValues;
    }

    public List<LocalizationTableRow> getFoundRows() {
        return foundRows;
    }
}
