package what.to.eat.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import what.to.eat.entities.Users;

@Repository
public interface UsersRepository extends CrudRepository<Users, Integer> {

    @Query("SELECT u.username FROM Users u WHERE id = ?1")
    String getUsernamebyId(Integer id);
}
