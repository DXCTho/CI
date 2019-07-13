package com.dai.xmp.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    private void johnsUnusedCodeSmellMethod() {
       String buggy=null;
        buggy.trim();
        
        System.out.println("Foo!!!");
        
}
}
