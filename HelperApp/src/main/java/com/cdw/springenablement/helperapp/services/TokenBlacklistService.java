package com.cdw.springenablement.helperapp.services;

import com.cdw.springenablement.helperapp.entity.BlackListToken;
import com.cdw.springenablement.helperapp.repository.BlackListTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class that handles to blacklist the token and to check whether a token is blacklisted
 *
 * @Author pavithra
 */

@Service
@Transactional
public class TokenBlacklistService {

    @Autowired
    private BlackListTokenRepository blackListTokenRepository;

    /**
     * Method which adds the token to blacklist
     *
     * @param token
     */
    public void addToBlacklist(String token) {
        BlackListToken blackListToken = new BlackListToken();
        blackListToken.setToken(token);
        blackListTokenRepository.save(blackListToken);
    }


    /**
     * Method which checks whether a token is blacklisted or not
     *
     * @param token
     * @return boolean value
     */

    public boolean isTokenBlacklisted(String token) {
        BlackListToken blackListToken = blackListTokenRepository.findByToken(token);
        return blackListToken != null;
    }
}

