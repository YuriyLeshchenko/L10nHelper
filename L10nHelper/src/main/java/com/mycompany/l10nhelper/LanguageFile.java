package com.mycompany.l10nhelper;

import java.util.Properties;

public class LanguageFile {

    private final Properties languageFile;
    private final String fileName;
    private final long fileSize;
    private final int keysCount;
    private final Language language;

    public LanguageFile(Properties languageFile, String fileName, long fileSize, int keysCount, Language language) {
        this.languageFile = languageFile;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.keysCount = keysCount;
        this.language = language;
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
