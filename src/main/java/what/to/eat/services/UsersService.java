package what.to.eat.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import what.to.eat.repositories.UsersRepository;

import javax.transaction.Transactional;

@Service
@Transactional
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

    /***
     * Returns userId by the given username
     *
     * @param username - the given username
     * @return userId if username is not empty or null
     * @return null otherwise
     */
    public Integer getIdByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            return null;
        }
        return usersRepository.getIdByUsername(username);
    }
}
