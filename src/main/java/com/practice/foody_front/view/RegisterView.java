package com.practice.foody_front.view;

import com.practice.foody_front.domain.Role;
import com.practice.foody_front.domain.User;
import com.practice.foody_front.service.BackendService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;

@Route("register")
public class RegisterView extends VerticalLayout {
    private final BackendService backendService;

    public RegisterView(BackendService backendService) {
        this.backendService = backendService;
        FormLayout registerForm = new FormLayout();
        EmailField email = new EmailField("Email");
        PasswordField password = new PasswordField("Password");
        PasswordField repeatedPassword = new PasswordField("Repeat password");
        Button register = new Button("Register");
        register.addClickListener(event -> {
          if(!password.getValue().equals(repeatedPassword.getValue())) {
              registerForm.add("Passwords don't match");
          }else if(!email.isEmpty() && !password.isEmpty()) {
              User user = new User(email.getValue(), password.getValue(), Role.USER);
              User createdUser = backendService.createUser(user);
              register.getUI().ifPresent(ui-> ui.navigate(UserView.class, new RouteParameters("userId", Long.valueOf(createdUser.getId()).toString())));
          }
        });
        registerForm.add(email,password, repeatedPassword, register);
        registerForm.setMaxWidth("500px");
        add(registerForm);
    }
}
