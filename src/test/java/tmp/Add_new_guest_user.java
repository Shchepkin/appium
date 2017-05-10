package tmp;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.Base;

import java.util.HashMap;

/**
 * PRECONDITION
 * There are:
 * - at least one Hub
 */

public class Add_new_guest_user {

    private Base base;
    private AndroidDriver driver;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        Base.log(3, "\nSTART TEST\n");
        base = new Base(deviceName_);
        this.driver = base.getDriver();
        base.initPageObjects(driver);
        base.loginPage.loginWithPinCancel();
        base.nav.gotoPage.inviteUser();
    }


    @Test(priority = 2, enabled = true)
    public void From_Contact_List() {
        System.out.println("start");
        String email = "test.email.ajax200@i.ua";
        String id = "com.ajaxsystems:id/recycler";

        Assert.assertTrue(base.nav.scroll.toElementWith.email(email, true));


//        RemoteWebElement radioGroup = (RemoteWebElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"" + id + "\")).scrollIntoView(new UiSelector().text(\"" + email + "\"));");
//        radioGroup.click();

//        System.out.println("WebElement element");
//        WebElement element = driver.findElement(By.xpath("//*[contains(@resource-id,'com.ajaxsystems:id/mail') and @text='" + email + "']"));

        System.out.println("touch");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
