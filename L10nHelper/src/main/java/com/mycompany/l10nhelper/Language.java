package com.mycompany.l10nhelper;

import java.util.Arrays;
import java.util.List;

public enum Language {

    EN(0),
    ES(1),
    RU(2),
    AR(3);
    public static final String ENGLISH_FILE_NAME = "locale-messages.properties";
    public static final String SPANISH_FILE_NAME = "locale-messages_es.properties";
    public static final String RUSSIAN_FILE_NAME = "locale-messages_ru.properties";
    public static final String ARABIC_FILE_NAME = "locale-messages_ar.properties";
    private final int id;

    private Language(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static List<Integer> getSupportedLanguages() {
        return Arrays.asList(EN.id, ES.id, RU.id, AR.id);
    }
}
