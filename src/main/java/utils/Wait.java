package utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Base;

public class Wait extends Base{
    private AppiumDriver driver;
    private boolean result;

    public void setWaiterText(String waiterText) {
        this.waiterText = waiterText;
    }

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
            if (makeScreenShot){
                getScreenShot(driver);
            }
        } catch (TimeoutException e) {
            log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                getScreenShot(driver);
            }
        }
        return result;
    }



    public boolean loaderWithText(boolean visibility, String searchingText, int timer, boolean makeScreenShot) {
        log("Method is started");
        result = false;

        try {
            log(2, "waiting for " + timer + " seconds for the element with text \"" + searchingText + "\"");
            WebDriverWait iWait = new WebDriverWait(driver, timer);

            if(visibility){
                iWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[contains(@resource-id,'com.ajaxsystems:id/content_text') and @text='" + searchingText + "']"))));
            }else {
                iWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[contains(@resource-id,'com.ajaxsystems:id/content_text') and @text='" + searchingText + "']")));
            }

            log(2, "element is shown with text: \"" + searchingText + "\"");
            result = true;
        } catch (NoSuchElementException e) {
            log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                getScreenShot(driver);
            }
        } catch (TimeoutException e) {
            log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                getScreenShot(driver);
            }
        }
        return result;
    }

    public boolean invisibilityElementWithText(String waiterText, boolean makeScreenShot) {
        log("Method is started");
        result = false;

        try {
            log(2, "waiting 100 seconds while Waiter become Invisible");
            WebDriverWait iWait = new WebDriverWait(driver, 100);
            iWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + waiterText + "']")));

            log(2, "waiter is gone");
            result = true;
        } catch (NoSuchElementException e) {
            log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                getScreenShot(driver);}
        } catch (TimeoutException e) {
            log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                getScreenShot(driver);}
        }
        return result;
    }


    public boolean invisibilityOfWaiter(boolean makeScreenShot) {
        log("Method is started");
        result = false;
        log(3, "waiterText: \"" + waiterText + "\"");
//        waiterText = getLocalizeTextForKey("request_send");
        invisibilityElementWithText(waiterText, makeScreenShot);
        return result;
    }

    public boolean invisibilityOfWaiter() {
        log("Method is started");
        result = false;
        log(2, "waiting 100 seconds while Waiter become Invisible");
        WebDriverWait iWait = new WebDriverWait(driver, 100);
        iWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("com.ajaxsystems:id/progress")));
        result = true;
        return result;
    }

    public boolean invisibilityOfLoaderLogo(boolean makeScreenShot) {
        log("Method is started");
        result = false;

        try {
            log(2, "waiting 100 seconds while LoaderLogo become Invisible");
            WebDriverWait iWait = new WebDriverWait(driver, 100);
            iWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("com.ajaxsystems:id/loaderLogo")));

            log(2, "waiter is gone");
            result = true;
        } catch (NoSuchElementException e) {
            log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                getScreenShot(driver);}
        } catch (TimeoutException e) {
            log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                getScreenShot(driver);}
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
            log(2, "waiting " + timer + " seconds for the element ");
            WebDriverWait iWait = new WebDriverWait(driver, timer);
            iWait.until(ExpectedConditions.visibilityOf(elementForWaiting));

            log(2, "element " + elementForWaiting + " is shown with text: \"" + elementForWaiting.getText() + "\"");
            result = true;

        } catch (NoSuchElementException e) {
            log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){getScreenShot(driver);}

        } catch (TimeoutException e) {
            log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){getScreenShot(driver);}
        }
        return result;
    }




}
