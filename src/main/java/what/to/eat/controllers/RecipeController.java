package what.to.eat.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import what.to.eat.dtos.AllRecipesDto;
import what.to.eat.dtos.RecipeDto;
import what.to.eat.entities.Recipe;
import what.to.eat.exception.WebApplicationException;
import what.to.eat.exception.WebApplicationExceptionFormat;
import what.to.eat.services.RecipeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;

/**
 * This class is responsible for all recipe related endpoints.
 */
@RestController
@RequestMapping(value = "/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Recipe Controller", description = "recipe operations controller")
public class RecipeController {

    private static final Logger LOGGER = LogManager.getLogger(RecipeController.class);

    @Autowired
    private RecipeService recipeService;

    /**
     * Retrieve all existing recipes filtered by categoryName and cookingMethodName
     * @param categoryName - the name of the category used for filtering
     * @param cookingMethodName - the name of the cooking method used for filtering
     * @return array with all existing recipes
     */
    @Operation(summary = "Retrieve all recipes.", description = "Retrieve all existing recipes." +
            " They can be filtered by category and/or cooking method. If empty values are passed," +
            " recipes from all categories/cooking methods are returned.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Query parameter is missing.",
            content = @Content(schema = @Schema()))
    })
    @GetMapping
    public ResponseEntity<List<AllRecipesDto>> getAllRecipes(
            @Parameter(description = "categoryName", allowEmptyValue = true)
            @RequestParam("category") String categoryName,
            @Parameter(description = "cookingMethodName", allowEmptyValue = true)
            @RequestParam("cookingMethod") String cookingMethodName) {

        LOGGER.info("Calling getAllRecipes() endpoint .. ");

        List<Recipe> recipes = recipeService.getAllRecipes(categoryName, cookingMethodName);

        return ResponseEntity.status(HttpStatus.OK).body(recipeService.convertToDto(recipes));
    }

    /**
     * Retrieve specific recipe by specifying its id
     *
     * @param id - recipeId which is going to be retrieved
     * @return the specified recipe with all its details
     * @throws WebApplicationException if the specified id is not present
     */
    @Operation(summary = "Retrieve a recipe.", description = "Retrieve specific recipe by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "404", description = "Recipe with this id is not found",
            content = @Content(schema = @Schema()))
    })
    @GetMapping(value = "/{id}")
    public ResponseEntity<RecipeDto> getRecipe(
            @Parameter(description = "recipeId", required = true) @PathVariable("id") Integer id) {

        LOGGER.info("Calling get specific recipe endpoint .. ");

        Recipe recipe = recipeService.getRecipeById(id);
        if (recipe == null) {
            throw new WebApplicationException("There is no such recipe id.", HttpStatus.NOT_FOUND);
        }

        RecipeDto recipeDto = recipeService.convertToDto(recipe);

        return ResponseEntity.status(HttpStatus.OK).body(recipeDto);
    }

    /**
     * Create new recipe with the given body
     * @param recipeDto - the body of the recipe to be saved
     * @return the new recipe
     */
    @Operation(summary = "Create a recipe.", description = "Create new recipe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Mandatory value is missing from the body"),
            @ApiResponse(responseCode = "415", description = "Missing body")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeDto> createRecipe( @RequestBody RecipeDto recipeDto){

        LOGGER.info("Calling create specific recipe endpoint .. ");
        Recipe recipe = recipeService.convertToEntity(recipeDto);
        Recipe newRecipe = recipeService.saveRecipe(recipe);

        LOGGER.info("The new recipe was successfully created");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(recipeService.convertToDto(newRecipe));
    }

}
