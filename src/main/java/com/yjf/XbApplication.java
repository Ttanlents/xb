package com.yjf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class XbApplication {

    public static void main(String[] args) {
        SpringApplication.run(XbApplication.class, args);
    }

}
