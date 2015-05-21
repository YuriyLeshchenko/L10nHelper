package com.mycompany.l10nhelper;

import java.util.Properties;

public class LanguageFile {

    private final Properties languageFile;
    private final Language language;

    public LanguageFile(Properties languageFile, Language language) {
        this.languageFile = languageFile;
        this.language = language;
    }

    public Properties getLanguageFile() {
        return languageFile;
    }

    public Language getLanguage() {
        return language;
    }
}
