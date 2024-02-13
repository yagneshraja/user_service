package com.example.userservice.dtos;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
public class LoginRequestDto {
    private String email;
    private String password;
}
