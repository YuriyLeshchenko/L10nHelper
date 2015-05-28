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

    private final int FILES_LIMIT = 4;
    private List<UploadedFile> uploadedFiles;
    private List<LocalizationTableRow> filteredRows;
    @Inject
    private LocalizationTable localizationTable;
    @Inject
    private LocalizationAnalyzer localizationAnalyzer;

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
        localizationAnalyzer.resetAnalyzedData();
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
        if (fileName.equals(Language.ENGLISH_FILE_NAME)) {
            localizationTable.setFileOfEnglish(new LanguageFile(file, propFile, file.getFileName(), file.getSize(), propFile.keySet().size(), Language.EN));
            return;
        }
        if (fileName.equals(Language.SPANISH_FILE_NAME)) {
            localizationTable.setFileOfSpanish(new LanguageFile(file, propFile, file.getFileName(), file.getSize(), propFile.keySet().size(), Language.ES));
            return;
        }
        if (fileName.equals(Language.RUSSIAN_FILE_NAME)) {
            localizationTable.setFileOfRussian(new LanguageFile(file, propFile, file.getFileName(), file.getSize(), propFile.keySet().size(), Language.RU));
        }
        if (fileName.equals(Language.ARABIC_FILE_NAME)) {
            localizationTable.setFileOfArabic(new LanguageFile(file, propFile, file.getFileName(), file.getSize(), propFile.keySet().size(), Language.RU));
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
