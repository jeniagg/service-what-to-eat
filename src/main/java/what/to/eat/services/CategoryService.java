package what.to.eat.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import what.to.eat.dtos.CategoryDto;
import what.to.eat.entities.Category;
import what.to.eat.repositories.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepo;

    public Category findCategory(Integer id) {
        return categoryRepo.getCategoryById(id);
    }

    public CategoryDto convertToDto(Integer id) {
        Category category = findCategory(id);
        return new CategoryDto(id, category.getName());
    }
}
