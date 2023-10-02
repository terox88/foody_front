package com.practice.foody_front.domain;

import lombok.Getter;

import javax.annotation.processing.Generated;

@Getter
public enum MealType {
    BREAKFAST("breakfast"),
    BRUNCH ("brunch"),
    LUNCH("lunch"),
    DINNER("dinner");
    private final String tag;

    private MealType (String tag) {
        this.tag = tag;
    }
}
