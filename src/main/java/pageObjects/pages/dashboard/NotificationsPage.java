package pageObjects.pages.dashboard;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pageObjects.Base;

import java.util.concurrent.TimeUnit;

public class NotificationsPage {

    @AndroidFindBy(id = "com.ajaxsystems:id/mail")
    public WebElement emailField;

    @AndroidFindBy(id = "com.ajaxsystems:id/badge")
    private WebElement badgeOnNotificationTab;

    public WebElement getBadgeOnNotificationTab() {
        return badgeOnNotificationTab;
    }

//----------------------------------------------------------------------------------------------------------------------
    private final Base base;
    private final AppiumDriver driver;

    public NotificationsPage(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.TIMEOUT, TimeUnit.SECONDS), this);
    }
//----------------------------------------------------------------------------------------------------------------------

}
