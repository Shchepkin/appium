package utils;

import pages.DashboardActivePINPage;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;


public class Check {
    private AndroidDriver driver;
    private ScreenShot screenShot;
    private DashboardActivePINPage dashboardActivePINPage;
//    private String pathOfScreenshot;

    public Check(AndroidDriver driver){
        this.driver = driver;
    }

    public void checkIsDisplayed(WebElement element) {
        // create ScreenShot object for making screenshots
        screenShot = new ScreenShot(driver);

        try {
            // assert is the element displayed on the page
            WebDriverWait iWait = new WebDriverWait (driver, 180);
            iWait.until(ExpectedConditions.visibilityOf(element));
            element.isDisplayed();
            System.out.println("Element is displayed. " + element);

        } catch (NoSuchElementException e) {

            // is failed - make screenshot
            try {
                screenShot.getScreenShot();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            // creation report
            Assert.fail("Test failed - no such element was appeared during 3 min\n" + e);
        }
    }


    public void waitElement (WebElement element) {
        WebDriverWait iWait = new WebDriverWait (driver, 60);
        iWait.until(ExpectedConditions.visibilityOf(element));
    }

}
