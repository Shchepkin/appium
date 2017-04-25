package pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class AuthorizationPage{

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

//----------------------------------------------------------------------------------------------------------------------

    private Base base;
    private boolean result;

    public AuthorizationPage(Base base) {
        this.base = base;
        PageFactory.initElements(new AppiumFieldDecorator(base.getDriver(), Base.TIMEOUT, TimeUnit.SECONDS), this);
    }

//----------------------------------------------------------------------------------------------------------------------

    public void loginToTheServer(String login, String password) {
        Base.log(4, "Method is started");

        Base.log(1, "fill the login field with data: \"" + login + "\"");
        loginField.sendKeys(login);

        Base.log(1, "fill the password field with data: \"" + password + "\"");
        passwordField.sendKeys(password);

        Base.log(1, "click login button");
        loginBtn.click();

        Base.log(4, "Method is finished");
    }

    public void loginToTheServer(String login, String password, String server) {
        Base.log(4, "Method is started");

        Base.log(1, "fill the login field with data: \"" + login + "\"", true);
        loginField.sendKeys(login);

        Base.log(1, "fill the password field with data: \"" + password + "\"");
        passwordField.sendKeys(password);

        chooseServer(server);

        Base.log(1, "click login button");
        loginBtn.click();

        Base.log(4, "Method is finished");
    }


    public boolean loginWithPinCancel(String login, String password, String server) {
        Base.log(4, "Method is started");

        Base.log(4, "start from IntroPage");
        loginButtonOnIntro.click();

        loginToTheServer(login, password, server);

        Base.log(1, "wait until LoaderLogo become invisible");
        base.wait.invisibilityOfLoaderLogo(true);

        Base.log(4, "waiting for Pincode PopUp and cancel it");
        if (base.check.waitElementWithoutPin(base.dashboardHeader.getMenuDrawer(), 10)){
           return true;
        }else return false;
     }


    public void loginWithPinCancel() {
        Base.log(4, "Method is started");

        Base.log(1, "get credentials for login");
        String login = base.getCredsWithKey("login");
        String password = base.getCredsWithKey("password");
        String server = base.getCredsWithKey("server");

        Base.log(1, "tap Login Button");
        loginButtonOnIntro.click();

        loginToTheServer(login, password, server);

        if (base.wait.menuIconOrPinPopUp(100, true)){
            Base.log(1, "Login successfully!");
        }

        Base.log(1, "check is there popUp present");
        for (int i = 0; i < 2; i++) {

            if (base.wait.element(base.nav.getCancelButton(), 2, true)) {
                Base.log(1, "Pincode PopUp is shown - cancel it!");
                base.nav.cancelIt();
                break;
            } else {
                Base.log(1, "popUp is not present");
                base.nav.gotoPage.Rooms();
                base.nav.gotoPage.Devices();

            }
        }

        Base.log(4, "Method is finished");
    }


    public void chooseServer(String server) {
        Base.log(1, "select server type: \"" + server + "\"");
        base.nav.longTapButton(loginBtn, 2);

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
    }

    public void logOut() {
        Base.log(4, "Method is started");

        Base.log(4, "tap menu icon");
        base.dashboardHeader.getMenuDrawer().click();

        Base.log(4, "tap Account Button");
        base.menuPage.getAccountButton().click();

        Base.log(4, "tap Logout Button");
        base.accountPage.getLogoutBtn().click();

        Base.log(4, "wait Login Button on Intro page");
        base.wait.element(base.introPage.getLoginBtn(), 15, true);
    }

}
