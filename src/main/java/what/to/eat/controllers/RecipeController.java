package what.to.eat.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import what.to.eat.dtos.RecipeDto;
import what.to.eat.entities.Recipe;
import what.to.eat.services.RecipeService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @GetMapping(value = "/recipe", produces = "application/json")
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {

        List<Recipe> recipes = recipeService.getAllRecipeNames();
        ArrayList<RecipeDto> recipeDtos = new ArrayList<>();

        for (Recipe recipe : recipes) {
            recipeDtos.add(recipeService.convertToDto(recipe));
        }

        return ResponseEntity.status(HttpStatus.OK).body(recipeDtos);
    }

}
