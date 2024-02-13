package com.example.userservice.dtos;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter(AccessLevel.NONE)
public class SignupRequestDto {
    private String email;
    private String name;
    private String password;
}
