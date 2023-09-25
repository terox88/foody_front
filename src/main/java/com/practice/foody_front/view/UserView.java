package com.practice.foody_front.view;

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
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;

import java.time.LocalDate;
import java.util.Set;

@Route("user/:userId?")

public class UserView extends VerticalLayout implements BeforeEnterObserver {
    private Preferences preferences = new Preferences();
    private long userId;
    private Grid<WeeklyRecipes> grid = new Grid<>(WeeklyRecipes.class);
    BackendService service;
    public UserView(BackendService service) {
        this.service = service;
        grid.setColumns("weekBegin", "weekEnd");
        grid.addComponentColumn(week -> {
            Button details = new Button("Details");
            details.addClickListener(event -> {
                details.getUI().ifPresent(ui-> ui.navigate(WeeklyRecipesView.class, new RouteParameters("weekId", Long.valueOf(week.getId()).toString())));
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




        add(new H2("Your weekly recipes"));
        add(grid);
        add(new H2("Create new Weekly recipes"));
        add(createForm);
        add("Set you preferences");
        add(preferencesForm);
        add(savePreferencesButton);
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

            refresh();

    }

    public void refresh() {
        grid.setItems(service.getUsersWeeklyRecipes(userId));
    }



}
