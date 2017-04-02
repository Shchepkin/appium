package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by installer on 1/21/17.
 */
public class MenuMainPage extends Base{
    public final AppiumDriver driver;

    @AndroidFindBy(id = "com.ajaxsystems:id/menuTitle")
    public WebElement menuTitle;

    @AndroidFindBy(id = "com.ajaxsystems:id/account")
    public WebElement accountBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/addHub")
    public WebElement addHubBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/logs")
    public WebElement logsBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/settings")
    public WebElement settingsBtn;

    public MenuMainPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}
