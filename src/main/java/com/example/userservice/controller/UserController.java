package com.example.userservice.controller;


import com.example.userservice.dtos.*;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.service.UserService;
import lombok.NonNull;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto loginDto){
        return userService.login(loginDto.getEmail(),loginDto.getPassword());
    }
    @PostMapping("/signup")
    public SignupResponseDto signUp(@RequestBody SignupRequestDto signupDto){

        User user =  userService.signUp(signupDto.getEmail(),
                            signupDto.getName(),
                            signupDto.getPassword());

        SignupResponseDto responseDto = new SignupResponseDto();
        responseDto.setEmail(user.getEmail());
        responseDto.setName(user.getName());
        return responseDto;
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto requestDto){
        userService.logout(requestDto.getToken());

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping("/validate/{token}")
    public ResponseEntity<UserDto> validate(@PathVariable("token")@NonNull String token){

        User returnedUser = userService.validate(token);
        if(returnedUser == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(UserDto.from(returnedUser),HttpStatus.OK);
    }
}
