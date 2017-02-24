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

 // ================= Header ===================


    @AndroidFindBy(id = "com.ajaxsystems:id/add")
    public WebElement addBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/name")
    public WebElement nameField;

    @AndroidFindBy(id = "com.ajaxsystems:id/cancel")
    public WebElement cancelBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/save")
    public WebElement saveBtn;

// ================= Add Hub ===================
    @AndroidFindBy(id = "com.ajaxsystems:id/plus")
    public WebElement plusBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/panel")
    public WebElement addHubWizard;

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

// ================= Add Rooms ===================

    @AndroidFindBy(id = "com.ajaxsystems:id/name")
    public WebElement roomName;

    @AndroidFindBy(id = "com.ajaxsystems:id/name")
    public WebElement roomNkame;


// ================= Footer ===================

    @AndroidFindBy(id = "com.ajaxsystems:id/devices")
    public WebElement footerDevices;

    @AndroidFindBy(id = "com.ajaxsystems:id/rooms")
    public WebElement footerRooms;

    @AndroidFindBy(id = "com.ajaxsystems:id/notifications")
    public WebElement footerNotifications;

    @AndroidFindBy(id = "com.ajaxsystems:id/remote")
    public WebElement footerRemote;




    public Dashboard(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}
