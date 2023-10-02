package com.practice.foody_front.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Recipe {
    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("instructions")
    private List<Instruction> instructions;
    @JsonProperty("sections")
    private List<Section> sections;
    @JsonProperty("nutrition")
    private Nutrition nutrition;
    @JsonProperty("mealType")
    private MealType mealType;

    public Recipe() {
    }
}
