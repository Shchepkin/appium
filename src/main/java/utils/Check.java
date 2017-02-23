package utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;


public class Check {
    private AppiumDriver driver;
    private Setup s = new Setup();
    private ScreenShot screenShot;
    private PopUp popUp;
    private int element = 0;

    public boolean result;

    public Check(AppiumDriver driver) {
        this.driver = driver;
        this.popUp = new PopUp(driver);
        this.screenShot = new ScreenShot(driver);
    }

    public void isElementDisplayed(WebElement element) {
        s.log("Method is started");
        // create ScreenShot object for making screenshots
//        screenShot = new ScreenShot(driver);

        try {
            // assert is the element displayed on the page
            WebDriverWait iWait = new WebDriverWait(driver, 15);
            iWait.until(ExpectedConditions.visibilityOf(element));
            element.isDisplayed();
            s.log("element is shown with text: \"" + element.getText() + "\"");

        } catch (NoSuchElementException e) {

            // is failed - make screenshot
            try {
                screenShot.getScreenShot();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            // creation report
            Assert.fail("Test failed - no such element was appeared during 3 min\n" + e);
        }
    }

    /**
        * @param element           - element which we want to wait
        * @param timer             - how long time we want to wait for the element (in seconds)
        * @param makeScreenShot    - make screenshot if element is not found (true)
        * @return                  - result true or false about successfully execute this method

         example:
            waitElement(element, 5, true)
     */
    public boolean waitElement(WebElement element, int timer, boolean makeScreenShot) {
        s.log("Method is started");

        try {
            s.log(2, "waiting " + timer + " seconds for the element ");
            WebDriverWait iWait = new WebDriverWait(driver, timer);
            iWait.until(ExpectedConditions.visibilityOf(element));
            s.log(2, "element " + element + " is shown with text: \"" + element.getText() + "\"");
            result = true;
        } catch (NoSuchElementException e) {
            s.log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                try {
                    screenShot.getScreenShot();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } catch (TimeoutException e) {
            s.log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){
                try {
                    screenShot.getScreenShot();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
         * @param elements - selectList of locators for checking
         * @param period   - how many iterations of checking we have to make, each iterations approximately 1.3 sec
         * @return         - number of element which was shown (if there is no elements it returns 0)

      example:
        waitElements(new WebElement[]{element1, element2, element3}, 5)
      or
        WebElement[] elements = new WebElement[]{element1, element2, element3};
        waitElements(elements, 5));
     */
    public int waitElements (WebElement[] elements, int period) {
        s.log("Method is started");
        element = 0;
        result = false;
        for (int i = 1; i <= period; i++) {
            int counter = 1;
            s.log(2, "start waiting period #" + i);

            for (WebElement el : elements) {
                try {
                    s.log(2, "element " + el + " is shown with text: \"" + el.getText() + "\"");
                    result = true;
                    element = counter;
                    break;
                } catch (NoSuchElementException e) {
                    s.log(3, "NoSuchElementException, element " + counter + " is not shown");
                } catch (TimeoutException e) {
                    s.log(3, "Timeout Exception, element " + counter + " is not shown");
                }
                counter ++;
            }
            if (result) {break;}
        }
        s.log("Method is finished");
        return element;
    }

    public boolean clickElementAndWaitingPopup(WebElement elementForClick, int period){
        s.log("Method is started");
        result = false;
        WebElement[] elements = new WebElement[]{popUp.snackBar, popUp.loadingWin, popUp.errorPic};

        for (int i = 1; i < 5; i++) {
            s.log(3, "click the element link, attempt #" +i);
            elementForClick.click();

            s.log("waiting for: 1.snackBar  2.error  3.loadingWin");
            element = waitElements(elements, period);
            switch (element){
                case 0: s.log(3, "no PopUp is shown or this moment is missed"); break;
                case 1: s.log(3, "snackBar is shown, the text was previously displayed"); break;
                case 2:
                    s.log(3, "loader is shown with text: \"" + popUp.contentText.getText() + "\", so wait for error message");
                    if (!waitElement(popUp.errorPic, 10, true)) {s.log("loader is shown, but without error"); break;}
                case 3: Assert.fail(popUp.contentText.getText()); break;
                default: break;
            }
            if (!waitElement(elementForClick, 5, true)) {
                s.log(3, "element for click is not shown now, maybe click was successfully!");
                result = true;
                break;
            }
            s.log(3, "previous click element is failed and it's shown again, so click it one more time");
        }
        return result;
    }

}