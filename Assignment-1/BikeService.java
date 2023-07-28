package com.example.demo;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("bikeService")
public class BikeService implements AutoMobile{
    public  BikeService(){
        System.out.println("HEY BIKE SERVICE CONSTRUCROR");
    }
    @Override
    public String minorService() {
        return "he i am mini in bike";
    }

    @Override
    public String majorService() {
        return "hey hi major in bike";
    }
}
