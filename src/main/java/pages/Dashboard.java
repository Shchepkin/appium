package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by installer on 1/21/17.
 */
public class Dashboard extends Base{
    public final AppiumDriver driver;

// ================= Header ===================

    @AndroidFindBy(id = "com.ajaxsystems:id/add")
    public WebElement addBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/name")
    private WebElement nameField;

    @AndroidFindBy(id = "com.ajaxsystems:id/image")
    public WebElement image;

    @AndroidFindBy(id = "com.ajaxsystems:id/settings")
    private WebElement settingsBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/cancel")
    private WebElement cancelBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/save")
    private WebElement saveBtn;




    // ================= Add Hub ===================
    @AndroidFindBy(id = "com.ajaxsystems:id/plus")
    private WebElement plusButton;
    public WebElement getPlusButton() {
        return plusButton;
    }




    @AndroidFindBy(id = "com.ajaxsystems:id/panel")
    public WebElement addHubWizard;

    @AndroidFindBy(id = "com.ajaxsystems:id/key")
    private WebElement hubKeyField;

    @AndroidFindBy(id = "com.ajaxsystems:id/nameInfo")
    public WebElement nameInfo;

    @AndroidFindBy(id = "com.ajaxsystems:id/keyInfo")
    public WebElement keyInfo;

    @AndroidFindBy(id = "com.ajaxsystems:id/qr")
    public WebElement qrIcon;

    @AndroidFindBy(id = "com.ajaxsystems:id/hint")
    public WebElement hint;

// ================= Add Device ===================

    @AndroidFindBy(id = "com.ajaxsystems:id/footerTitle")
    private WebElement addDeviceButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/name")
    public WebElement roomNkame;

// ================= Footer ===================

    @AndroidFindBy(id = "com.ajaxsystems:id/devices")
    private WebElement footerDevices;

    @AndroidFindBy(id = "com.ajaxsystems:id/rooms")
    private WebElement footerRooms;

    @AndroidFindBy(id = "com.ajaxsystems:id/notifications")
    private WebElement footerNotifications;

    @AndroidFindBy(id = "com.ajaxsystems:id/remote")
    public WebElement footerRemote;

//=====================================================================================================================

    public Dashboard(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
//=====================================================================================================================

    public void fillFieldsWith(String hubName, String hubKey){
        log("fill Name Field with \"" + hubName + "\"");
        nameField.sendKeys(hubName);

        log("fill Hub Key Field with \"" + hubKey + "\"");
        hubKeyField.sendKeys(hubKey);
    }

    public void plusButtonClick() {
        plusButton.click();
    }

    public void goToTheRoomPage() {
        footerRooms.click();
    }
}

