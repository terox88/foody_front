package com.practice.foody_front.view;

import com.practice.foody_front.config.TodoistConfig;
import com.practice.foody_front.domain.Preferences;
import com.practice.foody_front.domain.User;
import com.practice.foody_front.domain.UserChose;
import com.practice.foody_front.domain.WeeklyRecipes;
import com.practice.foody_front.service.BackendService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Route("user/:userId")
@Slf4j
public class UserView extends VerticalLayout implements BeforeEnterObserver {
    private Preferences preferences = new Preferences();
    private long userId;
    private Grid<WeeklyRecipes> grid = new Grid<>(WeeklyRecipes.class);
    private BackendService service;
   private TodoistConfig todoistConfig;
   private Anchor integrateLink = new Anchor("", "INTEGRATE");
   private FormLayout integrateLayout = new FormLayout();
   private FormLayout projectLayout = new FormLayout();
   private User user;
    public UserView(BackendService service, TodoistConfig todoistConfig) {
        this.service = service;
        this.todoistConfig = todoistConfig;
        grid.setColumns("weekBegin", "weekEnd");
        grid.setMinHeight("300px");
        grid.addComponentColumn(week -> {
            Button details = new Button("Details");
            details.addClickListener(event -> {
                Map<String, String> params = new HashMap<>();
                params.put("userId", Long.valueOf(userId).toString());
                params.put("weekId", Long.valueOf(week.getId()).toString());
                details.getUI().ifPresent(ui-> ui.navigate(WeeklyRecipesView.class, new RouteParameters(params)));
            });
            return details;
        }).setHeader("");
        FormLayout preferencesForm = new FormLayout();
        Checkbox option1 = new Checkbox("Dairy free");
        Checkbox option2 = new Checkbox("Gluten free");
        Checkbox option3 = new Checkbox("Low calorie");
        Checkbox option4 = new Checkbox("Vegetarian");

        Button savePreferencesButton = new Button("Save preferences");
        savePreferencesButton.addClickListener(event -> {
            preferences.getPreferences().clear();
            if (option1.getValue()) {
                preferences.getPreferences().add(UserChose.DAIRY_FREE);
            }
            if (option2.getValue()) {
                preferences.getPreferences().add(UserChose.GLUTEN_FREE);
            }
            if (option3.getValue()) {
                preferences.getPreferences().add(UserChose.LOW_CALORIE);
            }
            if (option4.getValue()) {
                preferences.getPreferences().add(UserChose.VEGETARIAN);
            }
            log.info("user id: " + userId);
            service.changePreferencees(userId, preferences);
            option1.clear();
            option2.clear();
            option3.clear();
            option4.clear();

        });
        preferencesForm.add(option1, option2, option3, option4);
        preferencesForm.setMaxWidth("500px");

        FormLayout createForm = new FormLayout();
        Button createWeeklyRecipes = new Button("Create");
        DatePicker datePicker =  new DatePicker("Begin date");
        datePicker.setMin(LocalDate.now());
        datePicker.setMax(LocalDate.now().plusMonths(3));
        createWeeklyRecipes.addClickListener(event -> {
            if (!datePicker.isEmpty()) {
                LocalDate begin = datePicker.getValue();
                service.createWeeklyRecipes(userId, begin);
                refresh();
            }
        });
        createForm.add(datePicker);
        createForm.add(createWeeklyRecipes);
        createForm.setMaxWidth("150px");
        integrateLayout.add(new H5("You can integrate this app with Todoist application"));
        integrateLayout.add(integrateLink);
        Button createProject = new Button("Create");
        createProject.addClickListener(event -> {
            service.createTodoistProject(userId);
            refresh();
        });
        projectLayout.setVisible(false);
        projectLayout.add(new H5("You can create new project in your Todoist application"));
        projectLayout.add(createProject);


        add(new H2("Your weekly recipes"));
        add(grid);
        add(new H2("Create new Weekly recipes"));
        add(createForm);
        add("Set you preferences");
        add(preferencesForm);
        add(savePreferencesButton);
        add(integrateLayout);
        add(projectLayout);
        setSizeFull();
    }
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        RouteParameters parameters = event.getRouteParameters();

        String userIdStr = parameters.get("userId").orElse("");
            try {
                userId = Long.parseLong(userIdStr);
            } catch (NumberFormatException e) {
                event.forwardTo(MainView.class);
            }
            user = service.getUser(userId);
            setUrl();

            refresh();


    }

    public void refresh() {
        user = service.getUser(userId);
        grid.setItems(service.getUsersWeeklyRecipes(userId));
        integrateVisibility();
        createVisibility();
    }
    public String createAuthUrl() {
        return UriComponentsBuilder.fromHttpUrl(todoistConfig.getEndpoint())
                .queryParam("client_id", todoistConfig.getClientID())
                .queryParam("scope", "data:read_write")
                .queryParam("state", Long.valueOf(userId).toString()).build().encode().toUri().toString();
    }
    public void setUrl(){
        this.integrateLink.setHref(createAuthUrl());
    }
    public void integrateVisibility() {
        if(user.isHasToken()) {
            integrateLayout.setVisible(false);
        }
    }
    public void createVisibility() {
        if(user.isHasToken() && !user.isHasProject()) {
            projectLayout.setVisible(true);
        } else {
            projectLayout.setVisible(false);
        }
    }





}
