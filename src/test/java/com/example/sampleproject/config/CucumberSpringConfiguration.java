package com.example.sampleproject.config;

import com.example.sampleproject.SampleProjectApplication;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = SampleProjectApplication.class)
public class CucumberSpringConfiguration {

    public CucumberSpringConfiguration() {
        System.out.println("CucumberSpringConfiguration initialized");
    }
}
