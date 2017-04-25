package testCases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.Base;

/**
 * PRECONDITION
 * There are:
 * - at least one Hub
 */

public class C42102_Add_new_guest_user{

    private String login, pass, server, expected, actual;
    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());

        Base.log(1, "login without Pin");
        base.loginPage.loginWithPinCancel();

        base.nav.gotoPage.userList();
    }

    @Test(priority = 1, enabled = true)
    public void From_Email_Field() {

        base.user.addFromEmailField();
        Assert.assertTrue(base.user.checkIsNewUsersAddedBy("text", base.user.getUsersForEmailField()), "Add users from Email Field is failed");
    }

    @Test(priority = 2, enabled = true)
    public void From_Contact_List() {

        base.user.addFromContactList();
        Assert.assertTrue(base.user.checkIsNewUsersAddedBy("email", base.user.getUsersForContactList()), "Add users from Contact List is failed");
    }

    @Test(priority = 3, enabled = true)
    public void Add_Mixed_Users() {

        base.user.addMixedUsers();
        Assert.assertTrue(base.user.checkIsNewUsersAddedBy("text", base.user.getUsersForMixedAdd()), "Add users with mixed style is failed");
    }

    @Test(priority = 4, enabled = true)
    public void Check_is_unreg_user_in_pending_list() {

        String unregisteredUserEmail = base.user.getUsersForMixedAdd().get(1);
        Assert.assertTrue(base.user.checkIsDeleteIconPresent(unregisteredUserEmail), "Unregistered user has no DELETE icon");
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
