package com.datahipster.app.service;

import com.datahipster.app.service.dto.HipsterRowCallBackHandler;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class CSVService {

    private static final String tempDir = System.getProperty("java.io.tmpdir");

    public String createCsv(HipsterRowCallBackHandler handler, String fileName) throws IOException {
        String[] header = handler.getHeader();
        File dir = new File(tempDir);
        File fileObj = File.createTempFile(fileName, ".csv", dir);

        FileWriter fileWriter = new FileWriter(fileObj, true);
        CSVWriter csvWriter = new CSVWriter(fileWriter, ',', '"');
        csvWriter.writeNext(header);

        List<Map<String,Object>> rows = handler.getDataResultSetContents();
        Map<String,Object> columnMetadata = handler.getResultSetColumnNames();

        for(Map<String,Object> row : rows){
            String[] values = new String[row.size()];

            for(int i = 0; i < header.length;i++){
                Object type = columnMetadata.get(header[i]);
                if(ClassUtils.isAssignable(type.getClass(),Float.class)){
                    values[i] = Float.toString((Float)row.get(header[i]));
                }else if(ClassUtils.isAssignable(type.getClass(),Integer.class)) {
                    values[i] = Integer.toString((Integer)row.get(header[i]));
                }else if(ClassUtils.isAssignable(type.getClass(),Long.class)) {
                    values[i] = Long.toString((Long) row.get(header[i]));
                }else if(ClassUtils.isAssignable(type.getClass(),Boolean.class)) {
                    values[i] = Boolean.toString((Boolean) row.get(header[i]));
                }else {
                    values[i] = (String)row.get(header[i]);
                }
            }
            csvWriter.writeNext(values);
        }
        csvWriter.close();
        return fileObj.getAbsolutePath();
    }
}
