package what.to.eat.controllers;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import what.to.eat.CommonMethods;
import what.to.eat.dtos.CategoryEnum;
import what.to.eat.dtos.CookingMethodEnum;
import what.to.eat.dtos.RecipeDto;
import what.to.eat.entities.Recipe;
import what.to.eat.exception.WebApplicationException;
import what.to.eat.services.RecipeService;

/**
 * This class is responsible for testing RecipeController
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RecipeControllerTest {

    @InjectMocks
    RecipeController recipeController;

    @Mock
    RecipeService recipeService;

    @Test
    public void getRecipeTest() {
        Recipe recipe = CommonMethods.createRecipe("Saved Recipe", 1, 2, "Add comment",
                "Add description", "Add steps", 1);
        recipe.setId(1);
        RecipeDto recipeDto = new RecipeDto(1, "Saved Recipe", "Add description", "User",
                "Add steps", CookingMethodEnum.valueOf("BAKING"), CategoryEnum.valueOf("MAIN"), "Add comment");

        Mockito.when(recipeService.getRecipeById(Mockito.anyInt())).thenReturn(recipe);
        Mockito.when(recipeService.convertToDto(Mockito.any(Recipe.class))).thenReturn(recipeDto);

        ResponseEntity<RecipeDto> result = recipeController.getRecipe(1);
        RecipeDto resultRecipe = result.getBody();

        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(recipe.getId(), resultRecipe.getId());
        Assert.assertEquals(recipe.getName(), recipeDto.getName());
    }

    @Test
    public void getRecipeMandatoryFieldOnlyTest() {
        Recipe recipe = CommonMethods.createRecipe("Saved Recipe", null, null, null,
                null, null, null);
        recipe.setId(1);
        RecipeDto recipeDto = new RecipeDto(1, "Saved Recipe", null, null,
                null, null,null, null);

        Mockito.when(recipeService.getRecipeById(Mockito.anyInt())).thenReturn(recipe);
        Mockito.when(recipeService.convertToDto(Mockito.any(Recipe.class))).thenReturn(recipeDto);

        ResponseEntity<RecipeDto> result = recipeController.getRecipe(1);
        RecipeDto resultRecipe = result.getBody();

        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(recipe.getId(), resultRecipe.getId());
        Assert.assertEquals(recipe.getName(), recipeDto.getName());
    }

    @Test(expected = WebApplicationException.class)
    public void getRecipeNonExistingIdTest() {
        Mockito.when(recipeService.getRecipeById(Mockito.anyInt())).thenReturn(null);

        try {
            recipeController.getRecipe(2);
        } catch (WebApplicationException ex) {
            Assert.assertEquals("There is no such recipe id.", ex.getMessage());
            Assert.assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
            throw ex;
        }
    }

    @Test
    public void deleteRecipeTest() {
        Recipe recipe = CommonMethods.createRecipe("Saved Recipe", null, null, null,
                null, null, null);
        recipe.setId(2);
        Mockito.when(recipeService.getRecipeById(2)).thenReturn(recipe);

        ResponseEntity responseEntity = recipeController.deleteRecipe(2);
        Assert.assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

    }

    @Test(expected = WebApplicationException.class)
    public void deleteRecipeNonExistingIdTest() {
        Mockito.when(recipeService.getRecipeById(Mockito.anyInt())).thenReturn(null);

        try {
            recipeController.deleteRecipe(2);
        } catch (WebApplicationException ex) {
            Assert.assertEquals("There is no such recipe id.", ex.getMessage());
            Assert.assertEquals(HttpStatus.NOT_FOUND, ex.getStatus());
            throw ex;
        }
    }
}

