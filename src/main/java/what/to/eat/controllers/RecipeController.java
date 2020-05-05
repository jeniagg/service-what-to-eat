package what.to.eat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import what.to.eat.dtos.AllRecipesDto;
import what.to.eat.dtos.RecipeDto;
import what.to.eat.entities.Recipe;
import what.to.eat.exception.WebApplicationException;
import what.to.eat.services.RecipeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;

@Controller
public class RecipeController {

    private static final Logger LOGGER = LogManager.getLogger(RecipeController.class);

    @Autowired
    private RecipeService recipeService;

    @GetMapping(value = "/recipe", produces = "application/json")
    public ResponseEntity<List<AllRecipesDto>> getAllRecipes() {

        LOGGER.info("Calling getAllRecipes() endpoint .. ");

        List<Recipe> recipes = recipeService.getAllRecipeNames();

        return ResponseEntity.status(HttpStatus.OK).body(recipeService.convertToDto(recipes));
    }

    @GetMapping(value = "/recipe/{id}", produces = "application/json")
    public ResponseEntity<RecipeDto> getRecipe( @PathVariable("id") Integer id) {

        LOGGER.info("Calling get specific recipe endpoint .. ");

        Recipe recipe = recipeService.getRecipeById(id);
        if (recipe == null) {
            throw new WebApplicationException("There is no such recipe id.", HttpStatus.NOT_FOUND);
        }

        RecipeDto recipeDto = recipeService.convertToDto(recipe);

        return ResponseEntity.status(HttpStatus.OK).body(recipeDto);
    }

}
