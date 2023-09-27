package com.practice.foody_front.view;

import com.practice.foody_front.service.BackendService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.router.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Route(value = "token")

@Slf4j
public class TodoistIntegrationView extends VerticalLayout implements BeforeEnterObserver {
    private long userId;
    private String code;

    private BackendService service;

    public TodoistIntegrationView(BackendService service) {
        this.service = service;
        Button confirm = new Button("Confirm");
        confirm.addClickListener(event -> {
            service.todoistIntegration(userId, code);
            confirm.getUI().ifPresent(ui-> ui.navigate(UserView.class, new RouteParameters("userId", Long.valueOf(userId).toString())));
        });
        add(confirm);

    }

    //@Override
    public void beforeEnter(BeforeEnterEvent event) {
        Map<String, List<String>> parameters = event.getLocation().getQueryParameters().getParameters();
        code = parameters.get("code").get(0);
        String userIdStr = parameters.get("state").get(0);
        log.info("user id: " + userIdStr);
        log.info("code: " + code);
        try {
            userId = Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            //event.forwardTo(MainView.class);
        }
    }

}



