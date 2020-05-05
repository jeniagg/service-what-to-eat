package what.to.eat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import what.to.eat.dtos.AllRecipesDto;
import what.to.eat.dtos.RecipeDto;
import what.to.eat.entities.Recipe;
import what.to.eat.repositories.RecipeRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {

    @Autowired
    RecipeRepository recipeRepo;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UsersService usersService;

    public List<Recipe> getAllRecipeNames() {
        return recipeRepo.findAll();
    }

    public Recipe getRecipeById(Integer id) {
        return recipeRepo.getRecipeById(id);
    }

    public RecipeDto convertToDto(Recipe recipe) {
        String username = usersService.getUsernameById(recipe.getUserId());
        String categoryName =  categoryService.getCategoryName(recipe.getCategoryId());

        return new RecipeDto(recipe.getId(), recipe.getName(), recipe.getDescription(),
               username, recipe.getSteps(), recipe.getCookingMethodId(),
               categoryName, recipe.getComment());
    }

    public List<AllRecipesDto> convertToDto(List<Recipe> recipes) {
       List<AllRecipesDto> recipesDto = new ArrayList<>();
        for (Recipe recipe : recipes) {
            recipesDto.add(new AllRecipesDto(recipe.getId(), recipe.getName()));
        }
        return recipesDto;
    }

}
