package what.to.eat;

import what.to.eat.entities.Recipe;

public class CommonMethods {

    public static Recipe createRecipe(String name, Integer categoryId, Integer cookingMethodId, String comment,
                                String description, String steps, Integer userId) {
        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setCategoryId(categoryId);
        recipe.setCookingMethodId(cookingMethodId);
        recipe.setComment(comment);
        recipe.setDescription(description);
        recipe.setSteps(steps);
        recipe.setUserId(userId);
        return recipe;
    }
}
