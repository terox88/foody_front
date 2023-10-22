package com.practice.foody_front.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginData {
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;

    public LoginData() {
    }
}

