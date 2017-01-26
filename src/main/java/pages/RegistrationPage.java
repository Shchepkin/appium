package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class RegistrationPage {

    public final AppiumDriver driver;

    @AndroidFindBy(id = "com.ajaxsystems:id/back")
    public WebElement backBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/photo")
    public WebElement userPic;

    @AndroidFindBy(id = "com.ajaxsystems:id/name")
    public WebElement nameField;

    @AndroidFindBy(id = "com.ajaxsystems:id/login")
    public WebElement emailField;

    @AndroidFindBy(id = "com.ajaxsystems:id/loginConfirm")
    public WebElement emailConfirmField;

    @AndroidFindBy(id = "com.ajaxsystems:id/code")
    public WebElement phoneCountryCode;

    @AndroidFindBy(id = "com.ajaxsystems:id/text")
    public List<WebElement> phoneCountryCodeList;

    @AndroidFindBy(id = "com.ajaxsystems:id/phone")
    public AndroidElement phoneField;

    @AndroidFindBy(id = "com.ajaxsystems:id/password")
    public WebElement passwordField;

    @AndroidFindBy(id = "com.ajaxsystems:id/passwordConfirm")
    public WebElement passwordConfirmField;

    @AndroidFindBy(id = "com.ajaxsystems:id/next")
    public WebElement registrationBtn;

    public void swipeUp() {
        Dimension screenSize = driver.manage().window().getSize();
        System.out.println("   Screen dimension is " + screenSize);
        int startX = (int)(screenSize.width / 2.00);
        int startY = (int)(screenSize.height / 3.00);
        int endX = startX;
        int endY = (int)(screenSize.height / 10.00);
        int duration = 1500;
        System.out.print("   swipe(startX, startY, endX, endY, duration) {" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + duration + "} ... ");
        driver.swipe(startX, startY, endX, endY, duration);
        System.out.println("Done");
    }

    public void swipeDown() {
        Dimension screenSize = driver.manage().window().getSize();
        System.out.println("   Screen dimension is " + screenSize);
        int startX = (int)(screenSize.width / 2.00);
        int startY = (int)(screenSize.height / 3.00);
        int endX = startX;
        int endY = (int)(screenSize.height / 1.5);
        int duration = 1500;
        System.out.print("   swipe(startX, startY, endX, endY, duration) {" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + duration + "} ... ");
        driver.swipe(startX, startY, endX, endY, duration);
        System.out.println("Done");
    }

    public RegistrationPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}
