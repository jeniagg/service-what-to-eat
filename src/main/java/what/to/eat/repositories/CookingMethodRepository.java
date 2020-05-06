package what.to.eat.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import what.to.eat.entities.CookingMethod;

@Repository
public interface CookingMethodRepository extends CrudRepository<CookingMethod, Integer> {

    @Query("SELECT cm.name FROM CookingMethod cm WHERE cm.id = ?1")
    String getCookingMethodNameById(Integer id);

    @Query("SELECT cm.id FROM CookingMethod cm WHERE cm.name = ?1")
    Integer getCookingMethodIdbyName(String name);
}
