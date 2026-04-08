package com.example.springbootdemotwo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController
{
     @GetMapping("/hello")
    public String sayHello()
     {
             return "Hi welcome to springboot devloper ";
         }
     
}
