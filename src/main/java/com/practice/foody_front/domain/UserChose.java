package com.practice.foody_front.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum UserChose {
    DAIRY_FREE ("dairy_free"), GLUTEN_FREE ("gluten_free"), LOW_CALORIE ("low_calorie"), VEGETARIAN ("vegetarian");
    @JsonProperty("chose")
    private final String chose;

    private UserChose(String chose) {
        this.chose = chose;
    }
}
