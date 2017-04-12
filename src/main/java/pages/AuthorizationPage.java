package pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

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

    private final Base base;
    private boolean result;

    public AuthorizationPage(Base base) {
        this.base = base;
        PageFactory.initElements(new AppiumFieldDecorator(base.getDriver()), this);
    }

//----------------------------------------------------------------------------------------------------------------------

    public void loginToTheServer(String login, String password) {
        Base.log("Method is started");

        Base.log(2, "fill the login field with data: [" + login + "]");
        loginField.sendKeys(login);

        Base.log(2, "fill the password field with data: [" + password + "]");
        passwordField.sendKeys(password);

        Base.log(2, "click login button");
        loginBtn.click();

        Base.log("Method is finished");
    }

    public void loginToTheServer(String login, String password, String server) {
        Base.log("Method is started");

        Base.log(2, "fill the login field with data: \"" + login + "\"");
        loginField.sendKeys(login);

        Base.log(2, "fill the password field with data: \"" + password + "\"");
        passwordField.sendKeys(password);

        chooseServer(server);

        Base.log(2, "click login button");
        loginBtn.click();

        Base.log("Method is finished");
    }


    public void loginWithPinCancel(String login, String password, String server) {
        Base.log("Method is started");

        Base.log("start from IntroPage");
        loginButtonOnIntro.click();

        loginToTheServer(login, password, server);

        Base.log("wait until LoaderLogo become invisible");
        base.wait.invisibilityOfLoaderLogo(true);

        Base.log("waiting for Pincode PopUp and cancel it");
        Assert.assertTrue(base.check.waitElementWithoutPin(base.dashboardHeader.getMenuDrawer(), 10), "Login failed!");

        Base.log("Method is finished");
    }


    public boolean loginWithPinCancel() {
        Base.log("Method is started");

        Base.log("get credentials for login");
        String login = base.getCredsWithKey("login");
        String password = base.getCredsWithKey("password");
        String server = base.getCredsWithKey("server");

        Base.log("start from IntroPage");
        loginButtonOnIntro.click();

        loginToTheServer(login, password, server);

        Base.log("wait until LoaderLogo become invisible");
        base.wait.invisibilityOfLoaderLogo(true);

        Base.log("waiting for Pincode PopUp and cancel it");
        if (base.check.waitElementWithoutPin(base.dashboardHeader.getMenuDrawer(), 10)){
            Base.log("Login successfully!");
            result = true;
        }else {
            Base.log("Login failed!");
            result = false;
        }
//        Assert.assertTrue(base.check.waitElementWithoutPin(base.dashboardHeader.getMenuDrawer(), 10), "Login failed!");

        Base.log("Method is finished");
        return result;
    }


    public void chooseServer(String server) {
        Base.log("Method is started");

        Base.log("select server type: \"" + server + "\"");
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
        Base.log("Method is finished");
    }

}
