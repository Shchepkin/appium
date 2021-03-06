package pageObjects.pages.dashboard;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pageObjects.Base;

import java.util.concurrent.TimeUnit;

public class Header {

    @AndroidFindBy(id = "com.ajaxsystems:id/menuDrawer")
    private WebElement menuDrawer;
    public WebElement getMenuDrawer() {
        return menuDrawer;
    }

    @AndroidFindBy(id = "com.ajaxsystems:id/connection")
    private WebElement connectionIcon;

    @AndroidFindBy(id = "com.ajaxsystems:id/hubImage")
    private WebElement hubImage;

    @AndroidFindBy(id = "com.ajaxsystems:id/gprs")
    private WebElement gprsImage;

    @AndroidFindBy(id = "com.ajaxsystems:id/hubSecurity")
    private WebElement hubSecurity;

    @AndroidFindBy(id = "com.ajaxsystems:id/title")
    private WebElement title;

    @AndroidFindBy(id = "com.ajaxsystems:id/status")
    private WebElement status;

    @AndroidFindBy(id = "com.ajaxsystems:id/normal")
    private WebElement normalElement;

    @AndroidFindBy(id = "com.ajaxsystems:id/hubOffline")
    private WebElement hubOffline;

//----------------------------------------------------------------------------------------------------------------------
    private final Base base;
    private final AndroidDriver driver;

    public Header(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.DEFAULT_TIMEOUT, TimeUnit.SECONDS), this);
    }
//----------------------------------------------------------------------------------------------------------------------

    public boolean isMenuIconPresent(int timer){
        boolean result = false;
        return result;
    }

    public WebElement getConnectionIcon() {
        return connectionIcon;
    }
    public WebElement getHubImage() {
        return hubImage;
    }
    public WebElement getHubSecurity() {
        return hubSecurity;
    }
    public WebElement getTitle() {
        return title;
    }
    public WebElement getStatus() {
        return status;
    }
    public WebElement getNormalElement() {
        return normalElement;
    }
    public WebElement getHubOffline() {
        return hubOffline;
    }
    public WebElement getGprsImage() {return gprsImage;}
}
