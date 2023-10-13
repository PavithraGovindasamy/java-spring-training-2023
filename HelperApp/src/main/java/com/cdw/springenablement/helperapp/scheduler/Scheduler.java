package com.cdw.springenablement.helperapp.scheduler;

import com.cdw.springenablement.helperapp.entity.BlackListToken;
import com.cdw.springenablement.helperapp.repository.BlackListTokenRepository;
import com.cdw.springenablement.helperapp.services.JwtService;
import com.cdw.springenablement.helperapp.services.JwtTokenUtil;
import com.cdw.springenablement.helperapp.services.TokenBlacklistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Slf4j
public class Scheduler {


 @Autowired
 private JwtService jwtService;

 @Autowired
 private BlackListTokenRepository blackListTokenRepository;



    @Scheduled(fixedDelay = 600000)
    public void scheduler() throws  InterruptedException{
        List<BlackListToken> tokenList=blackListTokenRepository.findAll();
        for (BlackListToken tokens : tokenList) {
            long remainingTime = jwtService.extractRemainingTime(String.valueOf(tokens.getToken()));
            if (remainingTime <= 0) {
                System.out.println("Token expired" + tokens);
                blackListTokenRepository.delete(tokens);
            }
        }
    }

}
