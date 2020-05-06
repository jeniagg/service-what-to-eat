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
     * Returns list of all recipes filtered by category
     * If the category is not passed, recipes from all categories are returned
     *
     * @param categoryName - name of the category used for filtering
     * @return all recipes
     */
    public List<Recipe> getAllRecipes(String categoryName) {
        if (categoryName == null || categoryName.isEmpty()) {
            return recipeRepo.findAll();
        }
        Integer categoryId = categoryService.getCategoryId(categoryName);
        return getAllRecipesByCategory(categoryId);
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
     * Retrieve all recipes from specific category
     * @param categoryId - id of the category
     * @return All recipes from the specified category
     */
    public List<Recipe> getAllRecipesByCategory(Integer categoryId) {
        if (categoryId != null && categoryId > 0) {
            return recipeRepo.getAllRecipesByCategoryId(categoryId);
        }
        return new ArrayList<>();
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
