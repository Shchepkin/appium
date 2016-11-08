package ajaxMobileApp;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import io.appium.java_client.pagefactory.*;

/**
 * Created by installer on 11/5/16.
 */
public class IntroScreen{

    public final AppiumDriver driver;

    @AndroidFindBy(id = "com.ajaxsystems:id/login")
    public WebElement authorizationBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/registration")
    public WebElement registrationBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/build")
    public WebElement build;

    @AndroidFindBy(id = "com.ajaxsystems:id/logo")
    public WebElement logo;

    public IntroScreen(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void goToAuthorization() {
        authorizationBtn.click();
    }
}
