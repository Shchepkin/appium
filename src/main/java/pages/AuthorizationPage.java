package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.Check;
import utils.Navigation;
import utils.Wait;


public class AuthorizationPage extends Base{

    private AppiumDriver driver;

    @AndroidFindBy(id = "com.ajaxsystems:id/login")
    private WebElement loginField;

    @AndroidFindBy(id = "com.ajaxsystems:id/password")
    private WebElement passwordField;

    @AndroidFindBy(id = "com.ajaxsystems:id/login")
    private WebElement loginButtonOnIntro;

    @AndroidFindBy(id = "com.ajaxsystems:id/next")
    private WebElement loginBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/forgot")
    private WebElement forgotPasswordBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/dialogMessage")
    private WebElement dialogMessage;

//---------------------------------------------------------------------------------------------------------------------

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.ajaxsystems:id/textView\").text(\"Debug\")")
    private WebElement serverDebug;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.ajaxsystems:id/textView\").text(\"Develop\")")
    private WebElement serverDevelop;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.ajaxsystems:id/textView\").text(\"Production\")")
    private WebElement serverProduction;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.ajaxsystems:id/textView\").text(\"Amazon\")")
    private WebElement serverAmazon;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.ajaxsystems:id/textView\").text(\"Eden\")")
    private WebElement serverEden;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.ajaxsystems:id/textView\").text(\"Glimmering_dev\")")
    private WebElement serverGlim;



    public AuthorizationPage(AppiumDriver driver) {
        this.driver = driver;
        this.nav = new Navigation(driver);
        this.wait = new Wait(driver);
        this.check = new Check(driver);
        this.dashboardHeader = new DashboardHeader(driver);
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


    public void loginWithPinCancel(String login, String password, String server) {
        log("Method is started");

        log("start from IntroPage");
        loginButtonOnIntro.click();

        loginToTheServer(login, password, server);

        log("wait until LoaderLogo become invisible");
        wait.invisibilityOfLoaderLogo(true);

        log("waiting for Pincode PopUp and cancel it");
        Assert.assertTrue(check.waitElementWithoutPin(dashboardHeader.getMenuDrawer(), 10), "Login failed!");

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
