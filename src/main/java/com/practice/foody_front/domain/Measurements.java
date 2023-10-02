package com.practice.foody_front.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Measurements {
    @JsonProperty("id")
    private long id;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("unit")
    private Unit unit;

    public Measurements() {
    }
}
