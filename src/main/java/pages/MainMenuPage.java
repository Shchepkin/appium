package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class MainMenuPage{

    @AndroidFindBy(id = "com.ajaxsystems:id/menuTitle")
    private WebElement menuTitle;

    @AndroidFindBy(id = "com.ajaxsystems:id/account")
    private WebElement accountButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/addHub")
    private WebElement addHubButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/logs")
    private WebElement logsButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/settings")
    private WebElement settingsButton;


//----------------------------------------------------------------------------------------------------------------------
    private final Base $;
    private final AppiumDriver driver;

    public MainMenuPage(Base base) {
        $ = base;
        this.driver = $.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
//----------------------------------------------------------------------------------------------------------------------

}
