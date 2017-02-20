package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardActivePINPage {
    public final AppiumDriver driver;

    @AndroidFindBy(id = "com.ajaxsystems:id/content_text")
    public WebElement contentText;

    @AndroidFindBy(id = "com.ajaxsystems:id/cancel_button")
    public WebElement cancelBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/confirm_button")
    public WebElement confirmBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/description")
    public WebElement description;

    @AndroidFindBy(id = "com.ajaxsystems:id/pin1")
    public WebElement pin1;

    @AndroidFindBy(id = "com.ajaxsystems:id/pin2")
    public WebElement pin2;

    @AndroidFindBy(id = "com.ajaxsystems:id/pin3")
    public WebElement pin3;

    @AndroidFindBy(id = "com.ajaxsystems:id/pin4")
    public WebElement pin4;

    @AndroidFindBy(id = "com.ajaxsystems:id/one")
    public WebElement one;

    @AndroidFindBy(id = "com.ajaxsystems:id/two")
    public WebElement two;

    @AndroidFindBy(id = "com.ajaxsystems:id/three")
    public WebElement three;

    @AndroidFindBy(id = "com.ajaxsystems:id/four")
    public WebElement four;

    @AndroidFindBy(id = "com.ajaxsystems:id/five")
    public WebElement five;

    @AndroidFindBy(id = "com.ajaxsystems:id/sixth")
    public WebElement six;

    @AndroidFindBy(id = "com.ajaxsystems:id/seven")
    public WebElement seven;

    @AndroidFindBy(id = "com.ajaxsystems:id/eight")
    public WebElement eight;

    @AndroidFindBy(id = "com.ajaxsystems:id/nine")
    public WebElement nine;

    @AndroidFindBy(id = "com.ajaxsystems:id/zero")
    public WebElement zero;

    @AndroidFindBy(id = "com.ajaxsystems:id/backspace")
    public WebElement backspace;

    @AndroidFindBy(id = "com.ajaxsystems:id/fingerprint")
    public WebElement fingerprint;



    public void closePinWindowIfDisplayed() {
        try {
            // assert is the element displayed on the page
            WebDriverWait iWait = new WebDriverWait (driver, 30);
            iWait.until(ExpectedConditions.visibilityOf(cancelBtn));
            cancelBtn.click();
            System.out.println("Window with PIN acceptation is displayed and canceled.");

        } catch (NoSuchElementException e) {
            System.out.println("Window with PIN acceptation isn't displayed.");
        }
    }

    public void openPinWindowIfDisplayed() {
        try {
            // assert is the element displayed on the page
            WebDriverWait iWait = new WebDriverWait (driver, 30);
            iWait.until(ExpectedConditions.visibilityOf(confirmBtn));
            confirmBtn.click();
            System.out.println("Window with PIN acceptation is displayed and confirmed.");

        } catch (NoSuchElementException e) {
            System.out.println("Window with PIN acceptation isn't displayed.");
        }
    }

    public DashboardActivePINPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}
