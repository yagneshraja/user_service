package com.example.userservice.service;

import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.repositories.TokenRepository;
import com.example.userservice.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService{

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private TokenRepository tokenRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }

    public Token login(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            return null;
        }
        User user = optionalUser.get();
        if(!bCryptPasswordEncoder.matches(password, user.getHashedPassword())){
            // TODO :  throw exception user or password not matched
            return null;
        }

        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plusDays(30);

        // Convert LocalDate to jaa.util.Date
         Date date = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Token token = new Token();
        token.setUser(user);
        token.setExpiryAt(date);
        token.setToken(RandomStringUtils.randomAlphanumeric(128));
        token.setDeleted(false);

        Token savedToken = tokenRepository.save(token);

        return savedToken;


    }

    public User signUp(String email, String name, String password) {
        var newUser = new User();
        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setHashedPassword(bCryptPasswordEncoder.encode(password));

        User saveNewUser = userRepository.save(newUser);

        if(saveNewUser != null){
            return saveNewUser;
        }
        return null;
    }

    public void logout(String tkn) {
        Optional<Token> token = tokenRepository.findByTokenAndDeletedEquals(tkn, false);
        if(token.isEmpty())
        {
            return;
        }
        Token deletedToken = token.get();
        deletedToken.setDeleted(true);

        tokenRepository.save(deletedToken);
        return;
    }

    public User validate(String token){
        Optional<Token> optionalToken = tokenRepository.findByTokenAndDeletedEquals(token, false);
        if(optionalToken.isEmpty()){
            return null;
        }
        Token token1 = optionalToken.get();
        return token1.getUser();
    }
}
