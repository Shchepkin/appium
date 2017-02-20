package pages;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import io.appium.java_client.pagefactory.*;
import org.testng.Reporter;
import utils.Navigation;
import utils.Setup;

/**
 *
 */
public class IntroPage {

    public final AppiumDriver driver;
    private AuthorizationPage authorizationPage;
    private Setup s = new Setup();

    @AndroidFindBy(id = "com.ajaxsystems:id/login")
    public WebElement loginBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/registration")
    public WebElement registrationBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/build")
    public WebElement build;

    public IntroPage(AppiumDriver driver) {
        this.driver = driver;
        authorizationPage = new AuthorizationPage(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void goToAuthorization() {
        s.log("Method is started");
        loginBtn.click();
    }

    public void goToRegistration() {
        s.log("Method is started");
        registrationBtn.click();
    }

    public void setServer(String server) {
        s.log("Method is started");
        loginBtn.click();
        authorizationPage.chooseServer(server);
        authorizationPage.backBtn.click();
        s.log("Method is finished");
    }

}
