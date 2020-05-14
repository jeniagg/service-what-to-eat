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
    public void getCategoryNameByIdTest() {
       String salad = categoryService.getCategoryName(1);
       Assert.assertEquals("APPETIZER", salad);
    }

    @Test
    @DatabaseSetup("/db/scripts/Category.xml")
    public void getCategoryNameByNoExististingIdTest() {
      Assert.assertNull(categoryService.getCategoryName(-1));
      Assert.assertNull(categoryService.getCategoryName(0));
      Assert.assertNull(categoryService.getCategoryName(100));
      Assert.assertNull(categoryService.getCategoryName(null));
    }

    @Test
    @DatabaseSetup("/db/scripts/Category.xml")
    public void getCategoryIdTest() {
        int appetizerId = categoryService.getCategoryId("APPETIZER");
        int saladUpperCaseId = categoryService.getCategoryId("SALAD");
        int saladLowerCaseId = categoryService.getCategoryId("salad");
        int entreeId = categoryService.getCategoryId("ENTREE");
        int mainId = categoryService.getCategoryId("MAIN");
        int dessertId = categoryService.getCategoryId("DESSERT");
        int beverageId = categoryService.getCategoryId("BEVERAGE");

        Assert.assertEquals(1, appetizerId);
        Assert.assertEquals(2, saladUpperCaseId);
        Assert.assertEquals(2, saladLowerCaseId);
        Assert.assertEquals(3, entreeId);
        Assert.assertEquals(4, mainId);
        Assert.assertEquals(5, dessertId);
        Assert.assertEquals(6, beverageId);
    }

    @Test
    @DatabaseSetup("/db/scripts/Category.xml")
    public void getCategoryIdNullNameTest() {
        Assert.assertNull(categoryService.getCategoryId(null));
        Assert.assertNull(categoryService.getCategoryId(""));
        Assert.assertNull(categoryService.getCategoryId(" "));
    }

    @Test
    @DatabaseSetup("/db/scripts/Category.xml")
    public void getCategoryIdNotExistingNameTest() {
        Assert.assertNull(categoryService.getCategoryId("Test"));
    }

}
