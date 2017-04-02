package utils;

import io.appium.java_client.AppiumDriver;
import org.apache.commons.io.FileUtils;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Base;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Wait extends Base{

    private AppiumDriver driver;
    private boolean result;
    private ScreenShot screenShot;
    private String waiterText;

    public Wait(AppiumDriver driver){
        this.driver = driver;
    }

    public boolean elementWithText(String searchingText, int timer, boolean makeScreenShot) {
        log("Method is started");
        result = false;

        try {
            log(2, "waiting " + timer + " seconds for the element with text \"" + searchingText + "\"");
            WebDriverWait iWait = new WebDriverWait(driver, timer);
            iWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//android.widget.TextView[@text='" + searchingText + "']"))));

            log(2, "element is shown with text: \"" + searchingText + "\"");
            result = true;
        } catch (NoSuchElementException e) {
            log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){screenShot.getScreenShot();}
        } catch (TimeoutException e) {
            log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){screenShot.getScreenShot();}
        }
        return result;
    }


    public boolean invisibilityOfWaiter(boolean makeScreenShot) {
        log("Method is started");
        result = false;
        waiterText = getLocalizeTextForKey("request_send");

        try {
            log(2, "waiting 90 seconds while Waiter become Invisible");
            WebDriverWait iWait = new WebDriverWait(driver, 90);
            iWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + waiterText + "']")));

            log(2, "waiter is gone");
            result = true;
        } catch (NoSuchElementException e) {
            log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){screenShot.getScreenShot();}
        } catch (TimeoutException e) {
            log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){screenShot.getScreenShot();}
        }
        return result;
    }


    /*******************************************************************************************************************
     * @param elementForWaiting - element which we want to wait
     * @param timer             - how long time we want to wait for the elementForWaiting (in seconds)
     * @param makeScreenShot    - make screenshot if elementForWaiting is not found (true)
     * @return                  - result true or false about successfully execute this method

    example:
    wait.element(elementForWaiting, 5, true)
     */
    public boolean element(WebElement elementForWaiting, int timer, boolean makeScreenShot) {
        log("Method is started");

        try {
            log(2, "waiting " + timer + " seconds for the elementForWaiting ");
            WebDriverWait iWait = new WebDriverWait(driver, timer);
            iWait.until(ExpectedConditions.visibilityOf(elementForWaiting));

            log(2, "element " + elementForWaiting + " is shown with text: \"" + elementForWaiting.getText() + "\"");
            result = true;

        } catch (NoSuchElementException e) {
            log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){screenShot.getScreenShot();}

        } catch (TimeoutException e) {
            log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){screenShot.getScreenShot();}
        }
        return result;
    }




}
