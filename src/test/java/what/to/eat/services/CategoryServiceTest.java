package what.to.eat.services;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class is responsible for testing CategoryService
 */
public class CategoryServiceTest extends ServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    @DatabaseSetup("/db/scripts/Category.xml")
    public void getCategoryIdTest() {
        Assert.assertNull(categoryService.getCategoryId(null));
        Assert.assertNull(categoryService.getCategoryId(""));
        int catId = categoryService.getCategoryId("SALAD");
        Assert.assertEquals(1, catId);
    }
}
