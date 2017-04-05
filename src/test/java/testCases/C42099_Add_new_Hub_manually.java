package testCases;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.Base;

/**
 * PRECONDITION:
 * Account has no hub
 */
public class C42099_Add_new_Hub_manually extends Base {

    private String login, pass, server, expected, actual;
    private WebElement[] elements;
    private Base $;

    @BeforeClass
    public void init(){
        $ = new Base(getDriver());
    }

    @Test(priority = 1, enabled = true)
    public void Add_first_Hub() {
        log("Method is started");
        String waiterText = getLocalizeTextForKey("request_send");

        log("get credentials for login");
        login = creds.get("login").toString();
        pass = creds.get("password").toString();
        server = creds.get("server").toString();

        String hubName = "1495";
        String hubKey = "00001495DDFB55691000";
//        String hubKey = "12345123451234512345";

        $.loginPage.loginWithPinCancel(login, pass, server);

        log("tap to the Plus Button");
        $.dashboard.plusButtonClick();

        log("choose manual Hub adding ");
        $.nav.nextButtonClick();

        $.dashboard.fillFieldsWith(hubName, hubKey);
        $.nav.confirmIt();

        $.wait.invisibilityElementWithText(waiterText, true);
        Assert.assertFalse($.check.isErrorPresent(3), "Hub adding failed!");

        Assert.assertTrue($.wait.element($.dashboardHeader.getGprsImage(), 15, true));
        log("hub successfully added!");
        log("Method is finished");
    }

    @Test(priority = 1, enabled = false)
    public void Add_Hub_from_menu() {}

    @AfterClass
    public void endSuit() {
        driver.quit();
    }
}
