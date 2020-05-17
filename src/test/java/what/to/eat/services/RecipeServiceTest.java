package what.to.eat.services;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import what.to.eat.entities.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for testing RecipeService
 */
public class RecipeServiceTest extends ServiceTest {

    @Autowired
    RecipeService recipeService;

    @Test
    @DatabaseSetup("/db/scripts/Category.xml")
    @DatabaseSetup("/db/scripts/Recipe.xml")
    public void getAllRecipesByCategoryTest() {
        List<Recipe> existingCategory = recipeService.getAllRecipesByCategory("ENTREE");
        List<Recipe> existingCategory2 = recipeService.getAllRecipesByCategory("entree");
        List<Recipe> nonExistingCategory = recipeService.getAllRecipesByCategory("Wrong");

        Assert.assertEquals(2, existingCategory2.size());
        Assert.assertEquals(2, existingCategory.size());
        Assert.assertEquals("testRecipe4", existingCategory.get(0).getName());
        Assert.assertEquals("testRecipe5", existingCategory.get(1).getName());

        Assert.assertEquals(new ArrayList<Recipe>(), nonExistingCategory);
    }

    @Test
    @DatabaseSetup("/db/scripts/CookingMethod.xml")
    @DatabaseSetup("/db/scripts/Recipe.xml")
    public void getAllRecipesByCookingMethodTest() {
        List<Recipe> existingCookingMethod = recipeService.getAllRecipesByCookingMethod("FRYING");
        List<Recipe> existingCookingMethod2 = recipeService.getAllRecipesByCookingMethod("frying");
        List<Recipe> nonExistingCookingMethod = recipeService.getAllRecipesByCookingMethod("Wrong");

        Assert.assertEquals(2, existingCookingMethod2.size());
        Assert.assertEquals(2, existingCookingMethod.size());
        Assert.assertEquals("testRecipe4", existingCookingMethod.get(0).getName());
        Assert.assertEquals("testRecipe6", existingCookingMethod.get(1).getName());

        Assert.assertEquals(new ArrayList<Recipe>(), nonExistingCookingMethod);
    }

    @Test
    @DatabaseSetup("/db/scripts/Category.xml")
    @DatabaseSetup("/db/scripts/CookingMethod.xml")
    @DatabaseSetup("/db/scripts/Recipe.xml")
    public void getAllRecipesByCategoryAndCookingMethod() {
        List<Recipe> existingCategoryAndCookingMethod =
                recipeService.getAllRecipesByCategoryAndCookingMethod("MAIN", "FRYING");
        List<Recipe> nonExistingCategory =
                recipeService.getAllRecipesByCategoryAndCookingMethod("Wrong", "FRYING");
        List<Recipe> nonExistingCookingMethod =
                recipeService.getAllRecipesByCategoryAndCookingMethod("MAIN", "Wrong");
        List<Recipe> nonExistingCategoryAndCookingMethod =
                recipeService.getAllRecipesByCategoryAndCookingMethod("Wrong", "Wrong");

        Assert.assertEquals(1, existingCategoryAndCookingMethod.size());
        Assert.assertEquals("testRecipe6", existingCategoryAndCookingMethod.get(0).getName());

        Assert.assertEquals(0, nonExistingCategory.size());
        Assert.assertEquals(0, nonExistingCookingMethod.size());
        Assert.assertEquals(0, nonExistingCategoryAndCookingMethod.size());
    }

    @Test
    @DatabaseSetup("/db/scripts/Category.xml")
    @DatabaseSetup("/db/scripts/CookingMethod.xml")
    @DatabaseSetup("/db/scripts/Recipe.xml")
    public void getAllRecipesTest() {
        List<Recipe> existingCategoryAndCookingMethod =
                recipeService.getAllRecipes("MAIN", "FRYING");

        List<Recipe> getByCategory = recipeService.getAllRecipes("MAIN", null);
        List<Recipe> getByCookingMethod =recipeService.getAllRecipes(null, "FRYING");
        List<Recipe> getAll = recipeService.getAllRecipes(null, null);
        List<Recipe> emptyCategoryAndCookingMethod = recipeService.getAllRecipes("", "");
        List<Recipe> spaceCategoryAndCookingMethod = recipeService.getAllRecipes(" ", " ");;

        List<Recipe> wrongCategory = recipeService.getAllRecipes("Wrong", "FRYING");
        List<Recipe> wrongCookingMethod = recipeService.getAllRecipes("MAIN", "Wrong");
        List<Recipe> wrongCategoryAndCookingMethod = recipeService.getAllRecipes("Wrong", "Wrong");

        Assert.assertEquals(1, existingCategoryAndCookingMethod.size());
        Assert.assertEquals("testRecipe6", existingCategoryAndCookingMethod.get(0).getName());

        Assert.assertEquals(3, getByCategory.size());
        Assert.assertEquals("testRecipe", getByCategory.get(0).getName());
        Assert.assertEquals("testRecipe3", getByCategory.get(1).getName());
        Assert.assertEquals("testRecipe6", getByCategory.get(2).getName());

        Assert.assertEquals(2, getByCookingMethod.size());
        Assert.assertEquals("testRecipe4", getByCookingMethod.get(0).getName());
        Assert.assertEquals("testRecipe6", getByCookingMethod.get(1).getName());

        Assert.assertEquals(6, getAll.size());
        Assert.assertEquals(6, emptyCategoryAndCookingMethod.size());
        Assert.assertEquals(6, spaceCategoryAndCookingMethod.size());

        Assert.assertEquals(0, wrongCategory.size());
        Assert.assertEquals(0, wrongCookingMethod.size());
        Assert.assertEquals(0, wrongCategoryAndCookingMethod.size());
    }

    @Test
    @DatabaseSetup("/db/scripts/Recipe.xml")
    public void getRecipeByIdTest() {
        Recipe recipe = recipeService.getRecipeById(1);
        Assert.assertEquals("testRecipe", recipe.getName());
    }

    @Test
    @DatabaseSetup("/db/scripts/Recipe.xml")
    public void getRecipeByNonExistingIdTest() {
        Assert.assertNull(recipeService.getRecipeById(-1));
        Assert.assertNull(recipeService.getRecipeById(0));
        Assert.assertNull(recipeService.getRecipeById(100));
        Assert.assertNull(recipeService.getRecipeById(null));
    }

    @Test
    @DatabaseSetup("/db/scripts/Recipe.xml")
    public void saveRecipe() {
        Recipe recipe = createRecipe("Saved Recipe",1,2,"Add comment",
                "Add description", "Add steps", 1);

        List<Recipe> before = recipeService.getAllRecipes(null, null);
        recipeService.saveRecipe(recipe);
        List<Recipe> after = recipeService.getAllRecipes(null, null);

        Assert.assertEquals(6, before.size());
        Assert.assertEquals(7, after.size());

        Recipe savedRecipe = after.get(6);
        Assert.assertEquals(recipe.getName(), savedRecipe.getName());
        Assert.assertEquals(recipe.getCategoryId(), savedRecipe.getCategoryId());
        Assert.assertEquals(recipe.getCookingMethodId(), savedRecipe.getCookingMethodId());
        Assert.assertEquals(recipe.getComment(), savedRecipe.getComment());
        Assert.assertEquals(recipe.getDescription(), savedRecipe.getDescription());
        Assert.assertEquals(recipe.getSteps(), savedRecipe.getSteps());
        Assert.assertEquals(recipe.getUserId(), savedRecipe.getUserId());

    }

    private Recipe createRecipe(String name, Integer categoryId, Integer cookingMethodId, String comment,
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
