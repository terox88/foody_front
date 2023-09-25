package com.practice.foody_front.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeeklyRecipes {
    @JsonProperty("id")
    private long id;
    @JsonProperty("weekBegin")
    private LocalDate weekBegin;
    @JsonProperty("weekEnd")
    private LocalDate weekEnd;
    @JsonProperty("dailyRecipesId")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<Long> dailyRecipesId;

    public WeeklyRecipes() {
    }
}

