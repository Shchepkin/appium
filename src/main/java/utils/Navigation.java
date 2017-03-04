package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;


public class Navigation {
    public final AppiumDriver driver;
    public boolean result;

    private long start, finish;
    private boolean flag;
    private int counter;
    private Setup s = new Setup();
    private ArrayList<String> etalon = new ArrayList<>();
    private ArrayList<String> current = new ArrayList<>();

    @AndroidFindBy(id = "com.ajaxsystems:id/back")
    public WebElement backBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/next")
    public WebElement nextBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/cancel")
    public WebElement cancelBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/ok")
    public WebElement okBtnd;

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





//======================================================================================================================
// Swipe
//======================================================================================================================
    public void swipeUp() {
        s.log("Method is started");

        Dimension screenSize = driver.manage().window().getSize();
        s.log("Screen dimension is " + screenSize);

        int startX = (int)(screenSize.width / 2.00);
        int startY = (int)(screenSize.height / 3.00);
        int endX = startX;
        int endY = (int)(screenSize.height / 20.00);
        int duration = 1500;

        s.log("swipe(startX, startY, endX, endY, duration) [" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + duration + "]");
        driver.swipe(startX, startY, endX, endY, duration);

        s.log("Method is finished");
    }

    public void swipeUp(int duration, double heightPart) {
        s.log("Method is started");

        Dimension screenSize = driver.manage().window().getSize();
        s.log("Screen dimension is " + screenSize);

        int startX = (int)(screenSize.width / 2.00);
        int startY = (int)(screenSize.height / heightPart);
        int endX = startX;
        int endY = (int)(screenSize.height / 20.00);

        s.log("swipe(startX, startY, endX, endY, duration) [" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + duration + "]");
        driver.swipe(startX, startY, endX, endY, duration);

        s.log("Method is finished");
    }

    public void swipeDown() {
        s.log("Method is started");

        Dimension screenSize = driver.manage().window().getSize();
        s.log("Screen dimension is " + screenSize);

        int startX = (int)(screenSize.width / 2.00);
        int startY = (int)(screenSize.height / 3.00);
        int endX = startX;
        int endY = (int)(screenSize.height / 1.3);
        int duration = 1500;

        s.log("swipe(startX, startY, endX, endY, duration) [" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + duration + "]");
        driver.swipe(startX, startY, endX, endY, duration);

        s.log("Method is finished");
    }

    public void swipeDown(int duration, double heightPart) {
        s.log("Method is started");

        Dimension screenSize = driver.manage().window().getSize();
        s.log("Screen dimension is " + screenSize);

        int startX = (int)(screenSize.width / 2.00);
        int startY = (int)(screenSize.height / heightPart);
        int endX = startX;
        int endY = (int)(screenSize.height / 1.3);

        s.log("swipe(startX, startY, endX, endY, duration) [" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + duration + "]");
        driver.swipe(startX, startY, endX, endY, duration);

        s.log("Method is finished");
    }
//======================================================================================================================
// Scroll
//======================================================================================================================

    /**
     * This method just scrolls current screen to the end with needed direction ("up" or "down")
     * @param direction "up"    - for scrolling to the end of screen
     *                  "down"  - for scrolling to the top of screen
     */
    public void scrollScreenToTheEnd(String direction){
        s.log("Method is started");
        start = System.nanoTime();
        flag = false;

        counter = 0;
        etalon.clear();
        current.clear();

        s.log("copy text objects from scrollList to etalon and current lists");
        for (WebElement i : scrollList) {
            etalon.add(i.getText());
            current.add(i.getText());
        }

        while (true){
            if (direction.equals("down")) {
                swipeDown(500, 2);
            }else swipeUp(500, 1.3);

            current.clear();
            s.log("put new text objects to the current list after swipe");
            try {
                for (WebElement i : scrollList) {
                  current.add(i.getText());
                }
            }catch (NoSuchElementException e){
                s.log(3, "NoSuchElementException, something was wrong!\n" + e);
            }

            compare(etalon, current);
            if (flag) break;
        }
        finish = System.nanoTime();
        s.log(2, "time " + String.format("%4.2f",(float)(finish - start)/1000000000) + " sec");
        s.log("Method is finished");
    }
//======================================================================================================================
    /**
     * This method scrolls current screen to the element with needed text and direction ("up" or "down") and gives you
     * the opportunity to click this element if you want
     * @param direction "up"    - for scrolling to the end of screen
     *                  "down"  - for scrolling to the top of screen
     * @param textOfSearchingElement - text which Searching Element contains
     * @param click - true - if you want to click the element
     *                false - if you don't need to click the element
     * @return true if method found the element (and click them if it required)
     */

    public boolean scrollToElementWithText(String direction, String textOfSearchingElement, boolean click) {
        start = System.nanoTime();
        s.log("Method is started");
        result = false;

        counter = 0;
        etalon.clear();
        current.clear();

        s.log("get all text objects from this screen");
        for (WebElement i : allTextObjects) {
            etalon.add(i.getText());
            current.add(i.getText());
        }

        while (true){

            if (current.contains(textOfSearchingElement)){
                try {
                    WebElement searchingElement = driver.findElement(By.xpath("//android.widget.TextView[@text='" + textOfSearchingElement + "']"));

                    if (click) {
                        searchingElement.click();
                        s.log("element with text \"" + textOfSearchingElement + "\" was found and clicked");
                    } else {
                        s.log("element with text \"" + textOfSearchingElement + "\" was found");
                    }
                    result = true;
                    break;
                }catch (NoSuchElementException e){
                    s.log(3, "NoSuchElementException, element is not found on this screen!");
                }
            }else s.log(3, "element is not found on this screen!");

            if (direction.equals("down")) {
                swipeDown(800, 2);
            }else swipeUp(800, 2);

            current.clear();
            s.log("put new text objects to the current list after swipe");
            try {
                for (WebElement i : allTextObjects) {
                    current.add(i.getText());
                }
            }catch (NoSuchElementException e){
                s.log(3, "NoSuchElementException, something was wrong!\n" + e);
            }

            compare(etalon, current);
            if (flag) break;
        }
        finish = System.nanoTime();
        s.log(2, "time " + String.format("%4.2f",(float)(finish - start)/1000000000) + " sec");
        s.log("Method is finished");
        return result;
    }

//======================================================================================================================

    private ArrayList <String> compare(ArrayList<String> etalon, ArrayList<String> current){
        if (etalon.containsAll(current) && counter < 2) {
            counter++;
            s.log(3, "nothing was changed, swipe try count = " + counter);
        }else if (counter >= 2 ){
                s.log(3, "the end of the list is reached");
                flag = true;
        } else {
            counter = 0;
            etalon.clear();
            etalon.addAll(current);
        }
       return etalon;
    }

    public List<WebElement> getScrollList() {
        return scrollList;
    }

//======================================================================================================================
// Tap
//======================================================================================================================

    public void longTapButton(WebElement element, int timer) {
        s.log("Method is started");
        s.log(2, "long tap for " + timer + " seconds");
        timer = timer * 1000;
        driver.tap(1, element, timer);
        s.log("Method is finished");
    }



    public Navigation(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}
