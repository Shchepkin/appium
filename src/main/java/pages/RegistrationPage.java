package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.Navigation;
import utils.Setup;

import java.util.List;

public class RegistrationPage {

    public final AppiumDriver driver;
    private IntroPage introPage;
    private AuthorizationPage authorizationPage;
    private Navigation nav;
    private Setup s = new Setup();

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

    @AndroidFindBy(id = "com.ajaxsystems:id/dashboard")
    public WebElement dashboard;

    public void fakeRegistration(String email, String password, String phone, String server) {
        s.log("Method is started");
        introPage = new IntroPage(driver);
        nav = new Navigation(driver);
        authorizationPage = new AuthorizationPage(driver);
        introPage.loginBtn.click();
        nav.longTapButton(authorizationPage.loginBtn, 2);
        authorizationPage.serverDevelop.click();
        nav.backBtn.click();
        introPage.goToRegistration();
        nameField.sendKeys("fakeRegistration");
        emailField.sendKeys(email);
        emailConfirmField.sendKeys(email);
        phoneField.sendKeys(phone);
        nav.swipeUp();
        passwordField.sendKeys(password);
        nav.swipeUp();
        passwordConfirmField.sendKeys(password);
        registrationBtn.click();
    }

    public RegistrationPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}
