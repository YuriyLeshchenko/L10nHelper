package com.mycompany.l10nhelper;

public enum Language {

    EN(0),
    ES(1),
    RU(2);
    private final int id;

    private Language(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
