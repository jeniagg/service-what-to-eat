package what.to.eat.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import what.to.eat.entities.Users;

import java.util.ArrayList;

@Repository
public interface UsersRepository extends CrudRepository<Users, Integer> {

    ArrayList<Users> findAll();

    @Query("SELECT u.username FROM Users u WHERE id = ?1")
    String getUsernamebyId(Integer id);

    @Query("SELECT u.id FROM Users u WHERE username = ?1")
    Integer getIdByUsername(String username);

    @Query("SELECT u FROM Users u WHERE username = ?1")
    Users getUserByUsername(String username);
}
