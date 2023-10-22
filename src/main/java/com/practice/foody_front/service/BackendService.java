package com.practice.foody_front.service;

import com.practice.foody_front.config.BackendConfig;
import com.practice.foody_front.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.Arrays;
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
    public User createUser(User user) {
        return restTemplate.postForObject(backendConfig.getBackendUrl()+"/users", user, User.class);
    }
    public void changePreferencees(Long userId, Preferences preferences) {
        HttpEntity entity = new HttpEntity<>(preferences);
        URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendUrl()+"/users/pref")
                        .queryParam("userId", userId).build().encode().toUri();
      restTemplate.exchange(uri, HttpMethod.PUT, entity,User.class);
    }
    public User login(LoginData loginData) {
        HttpEntity entity = new HttpEntity<>(loginData);
        return restTemplate.exchange(backendConfig.getBackendUrl()+"/users/login", HttpMethod.POST, entity, User.class).getBody();
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
    public void todoistIntegration(long userId, String code){
        URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendUrl()+"/users/token")
                .queryParam("userId", userId)
                .queryParam("code", code).build().encode().toUri();
        restTemplate.put(uri, null);
    }
    public void createTodoistProject(long userId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendUrl()+"/users/todoist")
                        .queryParam("userId", userId).build().encode().toUri();
        restTemplate.put(uri, null);
    }
    public void createTodoistTask(long dayId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendUrl()+"/recipe/daily")
                .queryParam("dailyId", dayId).build().encode().toUri();
        restTemplate.postForObject(uri, null, TodoistTask.class);
    }
    public List<DailyRecipes> getDailyRecipesList(long weekId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendUrl()+"/recipe/daily/list")
                .queryParam("weekId", weekId).build().encode().toUri();
        ResponseEntity<DailyRecipes[]> response = restTemplate.getForEntity(uri, DailyRecipes[].class);
        return Arrays.stream(response.getBody()).toList();
    }
    public List<Recipe> getRecipesForDay(long dayId) {
        ResponseEntity<Recipe[]> response = restTemplate.getForEntity(backendConfig.getBackendUrl()+"/recipe/"+dayId, Recipe[].class);
        return Arrays.stream(response.getBody()).toList();
    }
    public Recipe getRecipe(long recipeId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendUrl()+"/recipe")
                .queryParam("recipeId", recipeId).build().encode().toUri();
        return restTemplate.getForObject(uri, Recipe.class);
    }
    public DailyRecipes getDailyRecipes(long dayId) {
        URI uri = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendUrl()+"/recipe/daily")
                .queryParam("dailyId", dayId).build().encode().toUri();
        return restTemplate.getForObject(uri, DailyRecipes.class);
    }
}
