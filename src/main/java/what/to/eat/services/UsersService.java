package what.to.eat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import what.to.eat.repositories.UsersRepository;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;

    public String getUsernameById(Integer id){
        if (id != null && id >= 1) {
           return usersRepository.getUsernamebyId(id);
        }
        return null;
    }
}
