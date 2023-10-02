package com.practice.foody_front.view;

import com.practice.foody_front.domain.DailyRecipes;
import com.practice.foody_front.domain.Recipe;
import com.practice.foody_front.service.BackendService;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteParameters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route(value = "daily/:userId/:dayId")
public class DailyRecipesView extends VerticalLayout implements BeforeEnterObserver {
    private long dayId;
    private long userId;
    private DailyRecipes dailyRecipes;
    private List<Recipe> recipeList;
    private BackendService service;
    FormLayout mealLayout = new FormLayout();
    private Details shoppingList = new Details("SHOPPING LIST");

    public DailyRecipesView(BackendService service) {
        this.service = service;
        add(new H3("Choose you meal to see the recipe"));
        add(mealLayout);
        add(new H3("Bellow is shopping list for this day"));
        add(shoppingList);

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        RouteParameters parameters = event.getRouteParameters();

        String dayIdStr = parameters.get("dayId").orElse("");
        String userIdStr = parameters.get("userId").orElse("");
        try {
            dayId = Long.parseLong(dayIdStr);
            userId = Long.parseLong(userIdStr);

        } catch (NumberFormatException e) {
            event.forwardTo(MainView.class);
        }

        refresh();
    }
    public void refresh() {
        dailyRecipes = service.getDailyRecipes(dayId);
        recipeList = service.getRecipesForDay(dayId);
        createMealLayout();
        createShoppingList();
    }
    public void createMealLayout() {
        mealLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        mealLayout.setMaxWidth("300px");
        for(Recipe recipe : recipeList) {
            Button button = new Button(recipe.getMealType().getTag());
            button.addClickListener(event -> {
                Map<String, String> params = new HashMap<>();
                params.put("userId", Long.valueOf(userId).toString());
                params.put("recipeId", Long.valueOf(recipe.getId()).toString());
                button.getUI().ifPresent(ui -> ui.navigate(RecipesView.class, new RouteParameters(params)));
            });
            mealLayout.add(button);
        }
    }public void createShoppingList() {
        VerticalLayout content = new VerticalLayout();
        String list = dailyRecipes.getShoppingList().replaceAll("\n", "; ");
        content.add(list);
        shoppingList.addContent(content);
    }
}
