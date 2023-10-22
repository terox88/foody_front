package com.practice.foody_front.view;

import com.practice.foody_front.domain.Component;
import com.practice.foody_front.domain.Instruction;
import com.practice.foody_front.domain.Recipe;
import com.practice.foody_front.service.BackendService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route(value = "recipe/:userId/:recipeId/:dayId")
public class RecipesView extends VerticalLayout implements BeforeEnterObserver {
    private long recipeId;
    private long userId;
    private long dayId;
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
        String dayIdStr = parameters.get("dayId").orElse("");
        try {
            recipeId = Long.parseLong(recipeIdStr);
            userId = Long.parseLong(userIdStr);
            dayId = Long.parseLong(dayIdStr);

        } catch (NumberFormatException e) {
            event.forwardTo(AdminView.class);
        }

        recipe = service.getRecipe(recipeId);
        add(new H2(recipe.getName()));
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
        Button dailyRecipes = new Button("Back");
        dailyRecipes.addClickListener(ev -> {
            Map<String, String> params = new HashMap<>();
            params.put("userId", Long.valueOf(userId).toString());
            params.put("dayId", Long.valueOf(dayId).toString());
            dailyRecipes.getUI().ifPresent(ui -> ui.navigate(DailyRecipesView.class, new RouteParameters(params)));
        });
        add(dailyRecipes);
    }
}
