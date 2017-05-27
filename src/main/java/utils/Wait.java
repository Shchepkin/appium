package utils;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.Base;

import java.util.concurrent.TimeUnit;

public class Wait {

//----------------------------------------------------------------------------------------------------------------------
    private Base base;
    private AndroidDriver driver;
    private String waiterText, pinPopUpText;
    private static final int PERIOD = 200;

    public Wait(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        waiterText = base.getLocalizeTextForKey("request_send");
        pinPopUpText = base.getLocalizeTextForKey("do_you_want_to_enable_passcode");

    }
//----------------------------------------------------------------------------------------------------------------------

    public boolean elementWithText(String searchingText, int timer, boolean makeScreenShot) {
        try {
            Base.log(1, "waiting " + timer + " seconds for the element with text \"" + searchingText + "\"");
            WebDriverWait iWait = new WebDriverWait(driver, timer, PERIOD);
            iWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//android.widget.TextView[@text='" + searchingText + "']"))));

            Base.log(1, "element is shown with text: \"" + searchingText + "\"");
            return true;

        } catch (NoSuchElementException e) {
            Base.log(2, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            if (makeScreenShot) {base.getScreenShot();}

        } catch (TimeoutException e) {
            Base.log(2, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            if (makeScreenShot) {base.getScreenShot();}
        }
        return false;
    }

    public boolean text(String searchingText, int timer, boolean makeScreenShot) {
        Base.log(2, "set implicitlyWait to " + timer + " seconds");
        driver.manage().timeouts().implicitlyWait(timer, TimeUnit.SECONDS);

        Base.log(1, "expected message: \"" + searchingText + "\"", true);
        try {
            Base.log(1, "waiting " + timer + " seconds for text \"" + searchingText + "\"");
            WebDriverWait iWait = new WebDriverWait(driver, timer, PERIOD);
            iWait.until(ExpectedConditions.visibilityOf(driver.findElementByAndroidUIAutomator("new UiSelector().text(\"" + searchingText + "\")")));
            Base.log(1, "expected text is shown", true);
            if (makeScreenShot) {base.getScreenShot();}
            return true;

        } catch (Exception e) {
            Base.log(1, "expected text is not shown", true);
            Base.log(3, "\n" + e + "\n");
            if (makeScreenShot) {base.getScreenShot();}
            return false;
        }finally {
            Base.log(2, "set implicitlyWait to default");
            driver.manage().timeouts().implicitlyWait(Base.DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        }
    }

    public boolean invisibilityOfWaiter() {
        Base.log(1, "waiting  Waiter become Invisible");
        WebDriverWait iWait = new WebDriverWait(driver, 70, PERIOD);
        try {
            iWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("com.ajaxsystems:id/progress")));
            Base.log(1, "Waiter become Invisible");
            base.getScreenShot();
            return true;

        } catch (TimeoutException e) {
            Base.log(4, "Timeout Exception, waiter is not disappear:\n\n" + e + "\n");
            base.getScreenShot();
            return false;
        }
    }

    public boolean visibilityOfSnackBarWithText(String expectedText, int timer) {
        try {
            Base.log(1, "waiting for SnackBar with get text");
            WebDriverWait iWait = new WebDriverWait(driver, timer, PERIOD);
            iWait.until(ExpectedConditions.visibilityOf(base.popUp.getSnackBarElement()));

            String actualSnackBarText = base.popUp.getSnackBarText();
            Base.log(1, "Actual SnackBar text: \"" + actualSnackBarText + "\"");
            Base.log(1, "Expected SnackBar text: \"" + expectedText + "\"");
            return actualSnackBarText.equalsIgnoreCase(expectedText);

        } catch (NoSuchElementException e) {
            Base.log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            base.getScreenShot();

        } catch (TimeoutException e) {
            Base.log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            base.getScreenShot();
        }
        return false;
    }

    public boolean invisibilityOfLoaderLogo(boolean makeScreenShot) {
        try {
            Base.log(1, "waiting 60 seconds while LoaderLogo become Invisible");
            WebDriverWait iWait = new WebDriverWait(driver, 60, PERIOD);
            iWait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("com.ajaxsystems:id/loaderLogo")));
            Base.log(1, "waiter is gone");
            return true;

        } catch (NoSuchElementException e) {
            Base.log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            if (makeScreenShot) {base.getScreenShot();}

        } catch (TimeoutException e) {
            Base.log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            if (makeScreenShot) {base.getScreenShot();}
        }
        return false;
    }

    public boolean menuIconOrPinPopUp(int timer) {
        try {
            Base.log(1, "waiting " + timer + " seconds for menuIcon Or Pin PopUp");
            WebDriverWait iWait = new WebDriverWait(driver, timer, PERIOD);
            iWait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOf(base.header.getMenuDrawer()),
                    ExpectedConditions.visibilityOf(base.nav.getCancelButton())
                    )
            );
            base.getScreenShot();
            Base.log(1, "Main Menu icon or PIN popUp is shown");
            return true;

        } catch (NoSuchElementException e) {
            Base.log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");

        } catch (TimeoutException e) {
            Base.log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
        }
        base.getScreenShot();
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
            WebDriverWait iWait = new WebDriverWait(driver, timer, PERIOD);
            iWait.until(ExpectedConditions.visibilityOf(elementForWaiting));

            try {
                Base.log(1, "element is shown with text: \"" + elementForWaiting.getText() + "\", element: " + elementForWaiting);
            } catch (Exception e) {
                Base.log(4, "element is shown successfully, but there is Exception while trying to get the text from the element:\n\n" + e + "\n");
            }
            return true;

        } catch (NoSuchElementException e) {
            Base.log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            if (makeScreenShot) {base.getScreenShot();}

        } catch (TimeoutException e) {
            Base.log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            if (makeScreenShot) {base.getScreenShot();}
        }
        return false;
    }

    public boolean pinPopUp(int timer, boolean confirm) {
        Base.log(1, "Wait PIN popUp");
        if (element(base.nav.getCancelButton(), timer, true)) {
            Base.log(1, "PIN popUp is displayed", true);
            if (confirm) {
                base.nav.confirmIt();
            } else {
                base.nav.cancelIt();
            }
            return true;
        }
        return false;
    }

    public boolean invisibilityOfElement(By by, int timer) {
        Base.log(1, "waiting " + timer + " seconds while element become Invisible");
        WebDriverWait iWait = new WebDriverWait(driver, timer, PERIOD);
        try {
            iWait.until(ExpectedConditions.invisibilityOfElementLocated(by));
            Base.log(1, "element become Invisible");
            base.getScreenShot();
            return true;

        } catch (TimeoutException e) {
            Base.log(4, "Timeout Exception, element is still displayed:\n\n" + e + "\n");
            base.getScreenShot();
            return false;
        }
    }

}
