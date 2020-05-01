package what.to.eat.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import what.to.eat.entities.Recipe;

import java.util.ArrayList;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
    public ArrayList<Recipe> findAll();
}