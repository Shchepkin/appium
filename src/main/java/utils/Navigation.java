package utils;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.Base;

import java.util.ArrayList;


public class Navigation {

    @AndroidFindBy(xpath = "//android.widget.TextView")
    private ArrayList<WebElement> allTextObjects;

    public ArrayList<WebElement> getScrollableElementList() {
        return scrollableElementList;
    }

    @AndroidFindBy(uiAutomator = "new UiSelector().scrollable(true)")
    private ArrayList<WebElement> scrollableElementList;

    @AndroidFindBy(id = "com.ajaxsystems:id/spaceControl")
    private WebElement spaceControlImage;

//----------------------------------------------------------------------------------------------------------------------
    private Base base;
    private AndroidDriver driver;
//    private long start, finish;
//    private boolean flag;
//    private int counter;
//    private ArrayList<String> etalon;
//    private ArrayList<String> current;

    public Touch touch = new Touch();
    public Scroll scroll = new Scroll();
    public GoToPage gotoPage = new GoToPage();
    public TapButton tapButton = new TapButton();
//    public ScrollToElementWith scrollToElementWith = new ScrollToElementWith();

    public Navigation(Base base) {
        this.base = base;
        this.driver = base.getDriver();
//        etalon = new ArrayList<>();
//        current = new ArrayList<>();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

//----------------------------------------------------------------------------------------------------------------------
// Swipe
//----------------------------------------------------------------------------------------------------------------------
    public void swipeUp() {
        Dimension screenSize = driver.manage().window().getSize();
        Base.log(4, "Screen dimension is " + screenSize);

        int startX = (int) (screenSize.width / 2.00);
        int startY = (int) (screenSize.height / 3.00);
        int endX = startX;
        int endY = (int) (screenSize.height / 100.00);
        int duration = 1500;

        Base.log(4, "swipe(startX, startY, endX, endY, duration) [" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + duration + "]");
        driver.swipe(startX, startY, endX, endY, duration);
    }

    public void swipeUp(int duration, double heightPart) {
        Dimension screenSize = driver.manage().window().getSize();
        Base.log(4, "Screen dimension is " + screenSize);

        int startX = (int) (screenSize.width / 2.00);
        int startY = (int) (screenSize.height / heightPart);
        int endX = startX;
        int endY = (int) (screenSize.height / 100.00);

        Base.log(4, "swipe(startX, startY, endX, endY, duration) [" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + duration + "]");
        driver.swipe(startX, startY, endX, endY, duration);
    }

    public void swipeDown() {
        Dimension screenSize = driver.manage().window().getSize();
        Base.log(1, "Screen dimension is " + screenSize);

        int startX = (int) (screenSize.width / 2.00);
        int startY = (int) (screenSize.height / 3.00);
        int endX = startX;
        int endY = (int) (screenSize.height / 1.05);
        int duration = 1500;

        Base.log(1, "swipe(startX, startY, endX, endY, duration) [" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + duration + "]");
        driver.swipe(startX, startY, endX, endY, duration);
    }

    public void swipeDown(int duration, double heightPart) {
        Dimension screenSize = driver.manage().window().getSize();
        Base.log(4, "Screen dimension is " + screenSize);

        int startX = (int) (screenSize.width / 2.00);
        int startY = (int) (screenSize.height / heightPart);
        int endX = startX;
        int endY = (int) (screenSize.height / 1.05);

        Base.log(4, "swipe(startX, startY, endX, endY, duration) [" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + duration + "]");
        driver.swipe(startX, startY, endX, endY, duration);
    }
//----------------------------------------------------------------------------------------------------------------------
// Scroll
//----------------------------------------------------------------------------------------------------------------------

    /**
     * This class scrolls current screen to the element with needed parameter and gives you the opportunity to click this element if you want
     * @return true if method found the element (and click them if it required)
     */

    public class Scroll {
        public ToElementWith toElementWith = new ToElementWith();

        public class ToElementWith {
            public boolean text(String textOfSearchingElement, boolean click) {
                String searchingElement = "new UiSelector().text(\"" + textOfSearchingElement + "\")";
                return search(searchingElement, click);
            }

            public boolean email(String emailOfSearchingElement, boolean click) {
                String searchingElement = "new UiSelector().resourceId(\"com.ajaxsystems:id/mail\").text(\"" + emailOfSearchingElement + "\")";
                return search(searchingElement, click);
            }

            public boolean room(String roomOfSearchingElement, boolean click) {
                String searchingElement = "new UiSelector().resourceId(\"com.ajaxsystems:id/room\").text(\"" + roomOfSearchingElement + "\")";
                return search(searchingElement, click);
            }

            public boolean id(String idOfSearchingElement, boolean click) {
                String searchingElement = "new UiSelector().resourceId(\"" + idOfSearchingElement + "\")";
                return search(searchingElement, click);
            }

            public boolean name(String nameOfSearchingElement, boolean click) {
                String searchingElement = "new UiSelector().resourceId(\"com.ajaxsystems:id/name\").text(\"" + nameOfSearchingElement + "\")";
                return search(searchingElement, click);
            }

            public boolean status(String statusOfSearchingElement, boolean click) {
                String searchingElement = "new UiSelector().resourceId(\"com.ajaxsystems:id/status\").text(\"" + statusOfSearchingElement + "\")";
                return search(searchingElement, click);
            }

            private boolean search(String searchingElement, boolean click) {
                RemoteWebElement elementInScrollList;
                try {
                    Base.log(1, "wait for scrollable view");
                    Base.log(1, "scrollable view is found: id = \"" + driver.findElementByAndroidUIAutomator("new UiSelector().scrollable(true)").getAttribute("resourceId") + "\"");

                    Base.log(1, "search element: " + searchingElement);
                    try {
                        elementInScrollList = (RemoteWebElement) driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(" + searchingElement + ");");
                    } catch (Exception e) {
                        Base.log(3, "element is not found: \n\n" + e.getMessage() + "\n", true);
                        return false;
                    }

                } catch (NoSuchElementException e) {
                    try {
                        Base.log(1, "scrollable view is not found");
                        elementInScrollList = (RemoteWebElement) driver.findElementByAndroidUIAutomator(searchingElement);

                    } catch (NoSuchElementException e1) {
                        Base.log(3, "element not found: \n\n" + e1.getMessage() + "\n", true);
                        return false;
                    }
                }

                Base.log(1, "element found: id = \"" + elementInScrollList.getAttribute("resourceId") + "\", text = \"" + elementInScrollList.getText() + "\"", true);
                if (click) {
                    Base.log(1, "tap found element");
                    elementInScrollList.click();
                }
                return true;
            }
        }
    }

//----------------------------------------------------------------------------------------------------------------------

//    public void scrollTop(){
//        Base.log(1, "scroll to the top page");
//        scrollScreenToTheEnd("down");
//    }
//
//    public void scrollBottom(){
//        Base.log(1, "scroll to the bottom page");
//        scrollScreenToTheEnd("up");
//    }
//
//    /**
//     * This method just scrolls current screen to the end with needed direction ("up" or "down")
//     * @param direction "up"    - for scrolling to the end of screen
//     *                  "down"  - for scrolling to the top of screen
//     */
//    private void scrollScreenToTheEnd(String direction){
//        Base.log(4, "Method is started");
//        start = System.nanoTime();
//        flag = false;
//        counter = 0;
//        etalon.clear();
//
//        Base.log(4, "copy text objects from scrollList to etalon and current lists");
//        Base.log(4, "number of all text objects from this screen: " + allTextObjects.size());
//
//        etalon.addAll(putNewObjectsInCurrentList());
//
//        while (true){
//            if (direction.equals("down")) {
//                swipeDown(200, 2);
//            }else swipeUp(200, 2);
//
//            compare(etalon, putNewObjectsInCurrentList());
//            if (flag) break;
//        }
//        finish = System.nanoTime();
//        Base.log(4, "time " + String.format("%4.2f",(float)(finish - start)/1000000000) + " sec");
//        Base.log(4, "Method is finished");
//    }
//
//    private ArrayList<String> putNewObjectsInCurrentList(){
//        Base.log(4, "put new text objects to the current list after swipe");
//        current.clear();
//        try {
//            for (WebElement i : allTextObjects) {
//                current.add(i.getText());
//            }
//        }catch (NoSuchElementException e){
//            Base.log(3, "NoSuchElementException, something was wrong!\n" + e);
//        }
//        return current;
//    }

//
//    /**
//     * This class scrolls current screen to the element with needed parameter and direction ("up" or "down") and gives you
//     * the opportunity to click this element if you want
//     *
//     * @return true if method found the element (and click them if it required)
//     */
//
//    public class ScrollToElementWith{
//
//        public boolean text(String textOfSearchingElement, boolean click){
//            return  scrollToElementWith("text", "up", textOfSearchingElement, click);
//        }
//
//        public boolean email(String textOfSearchingElement, boolean click){
//            return  scrollToElementWith("email", "up", textOfSearchingElement, click);
//        }
//
//        public boolean id(String textOfSearchingElement, boolean click){
//            return  scrollToElementWith("id", "up", textOfSearchingElement, click);
//        }
//
//        public boolean name(String textOfSearchingElement, boolean click){
//            return  scrollToElementWith("name", "up", textOfSearchingElement, click);
//        }
//
//        public boolean type(String typeOfElement, String textOfSearchingElement, boolean click){
//            return  scrollToElementWith(typeOfElement, "up", textOfSearchingElement, click);
//        }
//
//
//        private boolean scrollToElementWith(String typeOfElement, String direction, String textOfSearchingElement, boolean click) {
//            start = System.nanoTime();
//            Base.log(4, "Method is started");
//            result = false;
//            WebElement searchingElement;
//            scrollTop();
//
//            counter = 0;
//            etalon.clear();
//            current.clear();
//
//            Base.log(4, "get all text objects from this screen");
//            Base.log(4, "number of all text objects from this screen: " + allTextObjects.size());
//            try {
//                for (WebElement i : allTextObjects) {
//                    etalon.add(i.getText());
//                    current.add(i.getText());
//                }
//            } catch (NoSuchElementException e) {
//                Base.log(3, "NoSuchElementException: \n\n" + e + "\n");
//            }
//            while (true){
//                if (current.contains(textOfSearchingElement)){
//                    Base.log(4, "text \"" + textOfSearchingElement + "\" is shown on this screen");
//                    try {
//                        switch (typeOfElement){
//                            case "id":
//                                searchingElement = driver.findElement(By.id("\"" + textOfSearchingElement + "\""));
//                                break;
//                            case "text":
//                                searchingElement = driver.findElement(By.xpath("//android.widget.TextView[@text='" + textOfSearchingElement + "']"));
//                                break;
//                            case "email":
//                                searchingElement = driver.findElement(By.xpath("//*[contains(@resource-id,'com.ajaxsystems:id/mail') and @text='" + textOfSearchingElement + "']"));
//                                break;
//                            case "name":
//                                searchingElement = driver.findElement(By.xpath("//*[contains(@resource-id,'com.ajaxsystems:id/name') and @text='" + textOfSearchingElement + "']"));
//                                break;
//                            default:
//                                searchingElement = driver.findElement(By.xpath("//android.widget.TextView[@text='" + textOfSearchingElement + "']"));
//                                break;
//                        }
//
//                        if (click) {
//                            searchingElement.click();
//                            Base.log(1, "element was found and clicked, text: \"" + textOfSearchingElement + "\"");
//                        } else {
//                            Base.log(1, "element was found with text \"" + textOfSearchingElement + "\"");
//                        }
//                        result = true;
//                        break;
//
//                    }catch (NoSuchElementException e){
//                        Base.log(3, "NoSuchElementException, element is not found on this screen!");
//                    }
//                }else Base.log(1, "element is not found on this screen, text: \"" + textOfSearchingElement + "\"");
//
//                if (direction.equals("down")) {
//                    swipeDown(1000, 2);
//                }else swipeUp(1000, 2);
//
//                current.clear();
//                Base.log(4, "put new text objects to the current list after swipe");
//                try {
//                    for (WebElement i : allTextObjects) {
//                        current.add(i.getText());
//                    }
//                }catch (NoSuchElementException e){
//                    Base.log(3, "NoSuchElementException, text elements is not found on the screen\n" + e);
//                }
//
//                compare(etalon, current);
//                if (flag) break;
//            }
//            finish = System.nanoTime();
//            Base.log(4, "time " + String.format("%4.2f",(float)(finish - start)/1000000000) + " sec");
//            Base.log(4, "Method is finished");
//            return result;
//        }
//    }

//----------------------------------------------------------------------------------------------------------------------
//
//    public boolean scrollToElement(WebElement elementForSearch, String direction) {
//        start = System.nanoTime();
//        Base.log(4, "Method is started");
//        result = false;
//
//        counter = 0;
//        etalon.clear();
//        current.clear();
//
//        Base.log(1, "get all text objects from this screen");
//        for (WebElement i : allTextObjects) {
//            etalon.add(i.getText());
//            current.add(i.getText());
//        }
//
//        while (true){
//
//            if (base.wait.element(elementForSearch, 2, true)){
//                result = true;
//                break;
//
//            }else {
//                Base.log(3, "element is not found on this screen!");
//                result = false;
//            }
//
//            if (direction.equals("down")) {
//                swipeDown(1000, 2);
//            }else swipeUp(1000, 2);
//
//            current.clear();
//            Base.log(1, "put new text objects to the current list after swipe");
//            try {
//                for (WebElement i : allTextObjects) {
//                    current.add(i.getText());
//                }
//            }catch (NoSuchElementException e){
//                Base.log(3, "NoSuchElementException, something was wrong!\n" + e);
//            }
//
//            compare(etalon, current);
//            if (flag) break;
//        }
//        finish = System.nanoTime();
//        Base.log(4, "time " + String.format("%4.2f",(float)(finish - start)/1000000000) + " sec");
//        Base.log(4, "Method is finished");
//        return result;
//    }


//----------------------------------------------------------------------------------------------------------------------

//    private ArrayList<String> compare(ArrayList<String> etalon, ArrayList<String> current) {
//        flag = false;
//        if (etalon.containsAll(current) && counter < 1) {
//            counter++;
//            Base.log(4, "nothing was changed, swipe try count = " + counter);
//        } else if (counter >= 1) {
//            Base.log(3, "the end of the list is reached");
//            flag = true;
//        } else {
//            counter = 0;
//            etalon.clear();
//            etalon.addAll(current);
//            flag = false;
//        }
//        return etalon;
//    }


//----------------------------------------------------------------------------------------------------------------------
// Touch
//----------------------------------------------------------------------------------------------------------------------

    public class Touch {
        public void tap(WebElement element) {
            driver.performTouchAction(new TouchAction(driver).tap(element));
        }

        public void longPress(WebElement element, int timer) {
            driver.performTouchAction(new TouchAction(driver).longPress(element, timer));
        }
    }

//----------------------------------------------------------------------------------------------------------------------
// Tap Button
//----------------------------------------------------------------------------------------------------------------------
    @AndroidFindBy(id = "com.ajaxsystems:id/wizard")
    private WebElement wizardButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/back")
    private WebElement backButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/next")
    private WebElement nextButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/settings")
    private WebElement settingsButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/add")
    private WebElement addButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/addDevice")
    private WebElement addDeviceButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/confirm_button")
    private WebElement confirmButton;   // PIN

    @AndroidFindBy(id = "com.ajaxsystems:id/ok")
    private WebElement okButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/cancel_button")
    private WebElement cancelButton;    // PIN

    @AndroidFindBy(id = "com.ajaxsystems:id/cancel")
    private WebElement cancel; // PIN

    public WebElement getCancel() {
        return cancel;
    }
    public WebElement getCancelButton() {
        return cancelButton;
    }
    public WebElement getSettingsButton() {
        return settingsButton;
    }

    public class TapButton {

        public void back() {
            Base.log(1, "tap Back button");
            backButton.click();
        }

        public void next() {
            Base.log(1, "tap Next button");
            nextButton.click();
        }

        public void addManually() {
            Base.log(1, "tap Add Manually button", true);
            nextButton.click();
        }

        public void useWizard() {
            Base.log(1, "tap Use Wizard button", true);
            wizardButton.click();
        }

        public void sendInvitation() {
            Base.log(1, "tap Send Invitation button",true);
            nextButton.click();
        }

        public void save() {
            Base.log(1, "tap Save button", true);
            nextButton.click();
        }

        public void add() {
            Base.log(1, "tap Add button", true);
            try {
                addButton.click();
            }catch (Exception e){
                addDeviceButton.click();
            }
        }

        public void ok() {
            Base.log(1, "tap OK button", true);
            okButton.click();
        }

        public void cancel() {
            Base.log(1, "tap Cancel button", true);
            try {
                cancelButton.click();
            }catch (Exception e){
                cancel.click();
            }
        }

        public void confirm() {
            Base.log(1, "tap Confirm button", true);
            confirmButton.click();
        }

        public void settings() {
            Base.log(1, "wait for Settings Button");
            WebDriverWait iWait = new WebDriverWait(driver, 5);
            iWait.until(ExpectedConditions.visibilityOf(settingsButton));
            Base.log(1, "tap Settings Button", true);
            settingsButton.click();
        }

        public void withId(String id) {
            base.wait.element(driver.findElement(By.id(id)), 10, false);
            driver.findElement(By.id(id)).click();
        }

        public void withXpath(String Xpath) {
            base.wait.element(driver.findElement(By.xpath(Xpath)), 10, false);
            driver.findElement(By.xpath(Xpath)).click();
        }

        public void withText(String text) {
            base.nav.scroll.toElementWith.text(text, true);
        }
    }

//----------------------------------------------------------------------------------------------------------------------
//  GoToPage
//----------------------------------------------------------------------------------------------------------------------

    @AndroidFindBy(id = "com.ajaxsystems:id/devices")
    private WebElement footerDevices;

    @AndroidFindBy(id = "com.ajaxsystems:id/rooms")
    private WebElement footerRooms;

    @AndroidFindBy(id = "com.ajaxsystems:id/notifications")
    private WebElement footerNotifications;

    @AndroidFindBy(id = "com.ajaxsystems:id/remote")
    private WebElement footerRemote;

    public class GoToPage {

        public void devices() {
            Base.log(1, "go to devices Page", true);
            backToDashboard();
            Base.log(1, "tap devices Button");
            footerDevices.click();
        }

        public void rooms() {
            Base.log(1, "go to rooms Page", true);
            backToDashboard();
            Base.log(1, "tap rooms Button");
            footerRooms.click();
        }

        public void remote() {
            Base.log(1, "go to Remote Page", true);
            backToDashboard();
            Base.log(1, "tap Remote Button");
            footerRemote.click();
        }

        public void notifications() {
            Base.log(1, "go to Notifications Page", true);
            backToDashboard();
            Base.log(1, "tap Notifications Button");
            footerNotifications.click();
        }

        public void registration() {
            Base.log(1, "go to Registration Page", true);
            base.introPage.clickRegistrationButton();
        }

        public void authorization() {
            Base.log(1, "go to Authorization Page", true);
            base.introPage.clickAuthorizationButton();
        }

        public void hubSettings() {
            Base.log(1, "go to HUB Settings Page", true);
            gotoPage.devices();

            Base.log(1, "tap HubImage on devices Page");
            base.hub.getHubImageOnDeviceList().click();
            tapButton.settings();
        }

        public void userList() {
            Base.log(1, "go to the Device List page");
            gotoPage.devices();

            Base.log(1, "tap Hub image on Device List page");
            base.hub.getHubImageOnDeviceList().click();

            Base.log(1, "tap Hub Settings button");
            base.hub.getSettingsButton().click();

            Base.log(1, "tap Users tab");
            base.hub.getHubSettingsUsersImage().click();

            Base.log(1, "waiting User Status element");
            base.wait.element(base.hub.getUserStatus(), 10, true);
        }

        public void inviteUser() {
            Base.log(1, "go to the User List page");
            userList();

            String sendInvitesButtonText = base.getLocalizeTextForKey("send_invites");
            Base.log(4, "sendInvitesButtonText: \"" + sendInvitesButtonText + "\"");

            Base.log(1, "searching and clicking the Send Invites Button");
            base.nav.scroll.toElementWith.text(sendInvitesButtonText, true);

            Base.log(1, "click the Add From Contact List Button");
            base.user.getAddButtonFromContactList().click();
        }

        private void backToDashboard() {
            Base.log(1, "back to dashboard");
            while (!base.wait.element(base.header.getMenuDrawer(), 2, true)) {
                if (base.wait.element(backButton, 1, true)) {
                    Base.log(1, "tap back button");
                    backButton.click();

                } else if (base.check.isPresent.popUpWithConfirmation(2)) {
                    cancelIt();

                } else {
                Base.log(3, "Dashboard is not reached");
                }
            }
            Base.log(1, "Dashboard is reached");
        }
    }

//----------------------------------------------------------------------------------------------------------------------
// CONFIRMATION
//----------------------------------------------------------------------------------------------------------------------

    public void confirmIt() {
        Base.log(1, "Confirm", true);
        try {
            tapButton.ok();
        } catch (Exception e) {

            try {
                tapButton.confirm();
            } catch (Exception e1) {
                try {
                    tapButton.add();
                } catch (Exception e2) {
                    Base.log(1, "confirm button is not found");
                }
            }
        }
    }

    public void cancelIt() {
        Base.log(1, "Cancel", true);
        try {
            cancel.click();
            Base.log(1, "Cancel button is pressed (cancel)");
        } catch (Exception e) {

            try {
                cancelButton.click();
                Base.log(1, "Cancel button is pressed (cancelButton)");
            } catch (Exception e1) {
                Base.log(4, "Cancel Button was not found");
            }
        }
    }

}
