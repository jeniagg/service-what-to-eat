package what.to.eat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import what.to.eat.dtos.RecipeDto;
import what.to.eat.entities.Recipe;
import what.to.eat.repositories.RecipeRepository;

import java.util.List;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepo;

    public List<Recipe> getAllRecipeNames() {
        return recipeRepo.findAll();
    }

    public RecipeDto convertToDto(Recipe recipe) {
        return new RecipeDto(recipe.getName(),recipe.getId());
    }

}
