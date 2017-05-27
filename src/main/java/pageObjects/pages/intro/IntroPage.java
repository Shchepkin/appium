package pageObjects.pages.intro;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pageObjects.Base;

import java.util.concurrent.TimeUnit;

public class IntroPage{

    @AndroidFindBy(id = "com.ajaxsystems:id/login")
    private WebElement loginBtn;
    public WebElement getLoginBtn() {
        return loginBtn;
    }

    @AndroidFindBy(id = "com.ajaxsystems:id/registration")
    private WebElement registrationBtn;
    public WebElement getRegistrationBtn() {
        return registrationBtn;
    }

    @AndroidFindBy(id = "com.ajaxsystems:id/build")
    private WebElement build;

//----------------------------------------------------------------------------------------------------------------------
    private final Base base;
    private final AndroidDriver driver;

    public IntroPage(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.DEFAULT_TIMEOUT, TimeUnit.SECONDS), this);
    }
//----------------------------------------------------------------------------------------------------------------------

    public void goToAuthorization() {
        Base.log(4, "tap Login button");
        loginBtn.click();
    }
    public void clickAuthorizationButton() {
        Base.log(1, "tap Login button");
        loginBtn.click();
    }
    public void clickRegistrationButton() {
        Base.log(1, "tap Registration button");
        registrationBtn.click();
    }

    public void setServer(String server) {
        base.wait.element(loginBtn, 5, true);
        clickAuthorizationButton();
        base.loginPage.chooseServer(server);
        base.nav.tapButton.back();
    }
}
