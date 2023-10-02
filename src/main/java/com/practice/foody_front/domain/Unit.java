package com.practice.foody_front.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Unit {
    @JsonProperty("system")
    private String system;
    @JsonProperty("name")
    private String name;

    public Unit() {
    }
}
