package com.mycompany.l10nhelper;

public class LocalizationTableRow {

    private String key;
    private String enValue;
    private String esValue;
    private String ruValue;

    public LocalizationTableRow(String key, String enValue, String esValue, String ruValue) {
        this.key = key;
        this.enValue = enValue;
        this.esValue = esValue;
        this.ruValue = ruValue;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEnValue() {
        return enValue;
    }

    public void setEnValue(String enValue) {
        this.enValue = enValue;
    }

    public String getEsValue() {
        return esValue;
    }

    public void setEsValue(String esValue) {
        this.esValue = esValue;
    }

    public String getRuValue() {
        return ruValue;
    }

    public void setRuValue(String ruValue) {
        this.ruValue = ruValue;
    }
}
