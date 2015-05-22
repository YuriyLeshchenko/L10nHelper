package com.mycompany.l10nhelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@Named
@SessionScoped
public class L10nHelper implements Serializable {

    private final int FILES_LIMIT = 3;
    private final String ENGLISH_FILE_NAME = "locale-messages.properties";
    private final String SPANISH_FILE_NAME = "locale-messages_es.properties";
    private final String RUSSIAN_FILE_NAME = "locale-messages_ru.properties";
    private List<UploadedFile> uploadedFiles;
    private List<LocalizationTableRow> filteredRows;
    @Inject
    private LocalizationTable localizationTable;

    @PostConstruct
    private void init() {
        uploadedFiles = new ArrayList<>(FILES_LIMIT);
    }

    public void uploadFile(FileUploadEvent event) {
        uploadedFiles.add(event.getFile());
        if (uploadedFiles.size() == FILES_LIMIT) {
            processFiles();
            FacesMessage message = new FacesMessage("Files have been successfully uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void processFiles() {
        for (UploadedFile file : uploadedFiles) {
            recognizeLanguageFile(file);
        }
        localizationTable.buildLocalizationTable();
    }

    public void removeFiles() {
        uploadedFiles.clear();
        localizationTable.reset();
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

    private void recognizeLanguageFile(UploadedFile file) {
        String fileName = file.getFileName();
        Properties propFile = doPropertyFile(file);
        if (fileName.equals(ENGLISH_FILE_NAME)) {
            localizationTable.setFileOfEnglish(new LanguageFile(propFile, file.getFileName(), file.getSize(), propFile.keySet().size(), Language.EN));
            return;
        }
        if (fileName.equals(SPANISH_FILE_NAME)) {
            localizationTable.setFileOfSpanish(new LanguageFile(propFile, file.getFileName(), file.getSize(), propFile.keySet().size(), Language.ES));
            return;
        }
        if (fileName.equals(RUSSIAN_FILE_NAME)) {
            localizationTable.setFileOfRussian(new LanguageFile(propFile, file.getFileName(), file.getSize(), propFile.keySet().size(), Language.RU));
        }
    }

    public int getFilesLimit() {
        return FILES_LIMIT;
    }

    public List<UploadedFile> getUploadedFiles() {
        return uploadedFiles;
    }

    public List<LocalizationTableRow> getFilteredRows() {
        return filteredRows;
    }

    public void setFilteredRows(List<LocalizationTableRow> filteredRows) {
        this.filteredRows = filteredRows;
    }
}
