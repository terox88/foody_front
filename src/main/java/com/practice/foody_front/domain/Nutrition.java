package com.practice.foody_front.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Nutrition {
    @JsonProperty("protein")
    private int	protein;
    @JsonProperty("fat")
    private int	fat;
    @JsonProperty("calories")
    private int	calories;
    @JsonProperty("sugar")
    private int	sugar;
    @JsonProperty("carbohydrates")
    private int	carbohydrates;
    @JsonProperty("fiber")
    private int	fiber;

    public Nutrition() {
    }
}
