package com.cdw.springenablement.helper_App.services;
import com.cdw.springenablement.helper_App.entity.BlackListToken;
import com.cdw.springenablement.helper_App.repository.BlackListTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class TokenBlacklistService {

    @Autowired
    private BlackListTokenRepository blackListTokenRepository;


    public void addToBlacklist(String token) {
        BlackListToken blackListToken = new BlackListToken();
        blackListToken.setToken(token);
        System.out.println("baj"+"token came"+"hsj");
        blackListTokenRepository.save(blackListToken);

    }

    public boolean isTokenBlacklisted(String token) {
        BlackListToken blackListToken = blackListTokenRepository.findByToken(token);
        return blackListToken != null;
    }
}

