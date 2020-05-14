package what.to.eat.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import what.to.eat.dtos.AllRecipesDto;
import what.to.eat.dtos.RecipeDto;
import what.to.eat.dtos.CategoryEnum;
import what.to.eat.dtos.CookingMethodEnum;
import what.to.eat.entities.Recipe;
import what.to.eat.repositories.RecipeRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
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
     * Returns list of all recipes filtered by category and cooking method
     * If the category and/or cooking method is not passed,
     * recipes from all categories and cooking methods are returned
     *
     * @param categoryName - name of the category used for filtering
     * @param cookingMethodName - name of the cooking method used for filtering
     * @return all recipes
     */
    public List<Recipe> getAllRecipes(String categoryName, String cookingMethodName) {
        if (StringUtils.isBlank(categoryName) && StringUtils.isBlank(cookingMethodName)) {
            return recipeRepo.findAll();
        }
        if (StringUtils.isBlank(categoryName)) {
            return getAllRecipesByCookingMethod(cookingMethodName);
        }
        if (StringUtils.isBlank(cookingMethodName)) {
            return getAllRecipesByCategory(categoryName);
        }
        return getAllRecipesByCategoryAndCookingMethod(categoryName, cookingMethodName);
    }

    /**
     * Retrieve recipes filtered by category
     * @param categoryName - name of the category used for filtering
     * @return all recipes from the specified category
     */
    private List<Recipe> getAllRecipesByCategory(String categoryName) {
        Integer categoryId = categoryService.getCategoryId(categoryName);
        return recipeRepo.getAllRecipesByCategoryId(categoryId);
    }

    /**
     * Retrieve recipes filtered by cooking method
     * @param cookingMethodName - name of the cooking method used for filtering
     * @return all recipes from the specified cooking method
     */
    private List<Recipe> getAllRecipesByCookingMethod(String cookingMethodName) {
        Integer cookingMethodId = cookingMethodService.getCookingMethodIdbyName(cookingMethodName);
        return recipeRepo.getAllRecipesByCookingMethodId(cookingMethodId);
    }

    /**
     * Retrieve recipes filtered by category and cooking method
     * @param categoryName - name of the category used for filtering
     * @param cookingMethodName - name of the cooking method used for filtering
     * @return all recipes from the specified category and cooking method
     */
    private List<Recipe> getAllRecipesByCategoryAndCookingMethod(String categoryName, String cookingMethodName) {
        Integer categoryId = categoryService.getCategoryId(categoryName);
        Integer cookingMethodId = cookingMethodService.getCookingMethodIdbyName(cookingMethodName);
        return recipeRepo.getAllRecipesByCategoryIdAndCookingMethodId(categoryId, cookingMethodId);
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
     * Saves the given recipe to the database
     * @param recipe - the recipe to be saved
     * @return saved recipe
     */
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepo.save(recipe);
    }

    /**
     * Transform recipe from {@link RecipeDto} to {@link Recipe}
     * @param recipeDto - the recipeDto to be transformed
     * @return recipe - the transformed recipe
     */
    public Recipe convertToEntity(RecipeDto recipeDto) {
        Recipe recipe = new Recipe();
        Integer categoryId = null;
        Integer cookingMethodId = null;
        Integer userId = usersService.getIdByUsername(recipeDto.getUsername());

        if (recipeDto.getCategory() != null) {
            categoryId = categoryService.getCategoryId(recipeDto.getCategory().name());
        }

        if (recipeDto.getCookingMethod() != null) {
            cookingMethodId = cookingMethodService.getCookingMethodIdbyName(recipeDto.getCookingMethod().name());
        }

        recipe.setCategoryId(categoryId);
        recipe.setComment(recipeDto.getComment());
        recipe.setCookingMethodId(cookingMethodId);
        recipe.setDescription(recipeDto.getDescription());
        recipe.setName(recipeDto.getName());
        recipe.setSteps(recipeDto.getSteps());
        recipe.setUserId(userId);

        return recipe;
    }

    /**
     * Transform recipe from {@link Recipe} to {@link RecipeDto}
     * @param recipe - the recipe to be transformed
     * @return recipeDto - the transformed recipe
     */
    public RecipeDto convertToDto(Recipe recipe) {
        String username = usersService.getUsernameById(recipe.getUserId());
        CategoryEnum category =  null;
        CookingMethodEnum cookingMethod = null;

        if (recipe.getCategoryId() != null) {
            category = CategoryEnum.valueOf(categoryService.getCategoryName(recipe.getCategoryId()));
        }
        if (recipe.getCookingMethodId() != null) {
            cookingMethod = CookingMethodEnum.valueOf(cookingMethodService.getCookingMethodNameById(recipe.getCookingMethodId()));
        }

        return new RecipeDto(recipe.getId(), recipe.getName(), recipe.getDescription(),
               username, recipe.getSteps(), cookingMethod,
               category, recipe.getComment());
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
