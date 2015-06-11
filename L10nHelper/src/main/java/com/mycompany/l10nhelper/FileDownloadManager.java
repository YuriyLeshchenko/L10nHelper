package com.mycompany.l10nhelper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

@Named
@SessionScoped
public class FileDownloadManager implements Serializable {

    @Inject
    private LocalizationTable localizationTable;
    private StreamedContent englishFile;
    private StreamedContent spanishFile;
    private StreamedContent russianFile;
    private StreamedContent arabicFile;
    private StreamedContent englishFileWithoutUnusedKeys;
    private StreamedContent spanishFileWithoutUnusedKeys;
    private StreamedContent russianFileWithoutUnusedKeys;
    private StreamedContent arabicFileWithoutUnusedKeys;

    private StreamedContent prepareSortedFile(String fileName, UploadedFile uploadedFile) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(uploadedFile.getInputstream()));
            Set<String> sortedSet = new TreeSet<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sortedSet.add(line);
            }

            StringBuilder content = new StringBuilder();
            for (String keyValueString : sortedSet) {
                content.append(keyValueString).append('\n');
            }
            InputStream stream = new ByteArrayInputStream(content.toString().getBytes());
            StreamedContent file = new DefaultStreamedContent(stream, "text/plain", fileName);
            return file;
        } catch (IOException ex) {
            System.out.println("Error in prepareSortedFile();");
        }
        return null;
    }

    public void removeUnusedKeys(List<LocalizationTableRow> foundRows) {
        Set<String> unusedKeys = new HashSet<>();
        for (LocalizationTableRow row : foundRows) {
            unusedKeys.add(row.getKey());
        }
        englishFileWithoutUnusedKeys = prepareFilesWithoutUnusedKeys(Language.ENGLISH_FILE_NAME, localizationTable.getFileOfEnglish().getUploadedFile(), unusedKeys);
        spanishFileWithoutUnusedKeys = prepareFilesWithoutUnusedKeys(Language.SPANISH_FILE_NAME, localizationTable.getFileOfSpanish().getUploadedFile(), unusedKeys);
        russianFileWithoutUnusedKeys = prepareFilesWithoutUnusedKeys(Language.RUSSIAN_FILE_NAME, localizationTable.getFileOfRussian().getUploadedFile(), unusedKeys);
        arabicFileWithoutUnusedKeys = prepareFilesWithoutUnusedKeys(Language.ARABIC_FILE_NAME, localizationTable.getFileOfArabic().getUploadedFile(), unusedKeys);
    }

    private StreamedContent prepareFilesWithoutUnusedKeys(String fileName, UploadedFile uploadedFile, Set<String> unusedKeys) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(uploadedFile.getInputstream()));
            Set<String> sortedSet = new TreeSet<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                int index = line.indexOf("=");
                if (index != -1) {
                    String key = line.substring(0, index);
                    if (!unusedKeys.contains(key)) {
                        sortedSet.add(line);
                    }
                }
            }

            StringBuilder content = new StringBuilder();
            for (String keyValueString : sortedSet) {
                content.append(keyValueString).append('\n');
            }
            InputStream stream = new ByteArrayInputStream(content.toString().getBytes());
            StreamedContent file = new DefaultStreamedContent(stream, "text/plain", fileName);
            return file;
        } catch (IOException ex) {
            System.out.println("Error in prepareFilesWithoutUnusedKeys();");
        }
        return null;
    }

    public StreamedContent getEnglishFile() {
        englishFile = prepareSortedFile(Language.ENGLISH_FILE_NAME, localizationTable.getFileOfEnglish().getUploadedFile());
        return englishFile;
    }

    public StreamedContent getSpanishFile() {
        spanishFile = prepareSortedFile(Language.SPANISH_FILE_NAME, localizationTable.getFileOfSpanish().getUploadedFile());
        return spanishFile;
    }

    public StreamedContent getRussianFile() {
        russianFile = prepareSortedFile(Language.RUSSIAN_FILE_NAME, localizationTable.getFileOfRussian().getUploadedFile());
        return russianFile;
    }

    public StreamedContent getArabicFile() {
        arabicFile = prepareSortedFile(Language.ARABIC_FILE_NAME, localizationTable.getFileOfArabic().getUploadedFile());
        return arabicFile;
    }

    public StreamedContent getEnglishFileWithoutUnusedKeys() {
        return englishFileWithoutUnusedKeys;
    }

    public StreamedContent getSpanishFileWithoutUnusedKeys() {
        return spanishFileWithoutUnusedKeys;
    }

    public StreamedContent getRussianFileWithoutUnusedKeys() {
        return russianFileWithoutUnusedKeys;
    }

    public StreamedContent getArabicFileWithoutUnusedKeys() {
        return arabicFileWithoutUnusedKeys;
    }
}
