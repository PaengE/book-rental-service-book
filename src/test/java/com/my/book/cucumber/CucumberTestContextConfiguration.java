package com.my.book.cucumber;

import com.my.book.BookApp;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = BookApp.class)
@WebAppConfiguration
public class CucumberTestContextConfiguration {}
