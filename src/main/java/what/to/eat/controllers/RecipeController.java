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
import what.to.eat.dtos.CategoryEnum;
import what.to.eat.dtos.CookingMethodEnum;
import what.to.eat.entities.Recipe;
import what.to.eat.exception.WebApplicationException;
import what.to.eat.services.CategoryService;
import what.to.eat.services.CookingMethodService;
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

    private static final String MISSING_RECIPE = "There is no such recipe id.";

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CookingMethodService cookingMethodService;

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
            throw new WebApplicationException(MISSING_RECIPE, HttpStatus.NOT_FOUND);
        }

        RecipeDto recipeDto = recipeService.convertToDto(recipe);

        return ResponseEntity.status(HttpStatus.OK).body(recipeDto);
    }

    /**
     * Create new recipe with the given body
     * @param recipeDto - the body of the recipe to be saved
     * @return the new recipe
     * @throws WebApplicationException
     */
    @Operation(summary = "Create a recipe.", description = "Create new recipe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Mandatory value is missing from the body or" +
                    " not allowed value is passed for some of the properties", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "415", description = "Missing body", content = @Content(schema = @Schema()))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeDto> createRecipe( @RequestBody RecipeDto recipeDto){

        LOGGER.info("Calling create specific recipe endpoint .. ");

        if ( (recipeDto.getCategory() != null) &&
                (!CategoryEnum.isValidCategory(recipeDto.getCategory().name())
                || categoryService.getCategoryId(recipeDto.getCategory().name()) == null)) {
            throw new WebApplicationException("There is no such category.", HttpStatus.BAD_REQUEST);
        }

        if ( (recipeDto.getCookingMethod() != null) &&
                (!CookingMethodEnum.isValidCookingMethod(recipeDto.getCookingMethod().name())
                || cookingMethodService.getCookingMethodIdbyName(recipeDto.getCookingMethod().name()) == null)) {
            throw new WebApplicationException("There is no such cooking method.", HttpStatus.BAD_REQUEST);
        }

        Recipe recipe = recipeService.convertToEntity(recipeDto);
        Recipe newRecipe = recipeService.saveRecipe(recipe);

        LOGGER.info("The new recipe was successfully created");

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(recipeService.convertToDto(newRecipe));
    }

    /**
     * Updates the recipe with the given body
     * @param id - id of the recipe to be updated
     * @param recipeDto - the body of the recipe to update the recipe with
     * @throws WebApplicationException
     */
    @Operation(summary = "Update a recipe.", description = "Update existing recipe.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully updated", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Mandatory value is missing from the body or" +
                    " not allowed value is passed for some of the properties", content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Recipe with this id is not found",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "415", description = "Missing body", content = @Content(schema = @Schema()))
    })
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeDto> updateRecipe(
            @Parameter(description = "recipeId", required = true) @PathVariable("id") Integer id,
            @RequestBody RecipeDto recipeDto){

        LOGGER.info("Calling update specific recipe endpoint .. ");

        if (recipeService.getRecipeById(id) == null) {
            throw new WebApplicationException(MISSING_RECIPE, HttpStatus.NOT_FOUND);
        }

        if ( (recipeDto.getCategory() != null) &&
                (!CategoryEnum.isValidCategory(recipeDto.getCategory().name())
                        || categoryService.getCategoryId(recipeDto.getCategory().name()) == null)) {
            throw new WebApplicationException("There is no such category.", HttpStatus.BAD_REQUEST);
        }

        if ( (recipeDto.getCookingMethod() != null) &&
                (!CookingMethodEnum.isValidCookingMethod(recipeDto.getCookingMethod().name())
                        || cookingMethodService.getCookingMethodIdbyName(recipeDto.getCookingMethod().name()) == null)) {
            throw new WebApplicationException("There is no such cooking method.", HttpStatus.BAD_REQUEST);
        }

        recipeService.updateRecipe(id, recipeDto);

        LOGGER.info("The recipe with id {} was successfully updated", id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * Delete specific recipe by specifying its id
     *
     * @param id - recipeId which is going to be deleted
     * @throws WebApplicationException if the specified id is not present
     */
    @Operation(summary = "Delete a recipe.", description = "Delete specific recipe by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful operation",
                    content = @Content(schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Recipe with this id is not found",
                    content = @Content(schema = @Schema()))
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Recipe> deleteRecipe(
            @Parameter(description = "recipeId", required = true) @PathVariable("id") Integer id) {

        LOGGER.info("Calling delete recipe endpoint .. ");

        Recipe recipe = recipeService.getRecipeById(id);
        if (recipe == null) {
            throw new WebApplicationException(MISSING_RECIPE, HttpStatus.NOT_FOUND);
        }
        recipeService.deleteRecipe(id);

        LOGGER.info("Recipe with id {} deleted successfully .. ", id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
