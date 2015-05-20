package com.mycompany.l10nhelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    private List<UploadedFile> uploadedFiles;
    private List<List<Row>> localizationFiles;

    @PostConstruct
    private void init() {
        uploadedFiles = new ArrayList<>(FILES_LIMIT);
        localizationFiles = new ArrayList<>(FILES_LIMIT);
    }

    public void uploadFile(FileUploadEvent event) {
        uploadedFiles.add(event.getFile());
        FacesMessage message = new FacesMessage("Files have been successfully uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public void processFiles() {
        for (UploadedFile file : uploadedFiles) {
            localizationFiles.add(getDataFromUploadedFile(file));
        }
    }

    public void removeFiles() {
        uploadedFiles.clear();
    }

    public static List<Row> getDataFromUploadedFile(UploadedFile file) {
        List<Row> rows = new ArrayList<>(0);
        return rows;
    }

    public int getFilesLimit() {
        return FILES_LIMIT;
    }

    public List<UploadedFile> getUploadedFiles() {
        return uploadedFiles;
    }
}
