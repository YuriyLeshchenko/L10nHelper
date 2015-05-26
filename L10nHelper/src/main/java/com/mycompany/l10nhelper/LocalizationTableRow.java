package com.mycompany.l10nhelper;

public class LocalizationTableRow {

    private String key;
    private String enValue;
    private String esValue;
    private String ruValue;
    private String arValue;

    public LocalizationTableRow(String key, String enValue, String esValue, String ruValue, String arValue) {
        this.key = key;
        this.enValue = enValue;
        this.esValue = esValue;
        this.ruValue = ruValue;
        this.arValue = arValue;
    }

    public String getValueByLanguage(Language lang) {
        switch (lang) {
            case EN:
                return enValue;
            case ES:
                return esValue;
            case RU:
                return ruValue;
            case AR:
                return arValue;
            default:
                return null;
        }
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

    public String getArValue() {
        return arValue;
    }

    public void setArValue(String arValue) {
        this.arValue = arValue;
    }
}
