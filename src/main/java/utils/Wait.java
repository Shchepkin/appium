package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Base;

public class Wait{

//----------------------------------------------------------------------------------------------------------------------
    private final Base $;
    private final AppiumDriver driver;
    private String waiterText;
    private boolean result;

    public Wait(Base base) {
        $ = base;
        this.driver = $.getDriver();
        waiterText = $.getLocalizeTextForKey("request_send");
    }
//----------------------------------------------------------------------------------------------------------------------


    public boolean elementWithText(String searchingText, int timer, boolean makeScreenShot) {
        Base.log("Method is started");
        result = false;

        try {
            Base.log(2, "waiting " + timer + " seconds for the element with text \"" + searchingText + "\"");
            WebDriverWait iWait = new WebDriverWait(driver, timer);
            iWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//android.widget.TextView[@text='" + searchingText + "']"))));

            Base.log(2, "element is shown with text: \"" + searchingText + "\"");
            result = true;
        } catch (NoSuchElementException e) {
            Base.log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                $.getScreenShot();
            }
        } catch (TimeoutException e) {
            Base.log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                $.getScreenShot();
            }
        }
        return result;
    }



    public boolean loaderWithText(boolean visibility, String searchingText, int timer, boolean makeScreenShot) {
        Base.log("Method is started");
        result = false;

        try {
            Base.log(2, "waiting for " + timer + " seconds for the element with text \"" + searchingText + "\"");
            WebDriverWait iWait = new WebDriverWait(driver, timer);

            if(visibility){
                iWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[contains(@resource-id,'com.ajaxsystems:id/content_text') and @text='" + searchingText + "']"))));
            }else {
                iWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(@resource-id,'com.ajaxsystems:id/content_text') and @text='" + searchingText + "']")));
            }

            Base.log(2, "element is shown with text: \"" + searchingText + "\"");
            result = true;
        } catch (NoSuchElementException e) {
            Base.log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                $.getScreenShot();
            }
        } catch (TimeoutException e) {
            Base.log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                $.getScreenShot();
            }
        }
        return result;
    }

    public boolean invisibilityElementWithText(String waiterText, boolean makeScreenShot) {
        Base.log("Method is started");
        result = false;

        try {
            Base.log(2, "waiting 100 seconds while Waiter become Invisible");
            WebDriverWait iWait = new WebDriverWait(driver, 100);
            iWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + waiterText + "']")));

            Base.log(2, "waiter is gone");
            result = true;
        } catch (NoSuchElementException e) {
            Base.log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                $.getScreenShot();}
        } catch (TimeoutException e) {
            Base.log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                $.getScreenShot();}
        }
        return result;
    }


    public boolean invisibilityOfWaiter(boolean makeScreenShot) {
        Base.log("Method is started");
        result = false;
        Base.log(3, "waiterText: \"" + waiterText + "\"");
//        waiterText = getLocalizeTextForKey("request_send");
        invisibilityElementWithText(waiterText, makeScreenShot);
        return result;
    }

    public boolean invisibilityOfWaiter() {
        Base.log("Method is started");
        result = false;
        Base.log(2, "waiting 100 seconds while Waiter become Invisible");
        WebDriverWait iWait = new WebDriverWait(driver, 100);
        iWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("com.ajaxsystems:id/progress")));
        result = true;
        return result;
    }

    public boolean invisibilityOfLoaderLogo(boolean makeScreenShot) {
        Base.log("Method is started");
        result = false;

        try {
            Base.log(2, "waiting 100 seconds while LoaderLogo become Invisible");
            WebDriverWait iWait = new WebDriverWait(driver, 100);
            iWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("com.ajaxsystems:id/loaderLogo")));

            Base.log(2, "waiter is gone");
            result = true;
        } catch (NoSuchElementException e) {
            Base.log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                $.getScreenShot();}
        } catch (TimeoutException e) {
            Base.log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                $.getScreenShot();}
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
        Base.log("Method is started");

        try {
            Base.log(2, "waiting " + timer + " seconds for the element ");
            WebDriverWait iWait = new WebDriverWait(driver, timer);
            iWait.until(ExpectedConditions.visibilityOf(elementForWaiting));

            Base.log(2, "element " + elementForWaiting + " is shown with text: \"" + elementForWaiting.getText() + "\"");
            result = true;

        } catch (NoSuchElementException e) {
            Base.log(2, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){$.getScreenShot();}

        } catch (TimeoutException e) {
            Base.log(2, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){$.getScreenShot();}
        }
        return result;
    }

}
