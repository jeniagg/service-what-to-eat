package what.to.eat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import what.to.eat.repositories.UsersRepository;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    /***
     * Returns username by the given userId
     *
     * @param id - the given userId
     * @return username if id is present and bigger than 0
     * @return null otherwise
     */
    public String getUsernameById(Integer id) {
        if (id != null && id >= 1) {
            return usersRepository.getUsernamebyId(id);
        }
        return null;
    }
}
