package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by installer on 1/21/17.
 */
public class DashboardRoomsPage {
    public final AppiumDriver driver;

    @AndroidFindBy(id = "com.ajaxsystems:id/add")
    public WebElement addRoomBtn;

    public DashboardRoomsPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}
