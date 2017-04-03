package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.Check;
import utils.Navigation;


public class AuthorizationPage extends Base{

    private AppiumDriver driver;
    private Navigation nav;
    private Check check;

    @AndroidFindBy(id = "com.ajaxsystems:id/back")
    private WebElement backBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/login")
    public WebElement loginField;

    @AndroidFindBy(id = "com.ajaxsystems:id/password")
    public WebElement passwordField;

    @AndroidFindBy(id = "com.ajaxsystems:id/next")
    public WebElement loginBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/forgot")
    public WebElement forgotPasswordBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/snackbar_text")
    public WebElement snackBar;

    @AndroidFindBy(id = "com.ajaxsystems:id/message")
    public WebElement toast;

    @AndroidFindBy(id = "com.ajaxsystems:id/dialogMessage")
    public WebElement dialogMessage;


// Server menu
// version >= 2.8
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

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.ajaxsystems:id/textView\").text(\"Glimmering_dev\")")
    public WebElement serverGlim;



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

    public AuthorizationPage(AppiumDriver driver) {
        this.driver = driver;
        this.nav = new Navigation(driver);
        this.check = new Check(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }


    public void loginToTheServer(String login, String password) {
        log("Method is started");

        log(2, "fill the login field with data: [" + login + "]");
        loginField.sendKeys(login);

        log(2, "fill the password field with data: [" + password + "]");
        passwordField.sendKeys(password);

        log(2, "click login button");
        loginBtn.click();

        log("Method is finished");
    }

    public void loginToTheServer(String login, String password, String server) {
        log("Method is started");

        log(2, "fill the login field with data: \"" + login + "\"");
        loginField.sendKeys(login);

        log(2, "fill the password field with data: \"" + password + "\"");
        passwordField.sendKeys(password);

        chooseServer(server);

        log(2, "click login button");
        loginBtn.click();

        log("Method is finished");
    }


    public void chooseServer(String server) {
        log("Method is started");

        log("select server type: \"" + server + "\"");
        nav.longTapButton(loginBtn, 2);

        switch (server) {
            case "Debug":  serverDebug.click();
                break;
            case "Develop":  serverDevelop.click();
                break;
            case "Production": serverProduction.click();
                break;
            case "Amazon":  serverAmazon.click();
                break;
            case "Eden":  serverEden.click();
                break;
            case "Glim":  serverGlim.click();
                break;
            default: serverProduction.click();
                break;
        }
        log("Method is finished");
    }

}
