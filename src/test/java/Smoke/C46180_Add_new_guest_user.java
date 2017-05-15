package Smoke;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.Base;

import java.util.ArrayList;
import java.util.List;

/**
 * PRECONDITION
 * There are:
 * - at least one Hub with Master permission
 */

public class C46180_Add_new_guest_user {

    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());
        base.loginPage.loginWithPinCancel();
        base.nav.gotoPage.userList();
    }

    @Test(priority = 1, enabled = true)
    public void From_Email_Field() {
        Base.log(1, "START TEST");
        Assert.assertTrue(base.user.add.fromEmailField(), "Process of adding users from Email Field is failed\n");
        Assert.assertTrue(base.check.isPresent.elementsWith.texts(base.user.getUsersForEmailField()), "Add users from Email Field is failed\n");
    }

    @Test(priority = 2, enabled = true)
    public void From_Contact_List() {
        Base.log(1, "START TEST");
        Assert.assertTrue(base.user.add.fromContactList(), "Process of adding users from Contact List is failed\n");
        Assert.assertTrue(base.check.isPresent.elementsWith.texts(base.user.getUsersForContactList()), "Add users from Contact List is failed\n");
    }

    @Test(priority = 3, enabled = true)
    public void Add_Mixed_Users() {
        Base.log(1, "START TEST");
        Assert.assertTrue(base.user.add.fromDifferentWays(), "Process of adding users with different ways is failed\n");
        Assert.assertTrue(base.check.isPresent.elementsWith.texts(base.user.getUsersForMixedAdd()), "Add users with different ways is failed\n");
    }

    @Test(priority = 4, enabled = true)
    public void Check_is_unreg_user_in_pending_list_has_delete_button() {
        Base.log(1, "START TEST");
        String unregisteredUserEmail = base.user.getUsersForMixedAdd().get(1);
        Assert.assertTrue(base.check.isPresent.button.deletePendingUser(unregisteredUserEmail), "Pending user has no DELETE icon\n");
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
