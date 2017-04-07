package testCases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.Base;

public class C42102_Add_new_guest_user{

    private String login, pass, server, expected, actual;
    private Base $;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        $ = new Base(deviceName_);
        $.initPageObjects($.getDriver());

        Base.log("get credentials for login");
        login = $.getCredsWithKey("login");
        pass = $.getCredsWithKey("password");
        server = $.getCredsWithKey("server");

        Base.log("login without Pin");
        $.loginPage.loginWithPinCancel(login, pass, server);

        $.hub.goToTheUserlistPage();
    }

    @Test(priority = 1, enabled = true)
    public void From_Email_Field() {

        $.user.addFromEmailField();
        Assert.assertTrue($.user.checkIsNewUsersAddedBy("text", $.user.getUsersForEmailField()), "Add users from Email Field is failed");
    }

    @Test(priority = 2, enabled = true)
    public void From_Contact_List() {

        $.user.addFromContactList();
        Assert.assertTrue($.user.checkIsNewUsersAddedBy("text", $.user.getUsersForContactList()), "Add users from Contact List is failed");
    }

    @Test(priority = 3, enabled = true)
    public void Add_Mixed_Users() {

        $.user.addMixedUsers();
        Assert.assertTrue($.user.checkIsNewUsersAddedBy("text", $.user.getUsersForMixedAdd()), "Add users with mixed style is failed");
    }

    @Test(priority = 3, enabled = true)
    public void Check_is_unreg_user_in_pending_list() {

        String unregisteredUserEmail = $.user.getUsersForMixedAdd().get(1);
        Assert.assertTrue($.user.checkIsDeleteIconPresent(unregisteredUserEmail), "Unregistered user has no DELETE icon");
    }

    @AfterClass
    public void endSuit() {
        $.getDriver().quit();
    }
}
