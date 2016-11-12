package ajaxMobileApp;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Parameters;


public class AuthorizationScreen {

    public final AppiumDriver driver;

    @AndroidFindBy(id = "com.ajaxsystems:id/back")
    public WebElement backBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/login")
    public WebElement loginField;

    @AndroidFindBy(id = "com.ajaxsystems:id/password")
    public WebElement passwordField;

    @AndroidFindBy(id = "com.ajaxsystems:id/next")
    public WebElement loginBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/forgot")
    public WebElement forgotPasswordBtn;


// Server menu
// version 2.8
//---------------------------------------------------------------------------------------------------------------------

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.ajaxsystems:id/textView\").text(\"Debug\")")
    public WebElement serverDebug;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.ajaxsystems:id/textView\").text(\"Develop\")")
    public WebElement serverDevelop;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.ajaxsystems:id/textView\").text(\"Production\")")
    public WebElement serverProduction;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.ajaxsystems:id/textView\").text(\"Amazon\")")
    public WebElement serverAmazon;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.ajaxsystems:id/textView\").text(\"Eden\")")
    public WebElement serverEden;

//---------------------------------------------------------------------------------------------------------------------

// version < 2.8
//---------------------------------------------------------------------------------------------------------------------
/*
    @AndroidFindBy(id = "com.ajaxsystems:id/s1")
    public WebElement serverDebug;

    @AndroidFindBy(id = "com.ajaxsystems:id/s2")
    public WebElement serverDevelop;

    @AndroidFindBy(id = "com.ajaxsystems:id/s3")
    public WebElement serverProduction;

    @AndroidFindBy(id = "com.ajaxsystems:id/s4")
    public WebElement serverAmazon;

    @AndroidFindBy(id = "com.ajaxsystems:id/s5")
    public WebElement serverEden;
*/
//---------------------------------------------------------------------------------------------------------------------

    public AuthorizationScreen(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }


    public void loginToDefaultServer(String login, String password) {
        loginField.sendKeys(login);
        passwordField.sendKeys(password);
        loginBtn.click();
    }

    public void loginToDebugServer(String login, String password) {
        loginField.sendKeys(login);
        passwordField.sendKeys(password);
        longTapLoginButton();
        serverDebug.click();
    }

    public void loginToDevelopServer(String login, String password) {
        loginField.sendKeys(login);
        passwordField.sendKeys(password);
        longTapLoginButton();
        serverDevelop.click();
    }

    public void loginToProductionServer(String login, String password) {
        loginField.sendKeys(login);
        passwordField.sendKeys(password);
        longTapLoginButton();
        serverProduction.click();
    }

    public void loginToAmazonServer(String login, String password) {
        loginField.sendKeys(login);
        passwordField.sendKeys(password);
        longTapLoginButton();
        serverAmazon.click();
    }

    public void loginToEdenServer(String login, String password) {
        loginField.sendKeys(login);
        passwordField.sendKeys(password);
        longTapLoginButton();
        serverEden.click();
    }

    public void longTapLoginButton() {
        driver.tap(1, loginBtn, 3000);
    }
}
