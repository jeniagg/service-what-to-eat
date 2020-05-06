package what.to.eat.repositories;

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
}