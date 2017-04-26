package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Base;

import java.util.ArrayList;
import java.util.List;


public class Navigation{



    @AndroidFindBy(id = "com.ajaxsystems:id/cancel")
    private WebElement cancel;

    @AndroidFindBy(id = "com.ajaxsystems:id/cancel_button")
    private WebElement cancelButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/ok")
    private WebElement okBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/confirm_button")
    private WebElement confirmButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/add")
    private WebElement addButton;

    public WebElement getConfirmButton() {
        return confirmButton;
    }
    public WebElement getCancelButton() {
        return cancelButton;
    }
    public WebElement getCancel() {
        return cancel;
    }

//----------------------------------------------------------------------------------------------------------------------

    @AndroidFindBy(xpath = "//*[@resource-id='com.ajaxsystems:id/rrv_recycler_view']//android.widget.RelativeLayout/android.widget.TextView")
    private List<WebElement> userList;

    @AndroidFindBy(xpath = "//*[@resource-id='com.ajaxsystems:id/recycler']//android.widget.LinearLayout/android.widget.TextView")
    private List<WebElement> roomList;

    @AndroidFindBy(xpath = "//*[@resource-id='com.ajaxsystems:id/recycler']//android.widget.LinearLayout/android.widget.TextView")
    private List<WebElement> deviceList;

    @AndroidFindBy(xpath = "//android.widget.LinearLayout//android.widget.TextView")
    private ArrayList<WebElement> scrollList;

    @AndroidFindBy(xpath = "//android.widget.TextView")
    private ArrayList<WebElement> allTextObjects;

    public ArrayList<WebElement> getScrollList() {
        return scrollList;
    }

//======================================================================================================================
// Header
//======================================================================================================================



    @AndroidFindBy(id = "com.ajaxsystems:id/back")
    private WebElement backButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/next")
    private WebElement nextButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/name")
    private WebElement nameField;

    @AndroidFindBy(id = "com.ajaxsystems:id/image")
    private WebElement image;

    @AndroidFindBy(id = "com.ajaxsystems:id/settings")
    private WebElement settingsButton;

    public WebElement getBackButton() {
        return backButton;
    }
    public WebElement getSettingsButton() {
        return settingsButton;
    }

//======================================================================================================================
// Footer
//======================================================================================================================

    @AndroidFindBy(id = "com.ajaxsystems:id/devices")
    private WebElement footerDevices;

    @AndroidFindBy(id = "com.ajaxsystems:id/rooms")
    private WebElement footerRooms;

    @AndroidFindBy(id = "com.ajaxsystems:id/notifications")
    private WebElement footerNotifications;

    @AndroidFindBy(id = "com.ajaxsystems:id/remote")
    private WebElement footerRemote;

//======================================================================================================================
// Anchors
//======================================================================================================================

    @AndroidFindBy(id = "com.ajaxsystems:id/spaceControl")
    private WebElement spaceControlImage;

//----------------------------------------------------------------------------------------------------------------------
    private final Base base;
    private final AppiumDriver driver;
    private boolean result;
    private long start, finish;
    private boolean flag;
    private int counter;
    private ArrayList<String> etalon;
    private ArrayList<String> current;
    public GoToPage gotoPage = new GoToPage();
    public ScrollToElementWith scrollToElementWith = new ScrollToElementWith();

    public Navigation(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        etalon = new ArrayList<>();
        current = new ArrayList<>();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
//----------------------------------------------------------------------------------------------------------------------
// Swipe
//----------------------------------------------------------------------------------------------------------------------
    public void swipeUp() {
        Base.log(4, "Method is started");

        Dimension screenSize = driver.manage().window().getSize();
        Base.log(1, "Screen dimension is " + screenSize);

        int startX = (int)(screenSize.width / 2.00);
        int startY = (int)(screenSize.height / 3.00);
        int endX = startX;
        int endY = (int)(screenSize.height / 100.00);
        int duration = 1500;

        Base.log(1, "swipe(startX, startY, endX, endY, duration) [" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + duration + "]");
        driver.swipe(startX, startY, endX, endY, duration);

        Base.log(4, "Method is finished");
    }

    public void swipeUp(int duration, double heightPart) {
        Base.log(4, "Method is started");

        Dimension screenSize = driver.manage().window().getSize();
        Base.log(1, "Screen dimension is " + screenSize);

        int startX = (int)(screenSize.width / 2.00);
        int startY = (int)(screenSize.height / heightPart);
        int endX = startX;
        int endY = (int)(screenSize.height / 100.00);

        Base.log(1, "swipe(startX, startY, endX, endY, duration) [" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + duration + "]");
        driver.swipe(startX, startY, endX, endY, duration);

        Base.log(4, "Method is finished");
    }

    public void swipeDown() {
        Base.log(4, "Method is started");

        Dimension screenSize = driver.manage().window().getSize();
        Base.log(1, "Screen dimension is " + screenSize);

        int startX = (int)(screenSize.width / 2.00);
        int startY = (int)(screenSize.height / 3.00);
        int endX = startX;
        int endY = (int)(screenSize.height / 1.05);
        int duration = 1500;

        Base.log(1, "swipe(startX, startY, endX, endY, duration) [" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + duration + "]");
        driver.swipe(startX, startY, endX, endY, duration);

        Base.log(4, "Method is finished");
    }

    public void swipeDown(int duration, double heightPart) {
        Base.log(4, "Method is started");

        Dimension screenSize = driver.manage().window().getSize();
        Base.log(1, "Screen dimension is " + screenSize);

        int startX = (int)(screenSize.width / 2.00);
        int startY = (int)(screenSize.height / heightPart);
        int endX = startX;
        int endY = (int)(screenSize.height / 1.05);

        Base.log(1, "swipe(startX, startY, endX, endY, duration) [" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + duration + "]");
        driver.swipe(startX, startY, endX, endY, duration);

        Base.log(4, "Method is finished");
    }
//======================================================================================================================
// Scroll
//======================================================================================================================

    public void scrollTop(){scrollScreenToTheEnd("down");}

    public void scrollBottom(){scrollScreenToTheEnd("up");}

    /**
     * This method just scrolls current screen to the end with needed direction ("up" or "down")
     * @param direction "up"    - for scrolling to the end of screen
     *                  "down"  - for scrolling to the top of screen
     */
    private void scrollScreenToTheEnd(String direction){
        Base.log(4, "Method is started");
        start = System.nanoTime();
        flag = false;
        counter = 0;
        etalon.clear();
        current.clear();

        Base.log(4, "copy text objects from scrollList to etalon and current lists");
        for (WebElement i : scrollList) {
            etalon.add(i.getText());
            current.add(i.getText());
        }

        while (true){
            if (direction.equals("down")) {
                swipeDown(200, 2);
            }else swipeUp(200, 2);

            current.clear();
            Base.log(4, "put new text objects to the current list after swipe");
            try {
                for (WebElement i : scrollList) {
                  current.add(i.getText());
                }
            }catch (NoSuchElementException e){
                Base.log(3, "NoSuchElementException, something was wrong!\n" + e);
            }

            compare(etalon, current);
            if (flag) break;
        }
        finish = System.nanoTime();
        Base.log(4, "time " + String.format("%4.2f",(float)(finish - start)/1000000000) + " sec");
        Base.log(4, "Method is finished");
    }

//======================================================================================================================

    /**
     * This method scrolls current screen to the element with needed text and direction ("up" or "down") and gives you
     * the opportunity to click this element if you want
     *
     * @return true if method found the element (and click them if it required)
     */

    public class ScrollToElementWith{

        public boolean text(String textOfSearchingElement, boolean click){
            return  scrollToElementWith("text", "up", textOfSearchingElement, click);
        }

        public boolean email(String textOfSearchingElement, boolean click){
            return  scrollToElementWith("email", "up", textOfSearchingElement, click);
        }

        public boolean id(String textOfSearchingElement, boolean click){
            return  scrollToElementWith("id", "up", textOfSearchingElement, click);
        }

        public boolean name(String textOfSearchingElement, boolean click){
            return  scrollToElementWith("name", "up", textOfSearchingElement, click);
        }

        public boolean type(String typeOfElement, String textOfSearchingElement, boolean click){
            return  scrollToElementWith(typeOfElement, "up", textOfSearchingElement, click);
        }


        private boolean scrollToElementWith(String typeOfElement, String direction, String textOfSearchingElement, boolean click) {
            start = System.nanoTime();
            Base.log(4, "Method is started");
            result = false;
            WebElement searchingElement;

            counter = 0;
            etalon.clear();
            current.clear();

            Base.log(1, "get all text objects from this screen");
            for (WebElement i : allTextObjects) {
                etalon.add(i.getText());
                current.add(i.getText());
            }

            while (true){
                if (current.contains(textOfSearchingElement)){
                    try {
                        switch (typeOfElement){
                            case "id":
                                searchingElement = driver.findElement(By.id("\"" + textOfSearchingElement + "\""));
                                break;
                            case "text":
                                searchingElement = driver.findElement(By.xpath("//android.widget.TextView[@text='" + textOfSearchingElement + "']"));
                                break;
                            case "email":
                                searchingElement = driver.findElement(By.xpath("//*[contains(@resource-id,'com.ajaxsystems:id/mail') and @text='" + textOfSearchingElement + "']"));
                                break;
                            case "name":
                                searchingElement = driver.findElement(By.xpath("//*[contains(@resource-id,'com.ajaxsystems:id/name') and @text='" + textOfSearchingElement + "']"));
                                break;
                            default:
                                searchingElement = driver.findElement(By.xpath("//android.widget.TextView[@text='" + textOfSearchingElement + "']"));
                                break;
                        }

                        if (click) {
                            searchingElement.click();
                            Base.log(1, "element with text \"" + textOfSearchingElement + "\" was found and clicked");
                        } else {
                            Base.log(1, "element with text \"" + textOfSearchingElement + "\" was found");
                        }
                        result = true;
                        break;

                    }catch (NoSuchElementException e){
                        Base.log(3, "NoSuchElementException, element is not found on this screen!");
                    }
                }else Base.log(3, "element with text \"" + textOfSearchingElement + "\" is not found on this screen!");

                if (direction.equals("down")) {
                    swipeDown(1000, 2);
                }else swipeUp(1000, 2);

                current.clear();
                Base.log(1, "put new text objects to the current list after swipe");
                try {
                    for (WebElement i : allTextObjects) {
                        current.add(i.getText());
                    }
                }catch (NoSuchElementException e){
                    Base.log(3, "NoSuchElementException, something was wrong!\n" + e);
                }

                compare(etalon, current);
                if (flag) break;
            }
            finish = System.nanoTime();
            Base.log(4, "time " + String.format("%4.2f",(float)(finish - start)/1000000000) + " sec");
            Base.log(4, "Method is finished");
            return result;
        }
    }



    public boolean scrollToElement(WebElement elementForSearch, String direction) {
        start = System.nanoTime();
        Base.log(4, "Method is started");
        result = false;

        counter = 0;
        etalon.clear();
        current.clear();

        Base.log(1, "get all text objects from this screen");
        for (WebElement i : allTextObjects) {
            etalon.add(i.getText());
            current.add(i.getText());
        }

        while (true){

            if (base.wait.element(elementForSearch, 2, true)){
                result = true;
                break;

            }else {
                Base.log(3, "element is not found on this screen!");
                result = false;
            }

            if (direction.equals("down")) {
                swipeDown(1000, 2);
            }else swipeUp(1000, 2);

            current.clear();
            Base.log(1, "put new text objects to the current list after swipe");
            try {
                for (WebElement i : allTextObjects) {
                    current.add(i.getText());
                }
            }catch (NoSuchElementException e){
                Base.log(3, "NoSuchElementException, something was wrong!\n" + e);
            }

            compare(etalon, current);
            if (flag) break;
        }
        finish = System.nanoTime();
        Base.log(4, "time " + String.format("%4.2f",(float)(finish - start)/1000000000) + " sec");
        Base.log(4, "Method is finished");
        return result;
    }


//======================================================================================================================

    private ArrayList <String> compare(ArrayList<String> etalon, ArrayList<String> current){
        Base.log(4, "Method is started");
        flag = false;

        if (etalon.containsAll(current) && counter < 1) {
            counter++;
            Base.log(3, "nothing was changed, swipe try count = " + counter);
        }else if (counter >= 1 ){
                Base.log(3, "the end of the list is reached");
                flag = true;
        } else {
            counter = 0;
            etalon.clear();
            etalon.addAll(current);
            flag = false;
        }
        Base.log(4, "Method is finished");
       return etalon;
    }


//======================================================================================================================
// Tap
//======================================================================================================================

    public void longTapButton(WebElement element, int timer) {
        Base.log(4, "Method is started");

        Base.log(4, "long tap for " + timer + " seconds");
        timer = timer * 1000;
        driver.tap(1, element, timer);

        Base.log(4, "Method is finished");
    }

//======================================================================================================================
// GOTO
//======================================================================================================================
    public void nextButtonClick(){nextButton.click();}

    public WebElement getNextButton() {return nextButton;}

    public void goBack() {
        Base.log(4, "tap Back button");
        backButton.click();
    }

    public void goToSettings(){
        WebDriverWait iWait = new WebDriverWait(driver, 5);
        iWait.until(ExpectedConditions.visibilityOf(settingsButton));
        settingsButton.click();
    }

    public boolean goToTheRemotePage(){
        Base.log(4, "Method is started");
        result = false;

        try {
            while (backButton.isDisplayed()) {
                goBack();
            }
        }catch (NoSuchElementException e){
            Base.log(3, "BackButton is not shown");
        }

        footerRemote.click();

        if (spaceControlImage.isDisplayed()) {
            result = true;
        }
        Base.log(4, "Method is finished");
        return result;
    }

//----------------------------------------------------------------------------------------------------------------------
//  GoToPage
//----------------------------------------------------------------------------------------------------------------------
    public class GoToPage {

        // dashboard
        public void Devices() {
            backToDashboard();
            Base.log(1, "tap Devices Button");
            footerDevices.click();
        }

        public void Rooms() {
            backToDashboard();
            Base.log(1, "tap Rooms Button");
            footerRooms.click();
        }

        public void Notifications() {
            backToDashboard();
            Base.log(1, "tap Notifications Button");
            footerNotifications.click();
        }

        public void Remote() {
            Base.log(1, "back to Dashboard");
            backToDashboard();
            Base.log(1, "tap Remote Button");
            footerRemote.click();
        }

        public void Registration() {
            Base.log(1, "tap Registration Button");
            base.introPage.getRegistrationBtn().click();
        }

        public void hubSettings() {
            gotoPage.Devices();
            base.hub.getHubImageOnDeviceList().click();
            base.hub.getSettingsButton().click();
        }

        public void userList() {
            Base.log(1, "go to the Device List page");
            gotoPage.Devices();

            Base.log(1, "click Hub image on Device List page");
            base.hub.getHubImageOnDeviceList().click();

            Base.log(1, "click Users tab");
            base.hub.getSettingsButton().click();

            Base.log(1, "click Users tab");
            base.hub.getHubSettingsUsersImage().click();

            base.wait.element(base.hub.getUserStatus(), 10, true);
        }


        private void backToDashboard(){
            while (!base.wait.element(base.dashboardHeader.getMenuDrawer(), 2, true)) {

                if (base.wait.element(backButton, 1, true)) {
                    Base.log(1, "tap back button");
                    backButton.click();

                } else if (cancelButton.isDisplayed() || cancel.isDisplayed()) {
                    cancelIt();

                } else {
                    Base.log(3, "Dashboard is not reached");
                }
            }
            Base.log(1, "Dashboard is reached");
        }
    }

//======================================================================================================================
// CONFIRMATION
//======================================================================================================================
    public void confirmIt() {

        try {
            start = System.nanoTime();
            okBtn.click();
            Base.log(1, "OK button is pressed");
        }catch (Exception e){
            Base.log(4, "OK Button was not found\n\n" + e.getMessage() + "\n");

            finish = System.nanoTime();
            Base.log(4, "time " + String.format("%4.2f",(float)(finish - start)/1000000000) + " sec");

            try {
                start = System.nanoTime();
                confirmButton.click();
                Base.log(1, "Confirm button is pressed");
            }catch (Exception e1){
                finish = System.nanoTime();
                Base.log(4, "time " + String.format("%4.2f",(float)(finish - start)/1000000000) + " sec");
                Base.log(4, "Confirm button was not found\n\n" + e1.getMessage() + "\n");

                try {
                    start = System.nanoTime();
                    addButton.click();
                    Base.log(1, "Confirm button is pressed");
                }catch (Exception e2){
                    finish = System.nanoTime();
                    Base.log(4, "time " + String.format("%4.2f",(float)(finish - start)/1000000000) + " sec");
                    Base.log(4, "Add button was not found\n\n" + e2.getMessage() + "\n");
                }
            }
        }
    }

    public void cancelIt() {
        try {
            cancel.click();
            Base.log(1, "Cancel button is pressed");
        }catch (Exception e){
            Base.log(4, "first Cancel Button was not found\n\n" + e.getMessage() + "\n");

            try {
                cancelButton.click();
                Base.log(1, "Cancel button is pressed");
            }catch (Exception e1){
                Base.log(4, "second Cancel Button was not found\n\n" + e.getMessage() + "\n");
            }
        }
    }

    public void cancelPopUpIfPresent() {
        cancelIt();
    }

}
