package com.practice.foody_front.service;

import com.practice.foody_front.config.BackendConfig;
import com.practice.foody_front.domain.Preferences;
import com.practice.foody_front.domain.User;
import com.practice.foody_front.domain.WeeklyRecipes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BackendService {
    private final BackendConfig backendConfig;
    private final RestTemplate restTemplate;

    public List<User> getUsers() {
        ResponseEntity<User[]> response = restTemplate.getForEntity(backendConfig.getBackendUrl()+"/users", User[].class);
        return Arrays.stream(response.getBody()).toList();
    }
    public User getUser(long userId) {
        return restTemplate.getForObject(backendConfig.getBackendUrl()+"/users/"+ userId, User.class);
    }
    public void changePreferencees(Long userId, Preferences preferences) {
        HttpEntity entity =new HttpEntity<>(preferences);
        URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendUrl()+"/users/pref")
                        .queryParam("userId", userId).build().encode().toUri();
      restTemplate.exchange(uri, HttpMethod.PUT, entity,User.class);
    }
    public List<WeeklyRecipes> getUsersWeeklyRecipes(long userId) {
        ResponseEntity<WeeklyRecipes[]> response = restTemplate.getForEntity(backendConfig.getBackendUrl()+"/week/"+ userId, WeeklyRecipes[].class);
        return Arrays.stream(response.getBody()).toList();
    }
    public void createWeeklyRecipes(long userId, LocalDate begin) {
        URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendUrl()+"/week")
                .queryParam("userId", userId)
                .queryParam("day", begin).build().encode().toUri();
        restTemplate.postForObject(uri, null, WeeklyRecipes.class);


    }
}
