package what.to.eat.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import what.to.eat.repositories.CookingMethodRepository;

@Service
public class CookingMethodService {

    @Autowired
    CookingMethodRepository cookingMethodRepo;

    /**
     * Returns name of cooking method by specific id
     *
     * @param id - the specific cookingMethodId
     * @return name of the cooking method if the cookingMethodId is present and bigger than 0
     * @return null otherwise
     */
    public String getCookingMethodNameById(Integer id) {
        if (id != null && id >= 1) {
            return cookingMethodRepo.getCookingMethodNameById(id);
        }
        return null;
    }

    /**
     * Returns id of cooking method by specific name
     *
     * @param cookingMethodName - the specific cookingMethodName
     * @return id of the cooking method if the cookingMethodName is present and not null
     * @return null otherwise
     */
    public Integer getCookingMethodIdbyName(String cookingMethodName) {
        if (StringUtils.isBlank(cookingMethodName)) {
            return null;
        }
        return cookingMethodRepo.getCookingMethodIdbyName(cookingMethodName);
    }
}
