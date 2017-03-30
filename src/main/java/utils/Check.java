package utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.Base;

import java.io.IOException;


public class Check extends Base{
    private AppiumDriver driver;
    private ScreenShot screenShot;
    private PopUp popUp;
    private int numOfFoundElement;

    private boolean result;

    public Check(AppiumDriver driver) {
        this.driver = driver;
        this.popUp = new PopUp(driver);
        this.screenShot = new ScreenShot(driver);
    }

//**********************************************************************************************************************
    public void isElementDisplayed(WebElement element, int timer) {
        log("Method is started");

        try {
            // assert is the element displayed on the page
            WebDriverWait iWait = new WebDriverWait(driver, timer);
            iWait.until(ExpectedConditions.visibilityOf(element));
            log("element is shown with text: \"" + element.getText() + "\"");

        } catch (NoSuchElementException e) {
            // is failed - make screenshot
            screenShot.getScreenShot();

            // creation report
            Assert.fail("Test failed - no such element was appeared during " + timer + " seconds\n" + e);
        }
    }

    /*******************************************************************************************************************
        * @param element           - element which we want to wait
        * @param timer             - how long time we want to wait for the element (in seconds)
        * @param makeScreenShot    - make screenshot if element is not found (true)
        * @return                  - result true or false about successfully execute this method

         example:
            waitElement(element, 5, true)
     */
    public boolean waitElement(WebElement element, int timer, boolean makeScreenShot) {
        log("Method is started");

        try {
            log(2, "waiting " + timer + " seconds for the element ");
            WebDriverWait iWait = new WebDriverWait(driver, timer);
            iWait.until(ExpectedConditions.visibilityOf(element));
            log(2, "element " + element + " is shown with text: \"" + element.getText() + "\"");
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
     *
         * @param elements - selectList of locators for checking
         * @param period   - how many iterations of checking we have to make, each iterations approximately 1.3 sec
         * @return         - int numOfFoundElement - number of element which was shown (if there is no elements it returns 0)

      example:
        waitElements(new WebElement[]{element1, element2, element3}, 5)
      or
        WebElement[] elements = new WebElement[]{element1, element2, element3};
        waitElements(elements, 5));
     */
    public int waitElements (WebElement[] elements, int period) {
        log("Method is started");
        numOfFoundElement = 0;
        result = false;
        for (int i = 1; i <= period; i++) {
            int counter = 1;
            log("start waiting period #" + i);

            for (WebElement el : elements) {
                try {
                    log("element " + el + " is shown with text: \"" + el.getText() + "\"");
                    result = true;
                    numOfFoundElement = counter;
                    break;
                } catch (NoSuchElementException e) {
                    log(4, "NoSuchElementException, element " + counter + " is not shown");
                } catch (TimeoutException e) {
                    log(4, "Timeout Exception, element " + counter + " is not shown");
                }
                counter ++;
            }
            if (result) {break;}
        }
        log("Method is finished");
        return numOfFoundElement;
    }

    /*******************************************************************************************************************
     *
        @param elementForClick           - element which we want to click
        @param period                    - how many iterations of checking PopUp we have to do, each iterations approximately 1.3 sec
        @param tryCount                  - number of attempts for trying click the elementForClick
        @param confirmPopupProposition   - set true if you want confirm proposition from PopUp window and set false if you don't
        @return                          - return true if element for click is not shown now

       example:
          clickElementAndWaitingPopup(popUp.cancelButton, 3, 3, false)
     */
    public boolean clickElementAndWaitingPopup(WebElement elementForClick, int period, int tryCount, boolean confirmPopupProposition){
        log("Method is started");
        result = false;
        WebElement[] elements = new WebElement[]{popUp.snackBar, popUp.loadingWin};

        for (int i = 1; i <= tryCount; i++) {
            log(3, "click the element link, try count #" +i);
            elementForClick.click();

            log("waiting for: 1.snackBar  2.loadingWin");
            numOfFoundElement = waitElements(elements, period);

            checkNum(numOfFoundElement, confirmPopupProposition);

            if (!waitElement(elementForClick, 1, true)) {
                log(3, "element for click is not shown now");
                result = true;
                break;
            }
            log(3, "element for click is shown again");
        }
        log("Method is finished");
        return result;
    }

//======================================================================================================================

    public boolean clickElementAndWaitingPopup(WebElement elementForClick, boolean confirmPopupProposition){
        log("Method is started");
        result = false;
        WebElement[] elements = new WebElement[]{popUp.snackBar, popUp.loadingWin};

        log(3, "click the element link");
        elementForClick.click();

        log("waiting for: 1.snackBar  2.loadingWin");
        numOfFoundElement = waitElements(elements, 4);

        checkNum(numOfFoundElement, confirmPopupProposition);

        log("Method is finished");
        return result;
    }

//======================================================================================================================

    private void checkNum (int numOfFoundElement, boolean confirmPopupProposition) {

        switch (numOfFoundElement){
            case 0: log(3, "no PopUp is shown or this moment is missed"); break;
            case 1: log(3, "snackBar is shown, the text was previously displayed"); break;
            case 2: log(3, "PopUp is shown with text: \"" + popUp.contentText.getText() + "\"");
                WebElement[] elements = new WebElement[]{popUp.cancelButton, popUp.errorPic};
                numOfFoundElement = waitElements(elements, 5);
                switch (numOfFoundElement){
                    case 0: log(3, "PopUp is shown, but without errors and any propositions"); break;
                    case 1:
                        if (confirmPopupProposition){
                            log("confirm Popup Proposition");
                            popUp.confirmButton.click();
                        }
                        else {
                            log("cancel Popup Proposition");
                            popUp.cancelButton.click();
                        }
                        break;
                    case 2: log(4, "ERROR is shown with text: \"" + popUp.contentText.getText() + "\""); break;
                    default: break;
                }
            default: break;
        }
    }

//======================================================================================================================

    public boolean waitElementWithoutPin(WebElement element, int timer) {
        log("Method is started");

        try {
            log(2, "waiting " + timer + " seconds for the element ");

            WebElement[] elements = new WebElement[]{element, popUp.cancelButton};
            if (waitElements(elements, 5) == 2) {
                log("Pincode PopUp is shown - cancel it!");
                popUp.cancelButton.click();
            }

            log(2, "element " + element + " is shown with text: \"" + element.getText() + "\"");
            result = true;

        } catch (NoSuchElementException e) {
            log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            screenShot.getScreenShot();

        } catch (TimeoutException e) {
            log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            screenShot.getScreenShot();
        }
        return result;
    }

//======================================================================================================================

    public boolean forSnackBarIsPresent(int timer) {
        log("Method is started");
        result = false;

        try {
            log(2, "waiting " + timer + " seconds for SnackBar");
            WebDriverWait iWait = new WebDriverWait(driver, timer);
            iWait.until(ExpectedConditions.visibilityOf(popUp.snackBar));

            log("SnackBar is shown with text: \"" + popUp.snackBar.getText() + "\"");
            result = true;

        } catch (Exception e) {
            log(3, "SnackBar is not shown:\n\n" + e + "\n");
            screenShot.getScreenShot();
        }
        return result;
    }
}