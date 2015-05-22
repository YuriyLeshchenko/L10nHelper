package com.mycompany.l10nhelper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class LocalizationAnalyzer implements Serializable {

    @Inject
    private L10nHelper l10nHelper;
    private Map<String, List<LocalizationTableRow>> identicalValues;

    public void analyze() {
        
    }

    private void findIdenticalValues() {
        
    }
}
