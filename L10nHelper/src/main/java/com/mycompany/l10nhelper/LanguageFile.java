package com.mycompany.l10nhelper;

import java.util.Properties;
import org.primefaces.model.UploadedFile;

public class LanguageFile {

    private final UploadedFile uploadedFile;
    private final Properties languageFile;
    private final String fileName;
    private final long fileSize;
    private final int keysCount;
    private final Language language;

    public LanguageFile(UploadedFile uploadedFile, Properties languageFile, String fileName, long fileSize, int keysCount, Language language) {
        this.uploadedFile = uploadedFile;
        this.languageFile = languageFile;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.keysCount = keysCount;
        this.language = language;
    }

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public Properties getLanguageFile() {
        return languageFile;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public int getKeysCount() {
        return keysCount;
    }

    public Language getLanguage() {
        return language;
    }
}
