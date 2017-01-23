package ajaxMobileApp;

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
            System.out.println("Element is displayed.\n" + element);

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

    public void closePinWindowIfDisplayed() {
        dashboardActivePINPage = new DashboardActivePINPage(driver);
        try {
            // assert is the element displayed on the page
            WebDriverWait iWait = new WebDriverWait (driver, 30);
            iWait.until(ExpectedConditions.visibilityOf(dashboardActivePINPage.contentText));
            dashboardActivePINPage.cancelBtn.click();

        } catch (NoSuchElementException e) {
            System.out.println("Window with PIN acceptation isn't displayed.");
        }
    }

    public void openPinWindowIfDisplayed() {
        dashboardActivePINPage = new DashboardActivePINPage(driver);
        try {
            // assert is the element displayed on the page
            WebDriverWait iWait = new WebDriverWait (driver, 30);
            iWait.until(ExpectedConditions.visibilityOf(dashboardActivePINPage.contentText));
            dashboardActivePINPage.confirmBtn.click();

        } catch (NoSuchElementException e) {
            System.out.println("Window with PIN acceptation isn't displayed.");
        }
    }
}
