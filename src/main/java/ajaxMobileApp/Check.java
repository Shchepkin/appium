package ajaxMobileApp;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.IOException;


public class Check {
    private AndroidDriver driver;
    private ScreenShot screenShot;
//    private String pathOfScreenshot;

    public Check(AndroidDriver driver){
        this.driver = driver;
    }

    public void checkIsDisplayed(WebElement element) {
        // create ScreenShot object for making screenshots
        screenShot = new ScreenShot(driver);

        try {
            // assert is the element displayed on the page
            element.isDisplayed();

        } catch (NoSuchElementException e) {

            // is failed - make screenshot
            try {
                screenShot.getScreenShot();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            // creation report
            Assert.fail("Test failed\n" + e);
        }
    }
}


//            Assert.assertEquals("Not exist", "Exist", "Selector is wrong or element is absent\n");
//            String df = screenShot.getPathScreenShot();
//            Assert.fail("Test failed\n<a href='"+ screenShot.getPathScreenshot() + "'>Screenshot</a>\n" + e);