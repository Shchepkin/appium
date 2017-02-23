package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by installer on 1/21/17.
 */
public class DashboardDevicesPage {
    public final AppiumDriver driver;

    @AndroidFindBy(id = "com.ajaxsystems:id/plus")
    public WebElement plusBtn;

    public DashboardDevicesPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}
