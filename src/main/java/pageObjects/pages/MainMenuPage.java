package pageObjects.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pageObjects.Base;

import java.util.concurrent.TimeUnit;

public class MainMenuPage {

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
    private final Base base;
    private final AndroidDriver driver;

    public MainMenuPage(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.TIMEOUT, TimeUnit.SECONDS), this);
    }

//----------------------------------------------------------------------------------------------------------------------

    public WebElement getAddHubButton() {
        return addHubButton;
    }

    public void addHubButtonClick() {
        addHubButton.click();
    }

    public WebElement getAccountButton() {
        return accountButton;
    }

    public WebElement getLogsButton() {
        return logsButton;
    }

    public WebElement getSettingsButton() {
        return settingsButton;
    }
}
