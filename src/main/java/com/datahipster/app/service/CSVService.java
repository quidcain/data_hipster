package com.datahipster.app.service;

import com.datahipster.app.service.dto.HipsterRowCallBackHandler;
import com.opencsv.CSVWriter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
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
        Map<String,Class> columnMetadata = handler.getResultSetColumnNames();

        for(Map<String,Object> row : rows){
            String[] values = new String[row.size()];

            for(int i = 0; i < header.length;i++){
                Class type = columnMetadata.get(header[i]);
                String typeName = type.getName();
                if(typeName.equalsIgnoreCase("java.lang.Float")){
                    values[i] = Float.toString((Float)row.get(header[i]));
                }else if(typeName.equalsIgnoreCase("java.lang.Integer")) {
                    values[i] = Integer.toString((Integer)row.get(header[i]));
                }else if(typeName.equalsIgnoreCase("java.lang.Long")) {
                    values[i] = Long.toString((Long) row.get(header[i]));
                }else if(typeName.equalsIgnoreCase("java.lang.Boolean")) {
                    values[i] = Boolean.toString((Boolean) row.get(header[i]));
                }else if(typeName.equalsIgnoreCase("java.sql.Date")) {
                    Date date = (Date) row.get(header[i]);
                    if(date != null){
                        values[i] = date.toString();
                    }else{
                        values[i] = "";
                    }

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
