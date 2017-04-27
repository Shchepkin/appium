package utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.Base;


public class Check{

//----------------------------------------------------------------------------------------------------------------------
    private Base base;
    private AppiumDriver driver;
    private boolean result;
    private int numOfFoundElement;
    public LocalizedTextFor localizedTextFor;
    public IsEmpty isEmpty;

    public Check(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        localizedTextFor = new LocalizedTextFor();
        isEmpty = new IsEmpty();
    }
//----------------------------------------------------------------------------------------------------------------------

    public boolean isElementDisplayed(WebElement element, int timer) {
        Base.log(4, "Method is started");

        try {
            // assert is the element displayed on the page
            WebDriverWait iWait = new WebDriverWait(driver, timer);
            iWait.until(ExpectedConditions.visibilityOf(element));
            Base.log(4, "element is shown with text: \"" + element.getText() + "\"");
            result = true;

        } catch (NoSuchElementException e) {
            // is failed - make screenshot
            base.getScreenShot();
            result = false;
            Base.log(4, "no such element was appeared during " + timer + " seconds\n\n" + e + "\n");
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
        Base.log(4, "Method is started");
        numOfFoundElement = 0;
        result = false;
        for (int i = 1; i <= period; i++) {
            int counter = 1;
            Base.log(4, "start waiting period #" + i);

            for (WebElement el : elements) {
                try {
                    Base.log(4, "element " + el + " is shown with text: \"" + el.getText() + "\"");
                    result = true;
                    numOfFoundElement = counter;
                    break;
                } catch (NoSuchElementException e) {
                    Base.log(2, "NoSuchElementException, element " + counter + " is not shown");
                } catch (TimeoutException e) {
                    Base.log(2, "Timeout Exception, element " + counter + " is not shown");
                }
                counter ++;
            }
            if (result) {break;}
        }
        Base.log(4, "Method is finished");
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
        Base.log(4, "Method is started");
        result = false;
        WebElement[] elements = new WebElement[]{base.popUp.getSnackBarElement(), base.popUp.loadingWindow};

        for (int i = 1; i <= tryCount; i++) {
            Base.log(3, "click the element link, try count #" +i);
            elementForClick.click();

            Base.log(4, "waiting for: 1.snackBar  2.loadingWindow");
            numOfFoundElement = waitElements(elements, period);

            checkNum(numOfFoundElement, confirmPopupProposition);

            if (!base.wait.element(elementForClick, 1, true)) {
                Base.log(3, "element for click is not shown now");
                result = true;
                break;
            }
            Base.log(3, "element for click is shown again");
        }
        Base.log(4, "Method is finished");
        return result;
    }

//======================================================================================================================

    public boolean clickElementAndWaitingPopup(WebElement elementForClick, boolean confirmPopupProposition){
        Base.log(4, "Method is started");
        result = false;
        WebElement[] elements = new WebElement[]{base.popUp.getSnackBarElement(), base.popUp.loadingWindow};

        Base.log(3, "click the element link");
        elementForClick.click();

        Base.log(4, "waiting for: 1.snackBar  2.loadingWindow");
        numOfFoundElement = waitElements(elements, 4);

        checkNum(numOfFoundElement, confirmPopupProposition);

        Base.log(4, "Method is finished");
        return result;
    }

//======================================================================================================================

    private void checkNum (int numOfFoundElement, boolean confirmPopupProposition) {

        switch (numOfFoundElement){
            case 0: Base.log(3, "no PopUp is shown or this moment is missed"); break;
            case 1: Base.log(3, "snackBar is shown, the text was previously displayed"); break;
            case 2: Base.log(3, "PopUp is shown with text: \"" + base.popUp.getContentText() + "\"");
                WebElement[] elements = new WebElement[]{base.nav.getCancelButton(), base.popUp.errorPic};
                numOfFoundElement = waitElements(elements, 5);
                switch (numOfFoundElement){
                    case 0: Base.log(3, "PopUp is shown, but without errors and any propositions"); break;
                    case 1:
                        if (confirmPopupProposition){
                            Base.log(4, "confirm Popup Proposition");
                            base.nav.getConfirmButton().click();
                        }
                        else {
                            Base.log(4, "cancel Popup Proposition");
                            base.nav.getCancelButton().click();
                        }
                        break;
                    case 2: Base.log(2, "ERROR is shown with text: \"" + base.popUp.getContentText() + "\""); break;
                    default: break;
                }
            default: break;
        }
    }

//======================================================================================================================

    public boolean waitElementWithoutPin(WebElement element, int timer) {
        Base.log(4, "Method is started");

        try {
            Base.log(4, "waiting " + timer + " seconds for the element ");

            WebElement[] elements = new WebElement[]{element, base.nav.getCancelButton()};
            if (waitElements(elements, 5) == 2) {
                Base.log(4, "Pincode PopUp is shown - cancel it!");
                base.nav.cancelIt();
            }

            Base.log(4, "element " + element + " is shown with text: \"" + element.getText() + "\"");
            result = true;

        } catch (NoSuchElementException e) {
            Base.log(2, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            base.getScreenShot();

        } catch (TimeoutException e) {
            Base.log(2, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            base.getScreenShot();
        }
        return result;
    }

//======================================================================================================================

    public boolean isSnackBarPresent(int timer) {
        Base.log(4, "Method is started");
        result = false;

        try {
            Base.log(4, "waiting " + timer + " seconds for SnackBar");
            WebDriverWait iWait = new WebDriverWait(driver, timer);
            iWait.until(ExpectedConditions.visibilityOf(base.popUp.getSnackBarElement()));

            Base.log(4, "SnackBar is shown with text: \"" + base.popUp.getSnackBarText() + "\"");
            result = true;

        } catch (NoSuchElementException e) {
            Base.log(4, "SnackBar is not shown:\n\n" + e + "\n");
            base.getScreenShot();
        }
        return result;
    }

    public boolean isErrorPresent(int timer) {
        Base.log(4, "Method is started");
        try {
            WebDriverWait iWait = new WebDriverWait(driver, timer);
            iWait.until(ExpectedConditions.visibilityOf(base.popUp.errorPic));

            Base.log(3, "Error message is shown with text: \"" + base.popUp.getContentText() + "\"");
            result = true;

        } catch (NoSuchElementException e) {
            Base.log(4, "Error message is not shown");
            base.getScreenShot();
            result = false;
        }
        return result;
    }

    public boolean isDeletedBy(String type, String value) {
        if (base.nav.scrollToElementWith.name(value, false)) {
            Base.log(4, "element with value \"" + value + "\" is still displayed in the List");
            result = false;
        }else {
            Base.log(3, "element with value \"" + value + "\" is not displayed in the List");
            result = true;
        }
        return result;
    }

    public boolean isCancelButtonPresent(){
        if (base.nav.getCancelButton().isDisplayed() || base.nav.getCancel().isDisplayed()){
            return true;
        }else return false;
    }

    public class LocalizedTextFor{

        public boolean hubUnpair() {
            Base.log(4, "get expected and actual localized text");
            String actualText = base.popUp.getContentText();
            String expectedText = base.getLocalizeTextForKey("Detach_success1");

            Base.log(4, "checking of localized text");
            Base.log(1, "actual: " + actualText, true);
            Base.log(1, "expected: " + expectedText, true);

            if(expectedText.equalsIgnoreCase(actualText)){

                Base.log(1, "checking of localized text is successfully passed", true);
                return true;
            }else {
                Base.log(1,  "expected text is not equals actual", true);
                return false;
            }
        }

        public boolean deletingDevice(){
            String successText = base.getLocalizeTextForKey("Deleting_success1");
            Base.log(1, "waiting for SUCCESS text");
            if(base.wait.elementWithText(successText, 10, true)){
                Base.log(1, "SUCCESS text is shown");
                return true;
            }else {
                Base.log(3, "SUCCESS text is not shown");
                return false;
            }
        }

    }

    public class IsEmpty{

        public boolean devicesList() {
            if(base.wait.element(base.devicesPage.getRoomOfDeviceLocator(), 1, true)){
                return false;
            }else {
                return true;
            }
        }

        public boolean roomsList() {
                return true;
        }

        public boolean guestUsersList() {
                return true;
        }

        public boolean pendingUsersList() {
                return true;
        }


    }
}