package com.mycompany.l10nhelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named
@SessionScoped
public class FileUploader implements Serializable {

    private final int FILES_LIMIT = 3;
    private final String ENGLISH_FILE_NAME = "locale-messages.properties";
    private final String SPANISH_FILE_NAME = "locale-messages_es.properties";
    private final String RUSSIAN_FILE_NAME = "locale-messages_ru.properties";
    private List<UploadedFile> uploadedFiles;
    private List<LanguageFile> languageFiles;
    private List<LocalizationTableRow> localizationTable;
    private List<LocalizationTableRow> filteredRows;
    private Properties fileOfEnglish;
    private Properties fileOfSpanish;
    private Properties fileOfRussian;

    @PostConstruct
    private void init() {
        uploadedFiles = new ArrayList<>(FILES_LIMIT);
        languageFiles = new ArrayList<>(FILES_LIMIT);
    }

    public void uploadFile(FileUploadEvent event) {
        uploadedFiles.add(event.getFile());
        FacesMessage message = new FacesMessage("Files have been successfully uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void processFiles() {
        for (UploadedFile file : uploadedFiles) {
            recognizeLanguageFile(file);
        }
        buildLocalizationTable();
    }

    public void removeFiles() {
        uploadedFiles.clear();
        languageFiles.clear();
        localizationTable.clear();
    }

    public static Properties doPropertyFile(UploadedFile file) {
        Properties l10nFile = new Properties();
        try (InputStream is = file.getInputstream()) {
            l10nFile.load(is);
        } catch (IOException ex) {
            System.out.println("Error reading input stream");
        }
        return l10nFile;
    }

    private void buildLocalizationTable() {
        Set<Object> keys = new HashSet<>();
        int maxKeysCount = 0;
        for (LanguageFile languageFile : languageFiles) {
            if (languageFile.getLanguageFile().keySet().size() > maxKeysCount) {
                keys = languageFile.getLanguageFile().keySet();
                maxKeysCount = languageFile.getLanguageFile().keySet().size();
            }
        }
        localizationTable = new ArrayList<>();
        for (Object key : keys) {
            String keyAsString = key.toString();
            String enValue = fileOfEnglish.getProperty(keyAsString);
            String esValue = fileOfSpanish.getProperty(keyAsString);
            String ruValue = fileOfRussian.getProperty(keyAsString);
            localizationTable.add(new LocalizationTableRow(keyAsString, enValue, esValue, ruValue));
        }
    }

    private void recognizeLanguageFile(UploadedFile file) {
        String fileName = file.getFileName();
        if (fileName.equals(ENGLISH_FILE_NAME)) {
            fileOfEnglish = doPropertyFile(file);
            languageFiles.add(new LanguageFile(fileOfEnglish, Language.EN));
            return;
        }
        if (fileName.equals(SPANISH_FILE_NAME)) {
            fileOfSpanish = doPropertyFile(file);
            languageFiles.add(new LanguageFile(fileOfSpanish, Language.EN));
            return;
        }
        if (fileName.equals(RUSSIAN_FILE_NAME)) {
            fileOfRussian = doPropertyFile(file);
            languageFiles.add(new LanguageFile(fileOfRussian, Language.EN));
        }
    }

    public int getFilesLimit() {
        return FILES_LIMIT;
    }

    public List<UploadedFile> getUploadedFiles() {
        return uploadedFiles;
    }

    public List<LanguageFile> getLanguageFiles() {
        return languageFiles;
    }

    public List<LocalizationTableRow> getLocalizationTable() {
        return localizationTable;
    }

    public List<LocalizationTableRow> getFilteredRows() {
        return filteredRows;
    }

    public void setFilteredRows(List<LocalizationTableRow> filteredRows) {
        this.filteredRows = filteredRows;
    }
}
