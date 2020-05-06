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

    @Autowired
    CookingMethodService cookingMethodService;

    /**
     * Returns list of all recipes
     * @return all recipes
     */
    public List<Recipe> getAllRecipes() {
        return recipeRepo.findAll();
    }

    /**
     * Returns specific recipe by the given id
     * @param id - the specific recipeId
     * @return recipe if the id is present and is bigger than 0
     * @return null otherwise
     */
    public Recipe getRecipeById(Integer id) {
        if (id != null && id > 0) {
            return recipeRepo.getRecipeById(id);
        }
        return null;
    }

    /**
     * Transform recipe from {@link Recipe} to {@link RecipeDto}
     * @param recipe - the recipe to be transformed
     * @return recipeDto - the transformed recipe
     */
    public RecipeDto convertToDto(Recipe recipe) {
        String username = usersService.getUsernameById(recipe.getUserId());
        String categoryName =  categoryService.getCategoryName(recipe.getCategoryId());
        String cookingMethodName = cookingMethodService.getCookingMethodNameById(recipe.getCookingMethodId());

        return new RecipeDto(recipe.getId(), recipe.getName(), recipe.getDescription(),
               username, recipe.getSteps(), cookingMethodName,
               categoryName, recipe.getComment());
    }

    /**
     * Transform recipes from {@link Recipe} to {@link AllRecipesDto}
     * @param recipes - the recipes to be transformed
     * @return recipesDto - the transformed recipes
     */
    public List<AllRecipesDto> convertToDto(List<Recipe> recipes) {
        List<AllRecipesDto> recipesDto = new ArrayList<>();
        for (Recipe recipe : recipes) {
            recipesDto.add(new AllRecipesDto(recipe.getId(), recipe.getName()));
        }
        return recipesDto;
    }

}
