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
    @GetMapping("/sample")
    public String sample()
    {
        return "sampleChangeForgit";
    }
    @GetMapping("/helloOne")
    public String sayHelloTwo()
    {
        return "Hi welcome to springboot devloper ";
    }
     
}
