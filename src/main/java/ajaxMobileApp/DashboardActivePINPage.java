package ajaxMobileApp;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by installer on 1/21/17.
 */
public class DashboardActivePINPage {
    public final AppiumDriver driver;

    @AndroidFindBy(id = "com.ajaxsystems:id/content_text")
    public WebElement contentText;

    @AndroidFindBy(id = "com.ajaxsystems:id/cancel_button")
    public WebElement cancelBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/confirm_button")
    public WebElement confirmBtn;



    public DashboardActivePINPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}
