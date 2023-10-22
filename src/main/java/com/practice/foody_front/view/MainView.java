package com.practice.foody_front.view;

import com.practice.foody_front.domain.LoginData;
import com.practice.foody_front.domain.User;
import com.practice.foody_front.service.BackendService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;

@Route
public class MainView extends VerticalLayout {
    private final BackendService backendService;

    public MainView(BackendService backendService) {
        this.backendService = backendService;
        FormLayout loginForm = new FormLayout();
        EmailField email = new EmailField("email");
        PasswordField password = new PasswordField("password");
        Button login = new Button("Login");
        login.addClickListener(event -> {
            if(!email.isEmpty() && !password.isEmpty()) {
             User user = backendService.login(new LoginData(email.getValue(), password.getValue()));
                login.getUI().ifPresent(ui-> ui.navigate(UserView.class, new RouteParameters("userId", Long.valueOf(user.getId()).toString())));
            }
        });
        loginForm.add(email);
        loginForm.add(password);
        loginForm.add(login);
        loginForm.setMaxWidth("500px");
        add(loginForm);
        add("OR");
        Button signIn = new Button("Register");
        signIn.addClickListener(event -> signIn.getUI().ifPresent(ui -> ui.navigate(RegisterView.class)));
        add(signIn);


    }
}
