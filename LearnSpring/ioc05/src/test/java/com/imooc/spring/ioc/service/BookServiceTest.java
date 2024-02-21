package com.imooc.spring.ioc.service;

import com.imooc.spring.ioc.SpringApplicationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import static org.junit.Assert.*;

public class BookServiceTest extends SpringApplicationTest {


    @Value("${metaData}")
    private String metaData;



    @Autowired
    BookService bookService;

    @Test
    public void init() {
        System.out.println(metaData);
    }
}