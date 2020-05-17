package what.to.eat.dtos;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This class is responsible for testing CategoryEnum
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CategoryEnumTest {

    @Test
    public void isValidCategoryTest() {
        Boolean validSmallerCase = CategoryEnum.isValidCategory("salad");
        Boolean validCase = CategoryEnum.isValidCategory("SalaD");
        Boolean validUpperCase = CategoryEnum.isValidCategory("SALAD");
        Boolean nullCategory = CategoryEnum.isValidCategory(null);

        Boolean wrongCategory = CategoryEnum.isValidCategory("notExsiting");
        Boolean emptyCategory = CategoryEnum.isValidCategory("");
        Boolean spaceCategory = CategoryEnum.isValidCategory(" ");

        Assert.assertTrue(validSmallerCase);
        Assert.assertTrue(validUpperCase);
        Assert.assertTrue(validCase);
        Assert.assertTrue(nullCategory);

        Assert.assertFalse(wrongCategory);
        Assert.assertFalse(emptyCategory);
        Assert.assertFalse(spaceCategory);
    }

}