package com.example.userservice.dtos;

import com.example.userservice.models.Role;
import com.example.userservice.models.User;
import jakarta.persistence.ManyToMany;

import java.util.List;

public class UserDto {

    private String name;
    private String email;


    private List<Role> roles;
    private Boolean isEmailVerified;

    public static UserDto from(User user){
        if(user == null )
            return null;

        UserDto userDto = new UserDto();

        userDto.email = user.getEmail();
        userDto.name = user.getName();
        userDto.roles = user.getRoles();
        userDto.isEmailVerified = user.getIsEmailVerified();

        return userDto;
    }

}
