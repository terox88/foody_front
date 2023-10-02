package com.practice.foody_front.view;

import com.practice.foody_front.domain.DailyRecipes;
import com.practice.foody_front.domain.User;
import com.practice.foody_front.domain.WeeklyRecipes;
import com.practice.foody_front.service.BackendService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route( value = "week/:userId/:weekId")
public class WeeklyRecipesView extends VerticalLayout implements BeforeEnterObserver {
    private long weekId;
    private long userId;
    private User user;
    private WeeklyRecipes weeklyRecipes;
    private Grid<DailyRecipes> grid = new Grid<>(DailyRecipes.class);
    private BackendService service;

    public WeeklyRecipesView(BackendService service) {
        this.service = service;
        grid.setColumns("day");
        grid.setMinHeight("300px");
        grid.addComponentColumn(day -> {
            Button details = new Button("Details");
            details.addClickListener(event -> {
                Map<String, String> params = new HashMap<>();
                params.put("userId", Long.valueOf(userId).toString());
                params.put("dayId", Long.valueOf(day.getId()).toString());
                details.getUI().ifPresent(ui -> ui.navigate(DailyRecipesView.class, new RouteParameters(params)));
            });
            return details;
        }).setHeader("");
        add(new H4("Daily Recipes"));
        add(grid);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        RouteParameters parameters = event.getRouteParameters();

        String weekIdStr = parameters.get("weekId").orElse("");
        String userIdStr = parameters.get("userId").orElse("");
        try {
            weekId = Long.parseLong(weekIdStr);
            userId = Long.parseLong(userIdStr);

        } catch (NumberFormatException e) {
            event.forwardTo(MainView.class);
        }
        refresh();

    }
    public void refresh() {
        grid.setItems(service.getDailyRecipesList(weekId));
    }


}
