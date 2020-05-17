package what.to.eat.services;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class is responsible for testing CookingMethodService
 */
public class CookingMethodServiceTest extends ServiceTest {

    @Autowired
    private CookingMethodService cookingMethodService;


    @Test
    @DatabaseSetup("/db/scripts/CookingMethod.xml")
    public void getCookingMethodNameByIdTest() {
        String baking = cookingMethodService.getCookingMethodNameById(1);
        Assert.assertEquals("BAKING", baking);
    }

    @Test
    @DatabaseSetup("/db/scripts/CookingMethod.xml")
    public void getCookingMethodNameByNoExististingIdTest() {
        Assert.assertNull(cookingMethodService.getCookingMethodNameById(-1));
        Assert.assertNull(cookingMethodService.getCookingMethodNameById(0));
        Assert.assertNull(cookingMethodService.getCookingMethodNameById(100));
        Assert.assertNull(cookingMethodService.getCookingMethodNameById(null));
    }

    @Test
    @DatabaseSetup("/db/scripts/CookingMethod.xml")
    public void getCookingMethodIdTest() {
        int bakingUpperCaseId = cookingMethodService.getCookingMethodIdbyName("BAKING");
        int bakingLowerCaseId = cookingMethodService.getCookingMethodIdbyName("BAKING");
        int fryingId = cookingMethodService.getCookingMethodIdbyName("FRYING");
        int roastingId =cookingMethodService.getCookingMethodIdbyName("ROASTING");
        int grillingId = cookingMethodService.getCookingMethodIdbyName("GRILLING");
        int steamingId = cookingMethodService.getCookingMethodIdbyName("STEAMING");
        int poachingId = cookingMethodService.getCookingMethodIdbyName("POACHING");
        int simmeringId = cookingMethodService.getCookingMethodIdbyName("SIMMERING");
        int broilingId = cookingMethodService.getCookingMethodIdbyName("BROILING");
        int blanchingId = cookingMethodService.getCookingMethodIdbyName("BLANCHING");
        int braisingId = cookingMethodService.getCookingMethodIdbyName("BRAISING");
        int stewingId = cookingMethodService.getCookingMethodIdbyName("STEWING");

        Assert.assertEquals(1, bakingUpperCaseId);
        Assert.assertEquals(1, bakingLowerCaseId);
        Assert.assertEquals(2, fryingId);
        Assert.assertEquals(3, roastingId);
        Assert.assertEquals(4, grillingId);
        Assert.assertEquals(5, steamingId);
        Assert.assertEquals(6, poachingId);
        Assert.assertEquals(7, simmeringId);
        Assert.assertEquals(8, broilingId);
        Assert.assertEquals(9, blanchingId);
        Assert.assertEquals(10, braisingId);
        Assert.assertEquals(11, stewingId);

    }

    @Test
    @DatabaseSetup("/db/scripts/CookingMethod.xml")
    public void getCookingMethodIdNullNameTest() {
        Assert.assertNull(cookingMethodService.getCookingMethodIdbyName(null));
        Assert.assertNull(cookingMethodService.getCookingMethodIdbyName(""));
        Assert.assertNull(cookingMethodService.getCookingMethodIdbyName(" "));
    }

    @Test
    @DatabaseSetup("/db/scripts/CookingMethod.xml")
    public void getCookingMethodIdNotExistingNameTest() {
        Assert.assertNull(cookingMethodService.getCookingMethodIdbyName("Test"));
    }

}
