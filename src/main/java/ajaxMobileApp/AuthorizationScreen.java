package ajaxMobileApp;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by installer on 11/5/16.
 */
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

    @AndroidFindBy(id = "com.ajaxsystems:id/s1")
    public WebElement serverDebug;

    @AndroidFindBy(id = "com.ajaxsystems:id/s2")
    public WebElement serverDevelop;

    @AndroidFindBy(id = "com.ajaxsystems:id/s3")
    public WebElement serverProduction;

    @AndroidFindBy(id = "com.ajaxsystems:id/s4")
    public WebElement serverNewProduction;

    @AndroidFindBy(id = "com.ajaxsystems:id/s5")
    public WebElement serverEden;

    public AuthorizationScreen(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }


    public void loginAction(String login, String password) {
        loginField.sendKeys(login);
        passwordField.sendKeys(password);
        loginBtn.click();
    }

    public void loginDebugServer(String login, String password) {
        loginField.sendKeys(login);
        passwordField.sendKeys(password);
        longTapLoginButton();
        serverDebug.click();
    }

    public void loginDevelopServer(String login, String password) {
        loginField.sendKeys(login);
        passwordField.sendKeys(password);
        longTapLoginButton();
        serverDevelop.click();
    }

    public void longTapLoginButton() {
        driver.tap(1, loginBtn, 3000);
    }
}
