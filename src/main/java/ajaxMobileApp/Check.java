package ajaxMobileApp;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.IOException;


public class Check {
    private AndroidDriver driver;
    private ScreenShot screenShot;

    public Check(AndroidDriver driver){
        this.driver = driver;
    }

    public void checkIsDisplayed(WebElement element) {
        screenShot = new ScreenShot(driver);

        try {
            element.isDisplayed();
        } catch (NoSuchElementException e) {

            try {
                screenShot.getScreenShot();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            Assert.assertEquals("Not exist", "Exist", "Selector is wrong or element is absent\n");
        }
    }
}
