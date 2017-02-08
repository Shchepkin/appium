package pages;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import io.appium.java_client.pagefactory.*;
import org.testng.Reporter;

/**
 *
 */
public class IntroPage {

    public final AppiumDriver driver;

    @AndroidFindBy(id = "com.ajaxsystems:id/login")
    public WebElement authorizationBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/registration")
    public WebElement registrationBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/build1")
    public WebElement build;

    public IntroPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void goToAuthorization() {
        Reporter.log("> goToAuthorization ", true);
        authorizationBtn.click();
    }

    public void goToRegistration() {

        Reporter.log("> goToRegistration ", true);
        registrationBtn.click();
    }
}
