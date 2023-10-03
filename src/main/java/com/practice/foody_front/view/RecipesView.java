package com.practice.foody_front.view;

import com.practice.foody_front.domain.Component;
import com.practice.foody_front.domain.Instruction;
import com.practice.foody_front.domain.Recipe;
import com.practice.foody_front.service.BackendService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;

import java.util.List;

@Route(value = "recipe/:userId/:recipeId")
public class RecipesView extends VerticalLayout implements BeforeEnterObserver {
    private long recipeId;
    private long userId;
    private Recipe recipe;
    private BackendService service;

    public RecipesView(BackendService service) {
        this.service = service;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        RouteParameters parameters = event.getRouteParameters();

        String recipeIdStr = parameters.get("recipeId").orElse("");
        String userIdStr = parameters.get("userId").orElse("");
        try {
            recipeId = Long.parseLong(recipeIdStr);
            userId = Long.parseLong(userIdStr);

        } catch (NumberFormatException e) {
            event.forwardTo(MainView.class);
        }

        recipe = service.getRecipe(recipeId);
        add(new H4("Ingredients"));
       List<String> ingredients = recipe.getSections().stream().flatMap(component -> component.getComponents()
                .stream().map(Component::getText)).toList();
       for(String ingredient : ingredients) {
           add(new Span(ingredient));
       }
       add(new H4("Instructions"));
        HorizontalLayout instructionLayout = new HorizontalLayout();
        for(Instruction instruction : recipe.getInstructions()) {
            add(new Span(instruction.getText()));
        }
        add(instructionLayout);
        add(new H4("Nutrition"));
        add(new Span("Calories: " + recipe.getNutrition().getCalories()));
        add(new Span("Sugar: " + recipe.getNutrition().getSugar()));
        add(new Span("Fat: " + recipe.getNutrition().getFat()));
        add(new Span("Fiber: " + recipe.getNutrition().getFiber()));
        add(new Span("Protein: " + recipe.getNutrition().getProtein()));
        add(new Span("Carbohydrates: " + recipe.getNutrition().getCarbohydrates()));

        Button userPanel = new Button("User Panel");
        userPanel.addClickListener(ev -> userPanel.getUI().ifPresent(ui-> ui.navigate(UserView.class, new RouteParameters("userId", Long.valueOf(userId).toString()))));
        add(userPanel);
    }
}
