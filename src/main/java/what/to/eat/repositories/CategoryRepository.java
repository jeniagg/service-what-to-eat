package what.to.eat.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import what.to.eat.entities.Category;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

    @Query("SELECT c.name FROM Category c WHERE id = ?1")
    String getCategoryNameById(Integer id);

    @Query("SELECT c.id FROM Category c WHERE name = ?1")
    Integer getCategoryIdByName(String name);
}
