package com.example.demo.rest;

//spring then proceeds to inject the beans using the chosen injection methods, which means that the setter injection can override the constructor injection if both are present and matching beans are
import com.example.demo.AutoMobile;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
@Component("restController")
public class RestController {
    private AutoMobile autoMobile;

    // constructor to inject the bean
    public RestController(AutoMobile automobile) {
        autoMobile = automobile;
    }
    // for hitting the API
    @GetMapping("/bikeService")
    public String hello(){
        System.out.println("heu"+autoMobile.minorService());
        System.out.println("hello"+autoMobile.majorService());
        System.out.println("HELLO BEAN CREATED");
      return autoMobile.minorService();   // xml based injection using constructor injection
    }


}
