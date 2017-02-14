package utils;

import org.openqa.selenium.TimeoutException;
import pages.DashboardActivePINPage;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;


public class Check {
    private Setup s = new Setup();
    private AndroidDriver driver;
    private ScreenShot screenShot;
    public boolean result;
    int element;
//    private String pathOfScreenshot;

    public Check(AndroidDriver driver) {
        this.driver = driver;
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
            s.log(2, "element is shown with text: \"" + element.getText() + "\"");
//            System.out.println("Element is displayed. " + element);

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

    public boolean waitElement(WebElement element, int timer, boolean makeScreenShot) {
        s.log("Method is started");

        try {
            s.log(2, "waiting " + timer + " seconds for the element ");
            WebDriverWait iWait = new WebDriverWait(driver, timer);
            iWait.until(ExpectedConditions.visibilityOf(element));
            s.log(2, "element " + element + " is shown with text: \"" + element.getText() + "\"");
            result = true;
        } catch (NoSuchElementException e) {
            result = false;
            if (makeScreenShot){
                try {
                    screenShot.getScreenShot();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            s.log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
        } catch (TimeoutException e) {
            result = false;
            if (makeScreenShot){
                try {
                    screenShot.getScreenShot();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            s.log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
        }
        return result;
    }

    /**
         * @param elements - list of locators for checking
         * @param period   - how many iterations of checking we have to make, each iterations approximately 1.2 sec
         * @return         - number of element which was shown (if there is no elements it returns 0)

      example:
        waitElements(new WebElement[]{element1, element2, element3}, 5)
      or
        WebElement elements = new WebElement[]{element1, element2, element3};
        waitElements(elements, 5));
     */
    public int waitElements (WebElement[] elements, int period) {
        s.log("Method is started");
        result = false;
        for (int i = 1; i <= period; i++) {
            int counter = 1;
            s.log(2, "start waiting period #" + i);

            for (WebElement el : elements) {
                try {
                    WebDriverWait iWait = new WebDriverWait(driver, 0);
                    iWait.until(ExpectedConditions.visibilityOf(el));
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
        return element;
    }

}
