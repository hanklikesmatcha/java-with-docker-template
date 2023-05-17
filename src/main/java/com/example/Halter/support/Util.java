package com.example.JavaWithDocker.support;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.JavaWithDocker.JavaWithDockerApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {
    private static final Logger log = LoggerFactory.getLogger(JavaWithDockerApplication.class);
    
    public static Date parseTimestamp(String timestamp) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
            Date parsedDate = dateFormat.parse(timestamp);
            return parsedDate;
        } catch (ParseException e) {
            log.info("failed to parse the timestamp");
            return (Date) null;
        }
    }
}
