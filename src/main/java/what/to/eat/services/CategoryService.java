package what.to.eat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import what.to.eat.repositories.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepo;

    /**
     * Returns name of the category by specific id
     *
     * @param id - the given categoryId
     * @return categoryName if the given id is present and bigger than 0
     * @return null otherwise
     */
    public String getCategoryName(Integer id) {
        if(id != null && id >= 1) {
            return categoryRepo.getCategoryNameById(id);
        }
        return null;
    }

    /**
     * Returns id of the category by specific name
     * @param categoryName - the given categoryName
     * @return the id of the category if the name is not empty or null
     * @return null otherwise
     */
    public Integer getCategoryId(String categoryName) {
        if (categoryName == null || categoryName.isEmpty()) {
            return null;
        }
        return categoryRepo.getCategoryIdByName(categoryName);
    }

}
