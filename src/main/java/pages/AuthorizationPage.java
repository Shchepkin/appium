package pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

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

        Base.log(1, "fill the password field with data: \"" + password + "\"", true);
        passwordField.sendKeys(password);

        chooseServer(server);

        Base.log(1, "click login button");
        loginBtn.click();

        Base.log(4, "Method is finished");
    }


    public boolean loginWithPinCancel(String login, String password, String server) {
        Base.log(4, "Method is started");

        Base.log(1, "tap Login Button");
        loginButtonOnIntro.click();

        loginToTheServer(login, password, server);

        Base.log(1, "wait menu Icon Or Pin PopUp");
        if (base.wait.menuIconOrPinPopUp(100, true)){

            Base.log(1, "check is Pincode PopUp displayed");
            if(base.wait.element(base.popUp.getContentTextElement(), 1, true)){
                Base.log(1, "Pincode PopUp displayed");
                base.nav.cancelIt();
            }
            Base.log(1, "Login successfully!");
            return true;
        }else {
            return false;
        }
     }


    public void loginWithPinCancel() {
        Base.log(1, "get credentials for login");
        String login = base.getCredsWithKey("login");
        String password = base.getCredsWithKey("password");
        String server = base.getCredsWithKey("server");

        loginWithPinCancel(login, password, server);
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
