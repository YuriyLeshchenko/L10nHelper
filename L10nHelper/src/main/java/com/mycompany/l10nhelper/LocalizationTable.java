package com.mycompany.l10nhelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class LocalizationTable implements Serializable {

    private LanguageFile fileOfEnglish;
    private LanguageFile fileOfSpanish;
    private LanguageFile fileOfRussian;
    private LanguageFile fileOfArabic;
    private List<LanguageFile> languageFiles;
    private List<LocalizationTableRow> localizationTableRows;

    @PostConstruct
    private void init() {
        languageFiles = new ArrayList<>();
        localizationTableRows = new ArrayList<>();
    }

    public void buildLocalizationTable() {
        Set<Object> keys = new HashSet<>();
        int maxKeysCount = 0;
        for (LanguageFile languageFile : getLanguageFiles()) {
            if (languageFile.getLanguageFile().keySet().size() > maxKeysCount) {
                keys = languageFile.getLanguageFile().keySet();
                maxKeysCount = languageFile.getLanguageFile().keySet().size();
            }
        }
        localizationTableRows = new ArrayList<>();
        for (Object key : keys) {
            String keyAsString = key.toString();
            String enValue = fileOfEnglish.getLanguageFile().getProperty(keyAsString);
            String esValue = fileOfSpanish.getLanguageFile().getProperty(keyAsString);
            String ruValue = fileOfRussian.getLanguageFile().getProperty(keyAsString);
            String arValue = fileOfArabic.getLanguageFile().getProperty(keyAsString);
            localizationTableRows.add(new LocalizationTableRow(keyAsString, enValue, esValue, ruValue, arValue));
        }
    }

    public LanguageFile getLocalizationFileForLanguage(Language language) {
        switch (language) {
            case EN:
                return fileOfEnglish;
            case ES:
                return fileOfSpanish;
            case RU:
                return fileOfRussian;
            case AR:
                return fileOfRussian;
            default:
                return null;
        }
    }

    public void reset() {
        fileOfEnglish = null;
        fileOfSpanish = null;
        fileOfRussian = null;
        fileOfArabic = null;
        languageFiles = new ArrayList<>();
        localizationTableRows = new ArrayList<>();
    }

    public void setFileOfEnglish(LanguageFile fileOfEnglish) {
        this.fileOfEnglish = fileOfEnglish;
    }

    public void setFileOfSpanish(LanguageFile fileOfSpanish) {
        this.fileOfSpanish = fileOfSpanish;
    }

    public void setFileOfRussian(LanguageFile fileOfRussian) {
        this.fileOfRussian = fileOfRussian;
    }

    public void setFileOfArabic(LanguageFile fileOfArabic) {
        this.fileOfArabic = fileOfArabic;
    }

    public List<LanguageFile> getLanguageFiles() {
        languageFiles.clear();
        if (fileOfEnglish != null) {
            languageFiles.add(fileOfEnglish);
        }
        if (fileOfSpanish != null) {
            languageFiles.add(fileOfSpanish);
        }
        if (fileOfRussian != null) {
            languageFiles.add(fileOfRussian);
        }
        if (fileOfArabic != null) {
            languageFiles.add(fileOfArabic);
        }
        return languageFiles;
    }

    public List<LocalizationTableRow> getLocalizationTableRows() {
        return localizationTableRows;
    }
}
