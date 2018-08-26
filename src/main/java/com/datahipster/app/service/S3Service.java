package com.datahipster.app.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

@Service
public class S3Service {

    private final static AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();

    public boolean put(String bucket, String prefix, File file){
        try{
            s3.putObject(bucket,prefix,file);
        }catch (AmazonServiceException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public InputStream getInputStream(String bucket, String prefix) {
        S3Object s3Object = s3.getObject(bucket,prefix);
        return s3Object.getObjectContent();
    }
}
