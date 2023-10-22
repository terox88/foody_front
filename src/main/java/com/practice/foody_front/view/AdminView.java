package com.practice.foody_front.view;

import com.practice.foody_front.domain.User;
import com.practice.foody_front.service.BackendService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;


@Route("admin")
public class AdminView extends VerticalLayout {
    private final BackendService backendService;
    private Grid<User> grid = new Grid<>(User.class);
    public AdminView(BackendService backendService) {
        this.backendService = backendService;
        grid.setColumns("id", "email", "password", "role");
        grid.addComponentColumn(user -> {
            Button userPanel = new Button("Panel");
            userPanel.addClickListener(event -> {
               userPanel.getUI().ifPresent(ui-> ui.navigate(UserView.class, new RouteParameters("userId", Long.valueOf(user.getId()).toString())));
            });
            return userPanel;

        }).setHeader("TEST");
        add(grid);
        setSizeFull();
        refresh();

    }
    public void refresh() {
        grid.setItems(backendService.getUsers());
    }
}
