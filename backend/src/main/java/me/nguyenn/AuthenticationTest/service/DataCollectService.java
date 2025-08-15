package me.nguyenn.AuthenticationTest.service;

import org.springframework.stereotype.Service;

@Service
public class DataCollectService {
    private static final String sampleData = "My name is Winston!";
    
    public String getData() {
        return sampleData;
    }

}
