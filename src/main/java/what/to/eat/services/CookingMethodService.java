package what.to.eat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import what.to.eat.repositories.CookingMethodRepository;

@Service
public class CookingMethodService {

    @Autowired
    CookingMethodRepository cookingMethodRepo;

    public String getCookingMethodNameById(Integer id) {
        if (id != null && id >= 1) {
            return cookingMethodRepo.getCookingMethodNameById(id);
        }
        return null;
    }
}
