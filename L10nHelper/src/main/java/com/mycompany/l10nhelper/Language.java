package com.mycompany.l10nhelper;

import java.util.Arrays;
import java.util.List;

public enum Language {

    EN(0),
    ES(1),
    RU(2),
    AR(3);
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
