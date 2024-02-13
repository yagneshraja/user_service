package com.example.userservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Setter
@Getter
@Entity
public class User extends BaseModel{
    private String email;
    private String name;
    private String hashedPassword;

    @ManyToMany
    private List<Role> roles;
    private Boolean isEmailVerified;
}

/*
    1       :   m
    User    :   Address
    1       :   1

    1:m
*/