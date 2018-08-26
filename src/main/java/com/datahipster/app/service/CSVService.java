package com.datahipster.app.service;

import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class CSVService {

    private static final String tempDir = System.getProperty("java.io.tmpdir");

    public String createCsv(String[] header, List<String[]> rows, String fileName) throws IOException {
        File dir = new File(tempDir);
        File fileObj = File.createTempFile(fileName, ".csv", dir);
        FileWriter fileWriter = new FileWriter(fileObj, true);
        CSVWriter csvWriter = new CSVWriter(fileWriter, ',', '"');
        csvWriter.writeNext(header);
        csvWriter.writeAll(rows);
        csvWriter.close();
        return fileObj.getAbsolutePath();
    }
}
