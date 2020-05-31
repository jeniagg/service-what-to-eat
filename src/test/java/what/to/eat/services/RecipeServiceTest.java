package what.to.eat.services;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import what.to.eat.CommonMethods;
import what.to.eat.dtos.AllRecipesDto;
import what.to.eat.dtos.CategoryEnum;
import what.to.eat.dtos.CookingMethodEnum;
import what.to.eat.dtos.RecipeDto;
import what.to.eat.entities.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for testing RecipeService
 */
public class RecipeServiceTest extends ServiceTest {

    @Autowired
    RecipeService recipeService;

    @Autowired
    UsersService usersService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CookingMethodService cookingMethodService;

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
        List<Recipe> getByCookingMethod = recipeService.getAllRecipes(null, "FRYING");
        List<Recipe> getAll = recipeService.getAllRecipes(null, null);
        List<Recipe> emptyCategoryAndCookingMethod = recipeService.getAllRecipes("", "");
        List<Recipe> spaceCategoryAndCookingMethod = recipeService.getAllRecipes(" ", " ");
        ;

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
        Recipe recipe = CommonMethods.createRecipe("Saved Recipe", 1, 2, "Add comment",
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

    @Test
    @DatabaseSetup("/db/scripts/Recipe.xml")
    @DatabaseSetup("/db/scripts/Users.xml")
    @DatabaseSetup("/db/scripts/Category.xml")
    @DatabaseSetup("/db/scripts/CookingMethod.xml")
    public void updateRecipeTest() {
        Recipe recipe = recipeService.getRecipeById(1);
        RecipeDto recipeDto = new RecipeDto(null, "updatedRecipe", "updated descr",
                "TestUser2","updated steps", CookingMethodEnum.valueOf("BLANCHING"),
                CategoryEnum.valueOf("ENTREE"), "updated comment");
        recipeService.updateRecipe(Integer.valueOf("1"), recipeDto);
        Recipe updatedRecipe = recipeService.getRecipeById(1);
        RecipeDto updatedRecipeDto = recipeService.convertToDto(updatedRecipe);

        Assert.assertEquals(recipe.getId(), updatedRecipe.getId());
        Assert.assertEquals(recipeDto.getName(), updatedRecipeDto.getName());
        Assert.assertEquals(recipeDto.getSteps(), updatedRecipeDto.getSteps());
        Assert.assertEquals(recipeDto.getDescription(), updatedRecipeDto.getDescription());
        Assert.assertEquals(recipeDto.getComment(), updatedRecipeDto.getComment());
        Assert.assertEquals(recipeDto.getCategory(), updatedRecipeDto.getCategory());
        Assert.assertEquals(recipeDto.getCookingMethod(), updatedRecipeDto.getCookingMethod());
        Assert.assertEquals(recipeDto.getUsername(), updatedRecipeDto.getUsername());
    }

    @Test
    @DatabaseSetup("/db/scripts/Recipe.xml")
    @DatabaseSetup("/db/scripts/Users.xml")
    @DatabaseSetup("/db/scripts/Category.xml")
    @DatabaseSetup("/db/scripts/CookingMethod.xml")
    public void updateRecipeNullValuesTest() {
        Recipe recipe = recipeService.getRecipeById(1);
        RecipeDto recipeDto = new RecipeDto(null, "updatedRecipe", null,
                null,null, null,
                null, null);
        recipeService.updateRecipe(Integer.valueOf("1"), recipeDto);
        Recipe updatedRecipe = recipeService.getRecipeById(1);
        RecipeDto updatedRecipeDto = recipeService.convertToDto(updatedRecipe);

        Assert.assertEquals(recipe.getId(), updatedRecipe.getId());
        Assert.assertEquals(recipeDto.getName(), updatedRecipeDto.getName());
        Assert.assertNull(updatedRecipeDto.getSteps());
        Assert.assertNull(updatedRecipeDto.getDescription());
        Assert.assertNull(updatedRecipeDto.getComment());
        Assert.assertNull(updatedRecipeDto.getCategory());
        Assert.assertNull(updatedRecipeDto.getCookingMethod());
        Assert.assertNull(updatedRecipeDto.getUsername());
    }

    @Test
    @DatabaseSetup("/db/scripts/Recipe.xml")
    public void deleteRecipeTest() {
        List<Recipe> recipes = recipeService.getAllRecipes(null, null);
        Recipe toBeDeleted = recipes.get(1);
        recipeService.deleteRecipe(1);
        List<Recipe> newRecipes = recipeService.getAllRecipes(null, null);

        Assert.assertEquals(recipes.size(), newRecipes.size() + 1);
        Assert.assertTrue(recipes.contains(toBeDeleted));
        Assert.assertFalse(newRecipes.contains(toBeDeleted));
    }

    @Test
    @DatabaseSetup("/db/scripts/Recipe.xml")
    public void deleteRecipeNonExistingTest() {
        List<Recipe> recipes = recipeService.getAllRecipes(null, null);
        recipeService.deleteRecipe(100);
        List<Recipe> newRecipes = recipeService.getAllRecipes(null, null);

        Assert.assertEquals(recipes.size(), newRecipes.size());
    }

    @Test
    @DatabaseSetup("/db/scripts/Users.xml")
    @DatabaseSetup("/db/scripts/Category.xml")
    @DatabaseSetup("/db/scripts/CookingMethod.xml")
    public void convertToEntityTest() {
        RecipeDto recipeDto = new RecipeDto(1, "testDto", "descr", "TestUser","steps",
                CookingMethodEnum.valueOf("BAKING"), CategoryEnum.valueOf("MAIN"), "comment");
        Recipe recipe = recipeService.convertToEntity(recipeDto);

        Assert.assertEquals(recipeDto.getName(), recipe.getName());
        Assert.assertEquals(recipeDto.getSteps(), recipe.getSteps());
        Assert.assertEquals(recipeDto.getDescription(), recipe.getDescription());
        Assert.assertEquals(recipeDto.getComment(), recipe.getComment());
        Assert.assertEquals(recipeDto.getCookingMethod().name(),
                cookingMethodService.getCookingMethodNameById(recipe.getCookingMethodId()));
        Assert.assertEquals(recipeDto.getCategory().name(), categoryService.getCategoryName(recipe.getCategoryId()));
        Assert.assertEquals(recipeDto.getUsername(), usersService.getUsernameById(recipe.getUserId()));
    }

    @Test
    public void convertToEntityMandatoryFieldsOnlyTest() {
        RecipeDto recipeDto = new RecipeDto(null, "testDto", null, null,null,
                null, null, null);
        Recipe recipe = recipeService.convertToEntity(recipeDto);

        Assert.assertEquals(recipeDto.getName(), recipe.getName());
        Assert.assertNull(recipe.getUserId());
        Assert.assertNull(recipe.getSteps());
        Assert.assertNull(recipe.getDescription());
        Assert.assertNull(recipe.getComment());
        Assert.assertNull(recipe.getCookingMethodId());
        Assert.assertNull(recipe.getCategoryId());
    }

    @Test
    @DatabaseSetup("/db/scripts/Users.xml")
    @DatabaseSetup("/db/scripts/Category.xml")
    @DatabaseSetup("/db/scripts/CookingMethod.xml")
    public void convertToDtoTest() {
        Recipe recipe = CommonMethods.createRecipe("Saved Recipe",1,2,"Add comment",
                "Add description", "Add steps", 1);
        RecipeDto recipeDto = recipeService.convertToDto(recipe);

        Assert.assertEquals(recipe.getName(), recipeDto.getName());
        Assert.assertEquals(recipe.getSteps(), recipeDto.getSteps());
        Assert.assertEquals(recipe.getDescription(), recipeDto.getDescription());
        Assert.assertEquals(recipe.getComment(), recipeDto.getComment());
        Assert.assertEquals(recipe.getCookingMethodId(),
                cookingMethodService.getCookingMethodIdbyName(recipeDto.getCookingMethod().name()));
        Assert.assertEquals(recipe.getCategoryId(), categoryService.getCategoryId(recipeDto.getCategory().name()));
        Assert.assertEquals(recipe.getUserId(), usersService.getIdByUsername(recipeDto.getUsername()));
    }

    @Test
    public void convertToDtoMandatoryFieldsOnlyTest() {
        Recipe recipe = CommonMethods.createRecipe("Saved Recipe",null,null,null,
                null, null, null);
        RecipeDto recipeDto = recipeService.convertToDto(recipe);

        Assert.assertEquals(recipe.getName(), recipeDto.getName());
        Assert.assertNull(recipeDto.getUsername());
        Assert.assertNull(recipeDto.getSteps());
        Assert.assertNull(recipeDto.getDescription());
        Assert.assertNull(recipeDto.getComment());
        Assert.assertNull(recipeDto.getCookingMethod());
        Assert.assertNull(recipeDto.getCategory());
    }

    @Test
    public void convertToDtoAllRecipes() {
        Recipe recipe = CommonMethods.createRecipe("Saved Recipe",1,2,"Add comment",
                "Add description", "Add steps", 1);
        Recipe recipe2 = CommonMethods.createRecipe("Saved Recipe",null,null,null,
                null, null, null);
        recipe.setId(1);
        recipe2.setId(2);

        List<Recipe> recipes = new ArrayList<>();
        recipes.add(recipe);
        recipes.add(recipe2);

        List<AllRecipesDto> recipesDto = recipeService.convertToDto(recipes);
        Assert.assertEquals(recipes.size(), recipesDto.size());
        Assert.assertEquals(recipe.getId().intValue(), recipesDto.get(0).getId());
        Assert.assertEquals(recipe2.getId().intValue(), recipesDto.get(1).getId());
        Assert.assertEquals(recipe.getName(), recipesDto.get(0).getName());
        Assert.assertEquals(recipe2.getName(), recipesDto.get(1).getName());
    }
}
