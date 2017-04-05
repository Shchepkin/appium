package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pages.Base;

import java.util.ArrayList;
import java.util.List;


public class Navigation extends Base{
    private final AppiumDriver driver;
    private boolean result;

    private long start, finish;
    private boolean flag;
    private int counter;
    private ArrayList<String> etalon = new ArrayList<>();
    private ArrayList<String> current = new ArrayList<>();

    @AndroidFindBy(id = "com.ajaxsystems:id/cancel")
    private WebElement cancelBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/cancel_button")
    private WebElement cancelButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/ok")
    private WebElement okBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/confirm_button")
    private WebElement confirmButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/add")
    private WebElement addButton;


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
    private WebElement backBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/next")
    private WebElement nextButton;

    public void nextButtonClick(){nextButton.click();}
    public WebElement getNextButton() {return nextButton;}

    @AndroidFindBy(id = "com.ajaxsystems:id/name")
    private WebElement nameField;

    @AndroidFindBy(id = "com.ajaxsystems:id/image")
    private WebElement image;

    @AndroidFindBy(id = "com.ajaxsystems:id/settings")
    private WebElement settingsBtn;

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
    private WebElement spaceControlImageOnRemotePage;




//======================================================================================================================
    public Navigation(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

//======================================================================================================================
// Swipe
//======================================================================================================================
    public void swipeUp() {
        log("Method is started");

        Dimension screenSize = driver.manage().window().getSize();
        log("Screen dimension is " + screenSize);

        int startX = (int)(screenSize.width / 2.00);
        int startY = (int)(screenSize.height / 3.00);
        int endX = startX;
        int endY = (int)(screenSize.height / 100.00);
        int duration = 1500;

        log("swipe(startX, startY, endX, endY, duration) [" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + duration + "]");
        driver.swipe(startX, startY, endX, endY, duration);

        log("Method is finished");
    }

    public void swipeUp(int duration, double heightPart) {
        log("Method is started");

        Dimension screenSize = driver.manage().window().getSize();
        log("Screen dimension is " + screenSize);

        int startX = (int)(screenSize.width / 2.00);
        int startY = (int)(screenSize.height / heightPart);
        int endX = startX;
        int endY = (int)(screenSize.height / 100.00);

        log("swipe(startX, startY, endX, endY, duration) [" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + duration + "]");
        driver.swipe(startX, startY, endX, endY, duration);

        log("Method is finished");
    }

    public void swipeDown() {
        log("Method is started");

        Dimension screenSize = driver.manage().window().getSize();
        log("Screen dimension is " + screenSize);

        int startX = (int)(screenSize.width / 2.00);
        int startY = (int)(screenSize.height / 3.00);
        int endX = startX;
        int endY = (int)(screenSize.height / 1.05);
        int duration = 1500;

        log("swipe(startX, startY, endX, endY, duration) [" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + duration + "]");
        driver.swipe(startX, startY, endX, endY, duration);

        log("Method is finished");
    }

    public void swipeDown(int duration, double heightPart) {
        log("Method is started");

        Dimension screenSize = driver.manage().window().getSize();
        log("Screen dimension is " + screenSize);

        int startX = (int)(screenSize.width / 2.00);
        int startY = (int)(screenSize.height / heightPart);
        int endX = startX;
        int endY = (int)(screenSize.height / 1.05);

        log("swipe(startX, startY, endX, endY, duration) [" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + duration + "]");
        driver.swipe(startX, startY, endX, endY, duration);

        log("Method is finished");
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
        log("Method is started");
        start = System.nanoTime();
        flag = false;
        counter = 0;
        etalon.clear();
        current.clear();

        log("copy text objects from scrollList to etalon and current lists");
        for (WebElement i : scrollList) {
            etalon.add(i.getText());
            current.add(i.getText());
        }

        while (true){
            if (direction.equals("down")) {
                swipeDown(200, 2);
            }else swipeUp(200, 2);

            current.clear();
            log("put new text objects to the current list after swipe");
            try {
                for (WebElement i : scrollList) {
                  current.add(i.getText());
                }
            }catch (NoSuchElementException e){
                log(3, "NoSuchElementException, something was wrong!\n" + e);
            }

            compare(etalon, current);
            if (flag) break;
        }
        finish = System.nanoTime();
        log(2, "time " + String.format("%4.2f",(float)(finish - start)/1000000000) + " sec");
        log("Method is finished");
    }

//======================================================================================================================

    /**
     * This method scrolls current screen to the element with needed text and direction ("up" or "down") and gives you
     * the opportunity to click this element if you want
     * @param typeOfElement text, email, name, id
     *
     * @param direction "up"    - for scrolling to the end of screen
     *                  "down"  - for scrolling to the top of screen
     *
     * @param textOfSearchingElement - text which Searching Element contains
     *
     * @param click - true - if you want to click the element
     *                false - if you don't need to click the element
     *
     * @return true if method found the element (and click them if it required)
     */

    public boolean scrollToElementWith(String typeOfElement, String direction, String textOfSearchingElement, boolean click) {
        start = System.nanoTime();
        log("Method is started");
        result = false;
        WebElement searchingElement;

        counter = 0;
        etalon.clear();
        current.clear();

        log("get all text objects from this screen");
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
                        log("element with text \"" + textOfSearchingElement + "\" was found and clicked");
                    } else {
                        log("element with text \"" + textOfSearchingElement + "\" was found");
                    }
                    result = true;
                    break;

                }catch (NoSuchElementException e){
                    log(3, "NoSuchElementException, element is not found on this screen!");
                }
            }else log(3, "element with text \"" + textOfSearchingElement + "\" is not found on this screen!");

            if (direction.equals("down")) {
                swipeDown(1000, 2);
            }else swipeUp(1000, 2);

            current.clear();
            log("put new text objects to the current list after swipe");
            try {
                for (WebElement i : allTextObjects) {
                    current.add(i.getText());
                }
            }catch (NoSuchElementException e){
                log(3, "NoSuchElementException, something was wrong!\n" + e);
            }

            compare(etalon, current);
            if (flag) break;
        }
        finish = System.nanoTime();
        log(2, "time " + String.format("%4.2f",(float)(finish - start)/1000000000) + " sec");
        log("Method is finished");
        return result;
    }

//======================================================================================================================

    private ArrayList <String> compare(ArrayList<String> etalon, ArrayList<String> current){
        log("Method is started");
        flag = false;

        if (etalon.containsAll(current) && counter < 1) {
            counter++;
            log(3, "nothing was changed, swipe try count = " + counter);
        }else if (counter >= 1 ){
                log(3, "the end of the list is reached");
                flag = true;
        } else {
            counter = 0;
            etalon.clear();
            etalon.addAll(current);
            flag = false;
        }
        log("Method is finished");
       return etalon;
    }


//======================================================================================================================
// Tap
//======================================================================================================================

    public void longTapButton(WebElement element, int timer) {
        log("Method is started");

        log(2, "long tap for " + timer + " seconds");
        timer = timer * 1000;
        driver.tap(1, element, timer);

        log("Method is finished");
    }

//======================================================================================================================
// GO
//======================================================================================================================

    public void goBack() {
        backBtn.click();
    }

    public void goNext() {
        nextButton.click();
    }

    public boolean goToTheRemotePage(){
        log("method is started");
        result = false;

        try {
            while (backBtn.isDisplayed()) {
                goBack();
            }
        }catch (NoSuchElementException e){
            log(3, "BackButton is not shown");
        }

        footerRemote.click();

        if (spaceControlImageOnRemotePage.isDisplayed()) {
            result = true;
        }
        log("Method is finished");
        return result;
    }

//======================================================================================================================
// CONFIRMATION
//======================================================================================================================
    public void confirmIt() {
        try {
            okBtn.click();
            log("OK button is pressed");
        }catch (Exception e){
            log(2, "OK Button was not found\n\n" + e.getMessage() + "\n");

            try {
                confirmButton.click();
                log("Confirm button is pressed");
            }catch (Exception e1){
                log(2, "Confirm button was not found\n\n" + e1.getMessage() + "\n");

                try {
                    addButton.click();
                    log("Confirm button is pressed");
                }catch (Exception e2){
                    log(2, "Add button was not found\n\n" + e2.getMessage() + "\n");
                }
            }
        }
    }

    public void cancelIt() {
        try {
            cancelBtn.click();
            log("Cancel button is pressed");
        }catch (Exception e){
            log(2, "first Cancel Button was not found\n\n" + e.getMessage() + "\n");

            try {
                cancelButton.click();
                log("Cancel button is pressed");
            }catch (Exception e1){
                log(2, "second Cancel Button was not found\n\n" + e.getMessage() + "\n");
            }
        }
    }

    public void cancelPopUpIfPresent() {
        cancelIt();
    }

}
