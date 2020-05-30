package what.to.eat.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import what.to.eat.entities.Recipe;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {

    ArrayList<Recipe> findAll();

    @Query("SELECT r FROM Recipe r WHERE r.id = ?1")
    Recipe getRecipeById(Integer id);

    @Query("SELECT r FROM Recipe r WHERE categoryId = ?1")
    List<Recipe> getAllRecipesByCategoryId(Integer categoryId);

    @Query("SELECT r FROM Recipe r WHERE cookingMethodId = ?1")
    List<Recipe> getAllRecipesByCookingMethodId(Integer cookingMethodId);

    @Query("SELECT r FROM Recipe r WHERE categoryId = ?1 AND cookingMethodId = ?2")
    List<Recipe> getAllRecipesByCategoryIdAndCookingMethodId(Integer categoryId, Integer cookingMethodId);

    @Modifying
    @Query("UPDATE Recipe SET name = ?2, description = ?3, categoryId = ?4, cookingMethodId = ?5," +
            "steps = ?6, userId = ?7, comment = ?8 WHERE id = ?1")
    void updateRecipe(Integer id, String name, String descr, Integer catId, Integer cmId, String steps,
                        Integer userId, String comment);
}