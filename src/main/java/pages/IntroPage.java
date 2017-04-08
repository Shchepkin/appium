package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

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
    public WebElement getBuild() {
        return build;
    }

//----------------------------------------------------------------------------------------------------------------------
    private final Base $;
    private final AppiumDriver driver;

    public IntroPage(Base base) {
        $ = base;
        this.driver = $.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
//----------------------------------------------------------------------------------------------------------------------

    public void goToAuthorization() {
        Base.log("Method is started");
        loginBtn.click();
    }

    public void goToRegistration() {
        Base.log("Method is started");
        registrationBtn.click();
    }

    public void setServer(String server) {
        Base.log("Method is started");
        loginBtn.click();
        $.loginPage.chooseServer(server);
        $.nav.goBack();
        Base.log("Method is finished");
    }
}
