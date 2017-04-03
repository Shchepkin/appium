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
    public void driverInit(){
        $ = new Base(getDriver());
    }

    @Test(priority = 1, enabled = true)
    public void Add_first_Hub() {
        log("Method is started");
        String waiterText = getLocalizeTextForKey("request_send");

        login = "ajax1@i.ua";
        pass = "qwe123";
        server = "Develop";
        String hubName = "1495";
        String hubKey = "00001495DDFB55691000";
//        String hubKey = "12345123451234512345";

        log("start from IntroPage");
        $.introPage.goToAuthorization();
        $.loginPage.loginToTheServer(login, pass, server);

        $.wait.invisibilityOfLoaderLogo(true);
        Assert.assertFalse($.check.forSnackBarIsPresent(2), "SnackBar is shown");

        log("waiting for Pincode PopUp and cancel it");
        $.check.waitElementWithoutPin($.dashboardHeader.getMenuDrawer(), 15);

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
