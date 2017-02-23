package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by installer on 1/21/17.
 */
public class Dashboard {
    public final AppiumDriver driver;


// ================= Add Hub ===================
    @AndroidFindBy(id = "com.ajaxsystems:id/plus")
    public WebElement plusBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/panel")
    public WebElement addHubWizard;

    @AndroidFindBy(id = "com.ajaxsystems:id/add")
    public WebElement addHubBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/cancel")
    public WebElement cancelBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/name")
    public WebElement hubName;

    @AndroidFindBy(id = "com.ajaxsystems:id/key")
    public WebElement hubKey;

    @AndroidFindBy(id = "com.ajaxsystems:id/nameInfo")
    public WebElement nameInfo;

    @AndroidFindBy(id = "com.ajaxsystems:id/keyInfo")
    public WebElement keyInfo;

    @AndroidFindBy(id = "com.ajaxsystems:id/qr")
    public WebElement qrIcon;

    @AndroidFindBy(id = "com.ajaxsystems:id/hint")
    public WebElement hint;



    public Dashboard(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}
