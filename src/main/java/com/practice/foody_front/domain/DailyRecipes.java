package com.practice.foody_front.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
@Getter
@AllArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyRecipes {
    @JsonProperty("id")
    private long id;
    @JsonProperty("day")
    private LocalDate day;
    @JsonProperty("recipesId")
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<Long> recipesId;
    @JsonProperty("todoistTaskDto")
    private TodoistTask todoistTask;

    public DailyRecipes() {
    }
}
