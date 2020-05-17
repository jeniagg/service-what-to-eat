package what.to.eat.dtos;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This class is responsible for testing CookingMethodEnum
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CookingMethodEnumTest {

    @Test
    public void isValidCookingMethodTest() {
        Boolean validSmallerCase = CookingMethodEnum.isValidCookingMethod("baking");
        Boolean validCase = CookingMethodEnum.isValidCookingMethod("BakinG");
        Boolean validUpperCase = CookingMethodEnum.isValidCookingMethod("BAKING");
        Boolean nullCookingMethod = CookingMethodEnum.isValidCookingMethod(null);

        Boolean wrongCookingMethod = CookingMethodEnum.isValidCookingMethod("notExsiting");
        Boolean emptyCookingMethod = CookingMethodEnum.isValidCookingMethod("");
        Boolean spaceCookingMethod = CookingMethodEnum.isValidCookingMethod(" ");

        Assert.assertTrue(validSmallerCase);
        Assert.assertTrue(validUpperCase);
        Assert.assertTrue(validCase);
        Assert.assertTrue(nullCookingMethod);

        Assert.assertFalse(wrongCookingMethod);
        Assert.assertFalse(emptyCookingMethod);
        Assert.assertFalse(spaceCookingMethod);
    }
}