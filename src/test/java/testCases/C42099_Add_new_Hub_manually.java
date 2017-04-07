package testCases;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.Base;

/**
 * PRECONDITION:
 * Account has no hub
 */
public class C42099_Add_new_Hub_manually{

    private String login, pass, server, hubName, hubMasterKey, expected, actual;
    private WebElement[] elements;
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

        hubName = $.getCredsWithKey("hubName");
        hubMasterKey = $.getCredsWithKey("hubMasterKey");

//        String hubKey = "12345123451234512345";

        $.loginPage.loginWithPinCancel(login, pass, server);
    }

    @Test(priority = 1, enabled = true)
    public void Add_first_Hub() {

        Base.log("tap to the Plus Button");
        $.dashboard.plusButtonClick();

        Base.log("choose manual Hub adding ");
        $.nav.nextButtonClick();

        $.dashboard.fillFieldsWith(hubName, hubMasterKey);
        $.nav.confirmIt();

//        $.wait.invisibilityElementWithText(waiterText, true);
        $.wait.invisibilityOfWaiter(true);
        Assert.assertFalse($.check.isErrorPresent(3), "Hub adding failed!");

        Assert.assertTrue($.wait.element($.dashboardHeader.getGprsImage(), 15, true));
        Base.log("hub successfully added!");
        Base.log("Method is finished");
    }

    @Test(priority = 1, enabled = false)
    public void Add_Hub_from_menu() {}

    @AfterClass
    public void endSuit() {
        $.getDriver().quit();
    }
}
