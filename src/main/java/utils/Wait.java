package utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Base;

public class Wait{

//----------------------------------------------------------------------------------------------------------------------
    private final Base base;
    private final AppiumDriver driver;
    private String waiterText, pinPopUpText;
    private boolean result;

    public Wait(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        waiterText = base.getLocalizeTextForKey("request_send");
        pinPopUpText = base.getLocalizeTextForKey("do_you_want_to_enable_passcode");

    }
//----------------------------------------------------------------------------------------------------------------------

    public boolean elementWithText(String searchingText, int timer, boolean makeScreenShot) {
        Base.log(4, "Method is started");
        result = false;

        try {
            Base.log(1, "waiting " + timer + " seconds for the element with text \"" + searchingText + "\"");
            WebDriverWait iWait = new WebDriverWait(driver, timer);
            iWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//android.widget.TextView[@text='" + searchingText + "']"))));

            Base.log(1, "element is shown with text: \"" + searchingText + "\"");
            result = true;
        } catch (NoSuchElementException e) {
            Base.log(2, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                base.getScreenShot();
            }
        } catch (TimeoutException e) {
            Base.log(2, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                base.getScreenShot();
            }
        }
        Base.log(4, "Method is finished");
        return result;
    }

    public boolean loaderWithText(boolean visibility, String searchingText, int timer, boolean makeScreenShot) {
        Base.log(4, "Method is started");
        result = false;
        String xPath = "//*[contains(@resource-id,'com.ajaxsystems:id/content_text') and @text='" + searchingText + "']";

        try {
            Base.log(1, "waiting for " + timer + " seconds for the element with text \"" + searchingText + "\"");
            WebDriverWait iWait = new WebDriverWait(driver, timer);

            if(visibility){
                iWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(xPath))));
            }else {
                iWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xPath)));
            }

            Base.log(1, "element is shown with text: \"" + searchingText + "\"");
            result = true;
        } catch (NoSuchElementException e) {
            Base.log(2, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                base.getScreenShot();
            }
        } catch (TimeoutException e) {
            Base.log(2, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                base.getScreenShot();
            }
        }
        Base.log(4, "Method is finished");
        return result;
    }

    public boolean invisibilityElementWithText(String text, boolean makeScreenShot) {
        Base.log(4, "Method is started");
        result = false;

        try {
            Base.log(1, "waiting 100 seconds while element with text \"" + text + "\" become Invisible");
            WebDriverWait iWait = new WebDriverWait(driver, 100);
            iWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + text + "']")));

            Base.log(1, "element with text \"" + text + "\" is gone");
            result = true;
        } catch (NoSuchElementException e) {
            Base.log(2, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                base.getScreenShot();}
        } catch (TimeoutException e) {
            Base.log(2, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                base.getScreenShot();}
        }
        Base.log(4, "Method is finished");
        return result;
    }

    public boolean invisibilityOfWaiter() {
        Base.log(4, "Method is started");
        result = false;
        Base.log(1, "waiting 100 seconds while Waiter become Invisible");
        WebDriverWait iWait = new WebDriverWait(driver, 100);
        iWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("com.ajaxsystems:id/progress")));
        result = true;
        Base.log(1, "Waiter become Invisible");
        base.getScreenShot();
        Base.log(4, "Method is finished");
        return result;
    }

    public boolean visibilityOfSnackBarWithText(String expectedText, int timer) {
        try {
            Base.log(1, "waiting for SnackBar with get text");
            WebDriverWait iWait = new WebDriverWait(driver, timer);
            iWait.until(ExpectedConditions.visibilityOf(base.popUp.getSnackBarElement()));

            String actualSnackBarText = base.popUp.getSnackBarText();
            Base.log(1, "Actual SnackBar text: \"" + actualSnackBarText + "\"");
            Base.log(1, "Expected SnackBar text: \"" + expectedText + "\"");
            if (actualSnackBarText.equalsIgnoreCase(expectedText)) {
                return true;
            } else {
                return false;
            }

        } catch (NoSuchElementException e) {
            Base.log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            base.getScreenShot();
            return false;

        } catch (TimeoutException e) {
            Base.log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            base.getScreenShot();
            return false;
        }
    }


    public boolean visibilityOfSnackBar(int timer, boolean makeScreenShot) {
        Base.log(1, "waiting for SnackBar");
        if(element(base.popUp.getSnackBarElement(), timer, makeScreenShot)){
            Base.log(1, "SnackBar text: \"" + base.popUp.getSnackBarText() + "\"");
            return  true;
        }else {
            return false;
        }
    }

    public boolean invisibilityOfLoaderLogo(boolean makeScreenShot) {
        Base.log(4, "Method is started");
        result = false;

        try {
            Base.log(1, "waiting 100 seconds while LoaderLogo become Invisible");
            WebDriverWait iWait = new WebDriverWait(driver, 100);
            iWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("com.ajaxsystems:id/loaderLogo")));

            Base.log(1, "waiter is gone");
            result = true;
        } catch (NoSuchElementException e) {
            Base.log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                base.getScreenShot();}
        } catch (TimeoutException e) {
            Base.log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                base.getScreenShot();}
        }
        Base.log(4, "Method is finished");
        return result;
    }

    public boolean menuIconOrPinPopUp(int timer, boolean makeScreenShot) {
        Base.log(4, "Method is started");
        try {
            Base.log(1, "waiting " + timer + " seconds for menuIcon Or Pin PopUp");
            WebDriverWait iWait = new WebDriverWait(driver, timer);
            iWait.until(ExpectedConditions.or(
                            ExpectedConditions.visibilityOf(base.dashboardHeader.getMenuDrawer()),
                            ExpectedConditions.visibilityOf(base.nav.getCancelButton())
                    )
            );

            Base.log(1, "Main Menu icon or PIN popUp is shown");
            return true;

        } catch (NoSuchElementException e) {
            Base.log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            if (makeScreenShot){base.getScreenShot();}

        } catch (TimeoutException e) {
            Base.log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            if (makeScreenShot){base.getScreenShot();}
        }
        Base.log(4, "Method is finished");
        return false;
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
        try {
            Base.log(1, "waiting " + timer + " seconds for the element ");
            WebDriverWait iWait = new WebDriverWait(driver, timer);
            iWait.until(ExpectedConditions.visibilityOf(elementForWaiting));

            try {
                Base.log(1, "element " + elementForWaiting + " is shown with text: \"" + elementForWaiting.getText() + "\"");
            }catch (Exception e){
                Base.log(4, "element is shown successfully, but there is Exception while trying to get the text from the element:\n\n" + e + "\n");
            }
            return true;

        } catch (NoSuchElementException e) {
            Base.log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            if (makeScreenShot){base.getScreenShot();}
            return false;

        } catch (TimeoutException e) {
            Base.log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            if (makeScreenShot){base.getScreenShot();}
            return false;
        }
    }

    public boolean pinPopUp (int timer, boolean confirm){
        Base.log(1, "Wait PIN popUp");
        if(element(base.nav.getCancelButton(), timer, true)){
            if(confirm){
                base.nav.confirmIt();
            }else {
                base.nav.cancelIt();
            }
            return true;
        }
        return false;
    }

}
