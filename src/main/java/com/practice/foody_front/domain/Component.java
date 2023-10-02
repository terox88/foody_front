package com.practice.foody_front.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Component {
    @JsonProperty("id")
    private long id;
    @JsonProperty("position")
    private int position;
    @JsonProperty("raw_text")
    private String text;
    @JsonProperty("measurements")
    private List<Measurements> measurements;
    @JsonProperty("ingredient")
    private  Ingredient ingredient;

    public Component() {
    }
}
