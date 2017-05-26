package pageObjects.pages.dashboard;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pageObjects.Base;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class DevicesPage {

    @AndroidFindBy(id = "com.ajaxsystems:id/add")
    private WebElement addDeviceButtonOnEmptyRoomsPage;

    @AndroidFindBy(id = "com.ajaxsystems:id/footerTitle")
    private WebElement addDeviceButtonOld;

    @AndroidFindBy(id = "com.ajaxsystems:id/addDevice")
    private WebElement addDeviceButtonNew;

    public String getAddDeviceButtonId() {
        return addDeviceButtonId;
    }

    private String addDeviceButtonId = "com.ajaxsystems:id/addDevice";

    @AndroidFindBy(id = "com.ajaxsystems:id/unpair")
    private WebElement unpairButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/addCamera")
    private WebElement addCameraButton;

    public WebElement getAddDeviceButtonOnEmptyRoomsPage() {
        return addDeviceButtonOnEmptyRoomsPage;
    }
    public WebElement getAddDeviceButtonOld() {
        return addDeviceButtonOld;
    }
    public WebElement getAddDeviceButtonNew() {
        return addDeviceButtonNew;
    }

//----------------------------------------------------------------------------------------------------------------------
    @AndroidFindBy(id = "com.ajaxsystems:id/rooms")
    private WebElement setRoomButtonElement;

    @AndroidFindBy(id = "com.ajaxsystems:id/room")
    private WebElement roomOfDeviceLocator;

    @AndroidFindBy(id = "com.ajaxsystems:id/image")
    private ArrayList<WebElement> allRoomObjects;

    @AndroidFindBy(id = "com.ajaxsystems:id/type")
    private WebElement roomObject;

    public WebElement getRoomOfDeviceLocator() {
        return roomOfDeviceLocator;
    }

//----------------------------------------------------------------------------------------------------------------------
    @AndroidFindBy(id = "com.ajaxsystems:id/name")
    private WebElement nameField;

    @AndroidFindBy(id = "com.ajaxsystems:id/id")
    private WebElement idField;

//----------------------------------------------------------------------------------------------------------------------
    private Base base;
    private AndroidDriver driver;
    private boolean result;

    public DevicesPage(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.DEFAULT_TIMEOUT, TimeUnit.SECONDS), this);
    }
//----------------------------------------------------------------------------------------------------------------------

    public void addDeviceButtonClick(){
        Base.log(1, "tap Add Device button", true);
        try {
            addDeviceButtonOld.click();
        }catch (Exception e){
            try {
                addDeviceButtonNew.click();
            }catch (Exception e1){
                base.nav.getAddButton().click();
            }
        }
    }

    public void unpairButtonClick(){
        Base.log(1, "tap Unpair button", true);
        unpairButton.click();
    }

    public void goToFirstDeviceSettingsPage(){
        Base.log(1, "click on Device tab (room element)", true);
        roomOfDeviceLocator.click();
        base.nav.goToSettings();
    }

    public void fillFieldsWith(String deviceName, String devID){
        Base.log(1, "fill name field with \"" + deviceName + "\"", true);
        nameField.sendKeys(deviceName);
        base.hideKeyboard();

        Base.log(1, "fill ID field with \"" + devID + "\"", true);
        idField.sendKeys(devID);
    }

    public void setRoom(int numOfRoom) {
        if (numOfRoom > 0) {
            Base.log(1, "tap Set Room button", true);
            setRoomButtonElement.click();

            Base.log(1, "wating for rooms popUp appear");
            base.wait.element(roomObject, 10, true);

            Base.log(1, "set room number to \"" + numOfRoom + "\"", true);
            allRoomObjects.get(numOfRoom - 1).click();
        } else {
            Base.log(3, "ignor room");
        }
    }

    public String getFirstDeviceName(){
        String roomXpath = "*[@resource-id = 'com.ajaxsystems:id/room']";
        String nameXpath = "*[@resource-id = 'com.ajaxsystems:id/name']";
        String xPath = "//" + roomXpath + "/preceding-sibling::" + nameXpath;
        return driver.findElementByXPath(xPath).getText();
    }

    public boolean deleteAll() {
        base.nav.gotoPage.Devices();
        String devName = null;
        int counter = 0;
        try {
            while (true) {
                if (base.wait.menuIconOrPinPopUp(10)){
                    if (counter != 0) {
                        Base.log(1, "device with name \"" + devName + "\" is deleted successfully\n", true);
                    }
                    base.nav.cancelIt();
                    if (!base.check.isEmpty.devicesList()){
                        devName = getFirstDeviceName();
                        goToFirstDeviceSettingsPage();
                    }else if (counter > 0){
                        Base.log(1, "devices are not found, number of deleted devices: " + counter);
                        return true;
                    }else {
                        Base.log(2, "Test impossible, because precondition isn't valid - no one element found");
                        return false;
                    }

//                    base.nav.scrollBottom();
                    unpairButtonClick();
                    Base.log(1, "Confirm proposition", true);
                    base.nav.confirmIt();
                    counter++;

                }else {
                    Base.log(2, "Test impossible, I don't know where are we");
                    return false;
                }
            }
        }catch (NoSuchElementException e){
            Base.log(1, "NoSuchElementException: \n\n" + e + "\n");
            return false;
        }
    }

}
