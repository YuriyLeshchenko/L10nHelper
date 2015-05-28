package com.mycompany.l10nhelper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Named
@SessionScoped
public class FileManager implements Serializable {

    @Inject
    private LocalizationTable localizationTable;
    private StreamedContent englishFile;
    private StreamedContent spanishFile;
    private StreamedContent russianFile;
    private StreamedContent arabicFile;

    private StreamedContent prepareFileToDownload(String fileName, Properties languageFile) {
        SortedMap sortedPropertiesMap = new TreeMap(languageFile);
        StringBuilder content = new StringBuilder();
        content.append("# Last date sorting: ").append(new Date()).append('\n');
        for (Object key : sortedPropertiesMap.keySet()) {
            content.append(key).append("=").append(sortedPropertiesMap.get(key)).append('\n');
        }
        InputStream stream = new ByteArrayInputStream(content.toString().getBytes());
        StreamedContent file = new DefaultStreamedContent(stream, "text/plain", fileName);
        return file;
    }

    public StreamedContent getEnglishFile() {
        englishFile = prepareFileToDownload(Language.ENGLISH_FILE_NAME, localizationTable.getFileOfEnglish().getLanguageFile());
        return englishFile;
    }

    public StreamedContent getSpanishFile() {
        spanishFile = prepareFileToDownload(Language.SPANISH_FILE_NAME, localizationTable.getFileOfSpanish().getLanguageFile());
        return spanishFile;
    }

    public StreamedContent getRussianFile() {
        russianFile = prepareFileToDownload(Language.RUSSIAN_FILE_NAME, localizationTable.getFileOfRussian().getLanguageFile());
        return russianFile;
    }

    public StreamedContent getArabicFile() {
        arabicFile = prepareFileToDownload(Language.ARABIC_FILE_NAME, localizationTable.getFileOfArabic().getLanguageFile());
        return arabicFile;
    }
}
