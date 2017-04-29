package utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Base;


public class Check{

//----------------------------------------------------------------------------------------------------------------------
    private Base base;
    private AppiumDriver driver;
    private boolean result;
    private int numOfFoundElement;
    public LocalizedTextFor localizedTextFor = new LocalizedTextFor();
    public IsEmpty isEmpty= new IsEmpty();
    public IsPresent isPresent = new IsPresent();

    public Check(Base base) {
        this.base = base;
        this.driver = base.getDriver();
    }
//----------------------------------------------------------------------------------------------------------------------

    public boolean isElementDisplayed(WebElement element, int timer) {
        try {
            WebDriverWait iWait = new WebDriverWait(driver, timer);
            iWait.until(ExpectedConditions.visibilityOf(element));
            Base.log(1, "element is displayed: " + element);
            return true;

        } catch (NoSuchElementException e) {
            base.getScreenShot();
            result = false;
            Base.log(3, "no such element was appeared during " + timer + " seconds\n\n" + e + "\n");
            return false;
        }
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
    public class IsPresent {
        public boolean snackBar(int timer) {
            return true;
        }
        public boolean error(int timer) {
            return true;
        }
        public boolean element(int timer) {
            return true;
        }
        public boolean popUpWithConfirmation(int timer) {
            return true;
        }
    }

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
    public boolean isCancelButtonPresent() {
        if (base.nav.getCancelButton().isDisplayed() || base.nav.getCancel().isDisplayed()) {
            return true;
        } else return false;
    }

    public boolean isDeletedBy(String type, String value) {
        if (base.nav.scrollToElementWith.name(value, false)) {
            Base.log(4, "element with value \"" + value + "\" is still displayed in the List");
            result = false;
        } else {
            Base.log(3, "element with value \"" + value + "\" is not displayed in the List");
            result = true;
        }
        return result;
    }

    public class LocalizedTextFor{
        public ConfirmLoader confirmLoader = new ConfirmLoader();
        public SuccessMessage successMessage = new SuccessMessage();
        String actualText, expectedText;

        private boolean checkIt(String actualText, String expectedText){
            Base.log(1, "\nchecking of localized text", true);
            Base.log(1, "actual: \"" + actualText + "\"", true);
            Base.log(1, "expected: \"" + expectedText + "\"", true);

            if (expectedText.equalsIgnoreCase(actualText)){
                Base.log(1, "checking of localized text is successfully passed\n", true);
                return true;
            }else {
                Base.log(3, "expected text is not equals actual\n", true);
                return false;
            }
        }

        public class ConfirmLoader {
            public boolean hubDeleteFromHubSettings(){
                actualText = base.popUp.getContentText().replaceAll("(\").*(\")", "[]");
                expectedText = base.getLocalizeTextForKey("remove_hub_from_this_account").replaceAll("(\").*(\")", "[]");
                return checkIt(actualText, expectedText);
            }
            public boolean hubDeleteFromMasterSettings(){
                actualText = base.popUp.getContentText();
                expectedText = base.getLocalizeTextForKey("you_are_about_to_revoke_hub_access_for_user_are_you_sure");
                return checkIt(actualText, expectedText);
            }
            public boolean roomDelete(){
                actualText = base.popUp.getContentText().replaceAll("(\").*(\")", "[]");
                expectedText = base.getLocalizeTextForKey("you_are_about_to_delete_room_all_settings_will_be_erased_continue").replaceAll("(\").*(\")", "[]");
                return checkIt(actualText, expectedText);
            }
            public boolean deviceDelete(){
                actualText = "";
                expectedText = "";
                return checkIt(actualText, expectedText);
            }
        }

        public class SuccessMessage {
            String actualText = base.popUp.getContentText();
            public boolean hubDelete(){
                expectedText = base.getLocalizeTextForKey("Detach_success1");
                return checkIt(actualText, expectedText);
            }
            public boolean roomDelete(){
                expectedText = base.getLocalizeTextForKey("Deleting_success1");
                return checkIt(actualText, expectedText);
            }
            public boolean deviceDelete(){
                expectedText = base.getLocalizeTextForKey("Deleting_success1");
                return checkIt(actualText, expectedText);
            }
        }
    }

    public class IsEmpty{
        public boolean devicesList() {
            return !base.wait.element(base.devicesPage.getRoomOfDeviceLocator(), 2, true);
        }
        public boolean roomsList() {
            return base.wait.element(base.roomsPage.getDescription(), 2, true);
        }
        public boolean guestUsersList() {
                return true;
        }
        public boolean pendingUsersList() {
                return true;
        }
    }
}