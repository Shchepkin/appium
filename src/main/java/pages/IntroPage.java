package pages;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 *
 */
public class IntroPage extends Base{

    private final AppiumDriver driver;
    private AuthorizationPage loginPage;

    @AndroidFindBy(id = "com.ajaxsystems:id/login")
    private WebElement loginBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/registration")
    private WebElement registrationBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/build")
    private WebElement build;

    public IntroPage(AppiumDriver driver) {
        this.driver = driver;
        loginPage = new AuthorizationPage(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void goToAuthorization() {
        log("Method is started");
        loginBtn.click();
    }

    public void goToRegistration() {
        log("Method is started");
        registrationBtn.click();
    }

    public void setServer(String server) {
        log("Method is started");
        loginBtn.click();
        loginPage.chooseServer(server);
        nav.goBack();
        log("Method is finished");
    }

    public WebElement getLoginBtn() {
        return loginBtn;
    }

    public WebElement getRegistrationBtn() {
        return registrationBtn;
    }

    public WebElement getBuild() {
        return build;
    }

}
