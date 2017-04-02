package testCases;

import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import pages.Base;

/**
 * Created by installer on 3/28/17.
 */
public class C42102_Add_new_guest_user extends Base {

    private String login, pass, server, expected, actual;

    public C42102_Add_new_guest_user(AppiumDriver driver, String locale_) {
        super(driver, locale_);
        log("TEST IS STARTED");

        login = "ajax1@i.ua";
        pass = "qwe123";
        server = "Develop";

        log("start from IntroPage");
        introPage.goToAuthorization();
        loginPage.loginToTheServer(login, pass, server);

        log("waiting for Pincode PopUp and cancel it");
        check.waitElementWithoutPin(dashboardHeader.menuDrawer, 15);

        hub.goToTheUserlistPage();

        user.addFromEmailField();
        Assert.assertTrue(user.checkIsNewUsersAddedBy("text", user.getUsersForEmailField()), "Add users from Email Field is failed");

        user.addFromContactList();
        Assert.assertTrue(user.checkIsNewUsersAddedBy("text", user.getUsersForContactList()), "Add users from Contact List is failed");

        user.addMixedUsers();
        Assert.assertTrue(user.checkIsNewUsersAddedBy("text", user.getUsersForMixedAdd()), "Add users with mixed style is failed");

        String unregisteredUserEmail = user.getUsersForMixedAdd().get(1);
        Assert.assertTrue(user.checkDeleteIconIsPresent(unregisteredUserEmail), "Unregistered user has no DELETE icon");

        log("TEST IS FINISHED");
    }

}
