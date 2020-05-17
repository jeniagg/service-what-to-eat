package what.to.eat.services;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class is responsible for testing UsersService
 */
public class UsersServiceTest extends ServiceTest {

    @Autowired
    private UsersService usersService;

    @Test
    @DatabaseSetup("/db/scripts/Users.xml")
    public void getUsernameById() {
        String username = usersService.getUsernameById(1);
        Assert.assertEquals("TestUser", username);
    }

    @Test
    @DatabaseSetup("/db/scripts/Users.xml")
    public void getUsernameByNonExistingId() {
        Assert.assertNull(usersService.getUsernameById(-1));
        Assert.assertNull(usersService.getUsernameById(0));
        Assert.assertNull(usersService.getUsernameById(100));
        Assert.assertNull(usersService.getUsernameById(null));

    }

    @Test
    @DatabaseSetup("/db/scripts/Users.xml")
    public void getIdByUsernameTest() {
        Integer correctNameId = usersService.getIdByUsername("TestUser");
        Integer wrongCaseId1 = usersService.getIdByUsername("testuser");
        Integer wrongCaseId2 = usersService.getIdByUsername("TESTUSER");
        Integer wrongCaseId3 = usersService.getIdByUsername("testUser");
        Integer nonExistingName = usersService.getIdByUsername("nonExisting");

        Assert.assertEquals(Integer.valueOf(1), correctNameId);
        Assert.assertNull(wrongCaseId1);
        Assert.assertNull(wrongCaseId2);
        Assert.assertNull(wrongCaseId3);
        Assert.assertNull(nonExistingName);
    }

    @Test
    @DatabaseSetup("/db/scripts/Users.xml")
    public void getIdByUsernameNullTest() {
        Assert.assertNull(usersService.getIdByUsername(null));
        Assert.assertNull(usersService.getIdByUsername(""));
        Assert.assertNull(usersService.getIdByUsername(" "));
    }

}