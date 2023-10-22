package com.practice.foody_front.view;

import com.practice.foody_front.domain.DailyRecipes;
import com.practice.foody_front.domain.Recipe;
import com.practice.foody_front.domain.User;
import com.practice.foody_front.service.BackendService;
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
    VerticalLayout content = new VerticalLayout();

    public DailyRecipesView(BackendService service) {
        this.service = service;
        add(new H3("Choose you meal to see the recipe"));
        add(mealLayout);
        add(new H3("Bellow is shopping list for this day"));
        add(shoppingList);
        Button userPanel = new Button("User Panel");
        userPanel.addClickListener(ev -> userPanel.getUI().ifPresent(ui-> ui.navigate(UserView.class, new RouteParameters("userId", Long.valueOf(userId).toString()))));
        add(userPanel);

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
            event.forwardTo(AdminView.class);
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
                params.put("dayId", Long.valueOf(dayId).toString());
                button.getUI().ifPresent(ui -> ui.navigate(RecipesView.class, new RouteParameters(params)));
            });
            mealLayout.add(button);
        }
    }public void createShoppingList() {
        String list = dailyRecipes.getShoppingList().replaceAll("\n", "; ");
        if (content.getComponentCount() == 0) {
            content.add(list);
        }
        User user = service.getUser(userId);
        if(user.isHasToken() && dailyRecipes.getTodoistTask().getContent() == null) {
            Button createList = new Button("Create Todoist task");
            createList.addClickListener(event -> {
                service.createTodoistTask(dayId);
                createList.setVisible(false);
                refresh();
            });
            content.add(createList);
        }
        if(dailyRecipes.getTodoistTask().getUrl() != null) {
            Anchor todoistUrl = new Anchor(dailyRecipes.getTodoistTask().getUrl(), "Todoist shopping list");
            content.add(todoistUrl);
        }
        shoppingList.addContent(content);
    }
}
