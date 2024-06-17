package com.example.sampleproject.init;

import com.example.sampleproject.service.impl.DataLoaderService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final DataLoaderService dataLoaderService;

    public DataInitializer(DataLoaderService dataLoaderService) {
        this.dataLoaderService = dataLoaderService;
    }


    @Override
    public void run(String... args) throws Exception {
        dataLoaderService.loadData();
    }
}
