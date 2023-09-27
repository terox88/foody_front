package com.practice.foody_front.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TodoistConfig {
@Value("${todoist.client}")
    private String clientID;
@Value("${todoist.endpoint}")
    private String endpoint;
}
