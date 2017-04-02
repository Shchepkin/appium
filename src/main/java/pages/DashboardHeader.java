package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class DashboardHeader extends Base{
    public final AppiumDriver driver;

    @AndroidFindBy(id = "com.ajaxsystems:id/menuDrawer")
    public WebElement menuDrawer;

    @AndroidFindBy(id = "com.ajaxsystems:id/connection")
    public WebElement connectionIcon;

    @AndroidFindBy(id = "com.ajaxsystems:id/hubImage")
    public WebElement hubImage;

    @AndroidFindBy(id = "com.ajaxsystems:id/hubSecurity")
    public WebElement hubSecurity;

    @AndroidFindBy(id = "com.ajaxsystems:id/title")
    public WebElement title;

    @AndroidFindBy(id = "com.ajaxsystems:id/status")
    public WebElement status;

    @AndroidFindBy(id = "com.ajaxsystems:id/normal")
    public WebElement normalElement;

    @AndroidFindBy(id = "com.ajaxsystems:id/hubOffline")
    public WebElement hubOffline;

     public DashboardHeader(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}
