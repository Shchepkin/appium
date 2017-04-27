package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class PinPage {

    @AndroidFindBy(id = "com.ajaxsystems:id/content_text")
    private WebElement contentText;

    @AndroidFindBy(id = "com.ajaxsystems:id/cancel_button")
    private WebElement cancelBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/confirm_button")
    private WebElement confirmBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/description")
    private WebElement description;

    @AndroidFindBy(id = "com.ajaxsystems:id/pin1")
    private WebElement pin1;

    @AndroidFindBy(id = "com.ajaxsystems:id/pin2")
    private WebElement pin2;

    @AndroidFindBy(id = "com.ajaxsystems:id/pin3")
    private WebElement pin3;

    @AndroidFindBy(id = "com.ajaxsystems:id/pin4")
    private WebElement pin4;

    @AndroidFindBy(id = "com.ajaxsystems:id/one")
    private WebElement one;

    @AndroidFindBy(id = "com.ajaxsystems:id/two")
    private WebElement two;

    @AndroidFindBy(id = "com.ajaxsystems:id/three")
    private WebElement three;

    @AndroidFindBy(id = "com.ajaxsystems:id/four")
    private WebElement four;

    @AndroidFindBy(id = "com.ajaxsystems:id/five")
    private WebElement five;

    @AndroidFindBy(id = "com.ajaxsystems:id/sixth")
    private WebElement six;

    @AndroidFindBy(id = "com.ajaxsystems:id/seven")
    private WebElement seven;

    @AndroidFindBy(id = "com.ajaxsystems:id/eight")
    private WebElement eight;

    @AndroidFindBy(id = "com.ajaxsystems:id/nine")
    private WebElement nine;

    @AndroidFindBy(id = "com.ajaxsystems:id/zero")
    private WebElement zero;

    @AndroidFindBy(id = "com.ajaxsystems:id/backspace")
    private WebElement backspace;

    @AndroidFindBy(id = "com.ajaxsystems:id/fingerprint")
    private WebElement fingerprint;
    public WebElement getFingerprint() {
        return fingerprint;
    }

//----------------------------------------------------------------------------------------------------------------------
    private final Base base;
    private final AppiumDriver driver;

    public PinPage(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.TIMEOUT, TimeUnit.SECONDS), this);
    }
//----------------------------------------------------------------------------------------------------------------------

    public void closePinWindowIfDisplayed() {
        try {
            // check is the element displayed on the page
            WebDriverWait iWait = new WebDriverWait (driver, 30);
            iWait.until(ExpectedConditions.visibilityOf(cancelBtn));
            cancelBtn.click();
            Base.log(1, "window with PIN acceptation is displayed and canceled");

        } catch (NoSuchElementException e) {
            Base.log(1, "window with PIN acceptation isn't displayed");
        }
    }

    public void openPinWindowIfDisplayed() {
        try {
            // check is the element displayed on the page
            WebDriverWait iWait = new WebDriverWait (driver, 30);
            iWait.until(ExpectedConditions.visibilityOf(confirmBtn));
            confirmBtn.click();
            Base.log(1, "Window with PIN acceptation is displayed and confirmed.");

        } catch (NoSuchElementException e) {
            Base.log(1, "Window with PIN acceptation isn't displayed.");
        }
    }

}
