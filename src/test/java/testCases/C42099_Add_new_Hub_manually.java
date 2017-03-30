package testCases;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.Base;

/**
 * Created by installer on 3/28/17.
 */
public class C42099_Add_new_Hub_manually  extends Base {

    private String login, pass, server, expected, actual;

    public C42099_Add_new_Hub_manually(AppiumDriver driver, String locale_) {
        super(driver, locale_);
        log("Method is started");

        login = "ajax1@i.ua";
        pass = "qwe";
        server = "Develop";
        String hubName = "1495";
        String hubKey = "00001495DDFB55691000";
        WebElement[] elements;

        log("start from IntroPage");
        introPage.goToAuthorization();
        authorizationPage.loginToTheServer(login, pass, server);

        log("waiting for Pincode PopUp");

        elements = new WebElement[]{dashboardHeader.menuDrawer, popUp.cancelButton};
        if (check.waitElements(elements, 3) == 2){popUp.cancelButton.click();}

        dashboard.plusBtn.click();
        nav.nextButtonClick();
        dashboard.nameField.sendKeys(hubName);
        dashboard.hubKeyField.sendKeys(hubKey);
        dashboard.addBtn.click();

        elements = new WebElement[]{dashboardHeader.hubImage, popUp.cancelButton};
        if (check.waitElements(elements, 3) == 2){popUp.cancelButton.click();}
        Assert.assertTrue(check.waitElement(dashboardHeader.hubImage, 15, true));
        log("hub successfully added!");
        log("Method is finished");
    }
}
