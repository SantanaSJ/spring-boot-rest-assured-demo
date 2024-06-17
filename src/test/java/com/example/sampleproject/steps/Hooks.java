package com.example.sampleproject.steps;

import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.filter.log.ErrorLoggingFilter;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

public class Hooks {

    @Before
    public static void beforeAll() {
        RestAssured.baseURI = "http://localhost:8080";
//
//        RestAssured.config = RestAssured.config()
//                .logConfig(LogConfig.logConfig()
//                        .enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL)
//                        .enablePrettyPrinting(true));

        RestAssured.filters(
                new RequestLoggingFilter(LogDetail.ALL),
                new ResponseLoggingFilter(LogDetail.ALL),
                new ErrorLoggingFilter());
    }


}
