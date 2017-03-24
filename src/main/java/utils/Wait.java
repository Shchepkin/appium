package utils;

import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Wait {

    private AppiumDriver driver;
    private boolean result;
    private ScreenShot screenShot;
    private String waiterText;
    private Setup s;

    public Wait(AppiumDriver driver, String locale_){
        this.driver = driver;
        s = new Setup(locale_);
        waiterText = s.getLocalizeTextForKey("request_send");
    }

    public boolean elementWithText(String searchingText, int timer, boolean makeScreenShot) {
        s.log("Method is started");
        result = false;

        try {
            s.log(2, "waiting " + timer + " seconds for the element with text \"" + searchingText + "\"");
            WebDriverWait iWait = new WebDriverWait(driver, timer);
            iWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//android.widget.TextView[@text='" + searchingText + "']"))));

            s.log(2, "element is shown with text: \"" + searchingText + "\"");
            result = true;
        } catch (NoSuchElementException e) {
            s.log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){screenShot.getScreenShot();}
        } catch (TimeoutException e) {
            s.log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){screenShot.getScreenShot();}
        }
        return result;
    }


    public boolean invisibilityOfWaiter(boolean makeScreenShot) {
        s.log("Method is started");
        result = false;

        try {
            s.log(2, "waiting 90 seconds while Waiter become Invisible");
            WebDriverWait iWait = new WebDriverWait(driver, 90);
            iWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + waiterText + "']")));

            s.log(2, "waiter is gone");
            result = true;
        } catch (NoSuchElementException e) {
            s.log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){screenShot.getScreenShot();}
        } catch (TimeoutException e) {
            s.log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){screenShot.getScreenShot();}
        }
        return result;
    }


}
