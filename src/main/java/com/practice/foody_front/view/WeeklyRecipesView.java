package com.practice.foody_front.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;

@Route("user/week/:weekId?")
public class WeeklyRecipesView extends VerticalLayout implements BeforeEnterObserver {
    long weekId;
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        RouteParameters parameters = event.getRouteParameters();

        String userIdStr = parameters.get("weekId").orElse("");
        try {
            weekId = Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            event.forwardTo(MainView.class);
        }

    }
}
