package com.practice.foody_front.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

@Route(value = "daily/:userId/:dayId")
public class DailyRecipesView extends VerticalLayout implements BeforeEnterObserver {
    @Override
    public void beforeEnter(BeforeEnterEvent event) {

    }
}
