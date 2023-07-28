package com.example.demo;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
//pojo-plain old java object
// qualifier can over-ride the primary if we want
// giving importance to this rather than other class can be only one

@Component("carService")
public class CarService implements AutoMobile {
    public  CarService(){
      System.out.println("HEY CAR SERVICE CONSTRUCROR");
    }

    @Override
    public String minorService() {
        return "he i am mini in car";
    }

    @Override
    public String majorService() {
        return "You are bad in car";
    }
}
