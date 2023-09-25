package com.practice.foody_front.domain;

public enum Role {
    USER ("User"), ADMIN ("Admin"), Author ("Author");
    private String role;
    private Role (String role) {
        this.role = role;
    }
}

