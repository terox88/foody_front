package com.practice.foody_front.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Section {
    @JsonProperty("name")
    private String name;
    @JsonProperty("position")
    private int position;
    @JsonProperty("components")
    private List<Component> components;

    public Section() {
    }
}
