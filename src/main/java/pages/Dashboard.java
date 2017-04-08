package pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class Dashboard{

// ================= Header ===================

    @AndroidFindBy(id = "com.ajaxsystems:id/add")
    private WebElement addBtn;

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
    private WebElement addHubWizard;

    @AndroidFindBy(id = "com.ajaxsystems:id/key")
    private WebElement hubKeyField;

    @AndroidFindBy(id = "com.ajaxsystems:id/nameInfo")
    private WebElement nameInfo;

    @AndroidFindBy(id = "com.ajaxsystems:id/keyInfo")
    private WebElement keyInfo;

    @AndroidFindBy(id = "com.ajaxsystems:id/qr")
    private WebElement qrIcon;

    @AndroidFindBy(id = "com.ajaxsystems:id/hint")
    private WebElement hint;

// ================= Add Device ===================

    @AndroidFindBy(id = "com.ajaxsystems:id/footerTitle")
    private WebElement addDeviceButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/name")
    private WebElement roomName;

// ================= Footer ===================

    @AndroidFindBy(id = "com.ajaxsystems:id/devices")
    private WebElement footerDevices;

    @AndroidFindBy(id = "com.ajaxsystems:id/rooms")
    private WebElement footerRooms;

    @AndroidFindBy(id = "com.ajaxsystems:id/notifications")
    private WebElement footerNotifications;

    @AndroidFindBy(id = "com.ajaxsystems:id/remote")
    private WebElement footerRemote;

//----------------------------------------------------------------------------------------------------------------------
    private final Base $;

    public Dashboard(Base base) {
        $ = base;
        PageFactory.initElements(new AppiumFieldDecorator($.getDriver()), this);
    }
//----------------------------------------------------------------------------------------------------------------------

    public void fillFieldsWith(String hubName, String hubKey){
        Base.log("fill Name Field with \"" + hubName + "\"");
        nameField.sendKeys(hubName);

        Base.log("fill Hub Key Field with \"" + hubKey + "\"");
        hubKeyField.sendKeys(hubKey);
    }

    public void plusButtonClick() {
        plusButton.click();
    }

    public void footerRemoteClick() {
        footerRemote.click();
    }

    public void goToTheRoomPage() {
        footerRooms.click();
    }

    public void goToTheRemotePage() {
        footerRemote.click();
    }
    public void goToTheNotificationPage() {
        footerNotifications.click();
    }
    public void goToTheDevicesPage() {
        footerDevices.click();
    }
}

