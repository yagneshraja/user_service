package com.example.userservice.dtos;

import com.example.userservice.models.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
public class SignupResponseDto {
    private String email;
    private String name;

}
