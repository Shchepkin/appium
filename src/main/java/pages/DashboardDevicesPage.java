package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class DashboardDevicesPage{

    @AndroidFindBy(id = "com.ajaxsystems:id/footerTitle")
    private WebElement addDeviceButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/unpair")
    private WebElement unpairButton;

//----------------------------------------------------------------------------------------------------------------------
    @AndroidFindBy(id = "com.ajaxsystems:id/rooms")
    private WebElement setRoomButtonElement;

    @AndroidFindBy(id = "com.ajaxsystems:id/room")
    private WebElement roomOfDeviceLocator;

    @AndroidFindBy(id = "com.ajaxsystems:id/image")
    private ArrayList<WebElement> allRoomObjects;

    @AndroidFindBy(id = "com.ajaxsystems:id/type")
    private WebElement roomObject;

//----------------------------------------------------------------------------------------------------------------------
    @AndroidFindBy(id = "com.ajaxsystems:id/name")
    private WebElement nameField;

    @AndroidFindBy(id = "com.ajaxsystems:id/id")
    private WebElement idField;

//----------------------------------------------------------------------------------------------------------------------
    private Base base;
    private AppiumDriver driver;
    private boolean result;

    public DashboardDevicesPage(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.TIMEOUT, TimeUnit.SECONDS), this);
    }
//----------------------------------------------------------------------------------------------------------------------

    public void addDeviceButtonClick(){
        Base.log(1, "tap Add Device button");
        addDeviceButton.click();
    }

    public void unpairButtonClick(){
        Base.log(1, "click delete button");
        unpairButton.click();
    }

    public void goToDeviceSettingsPage(){
        Base.log(1, "click on Device tab (room element)");
        roomOfDeviceLocator.click();

        Base.log(1, "click Settings Button");
        base.nav.goToSettings();
    }

    public void fillFieldsWith(String deviceName, String devID){
        Base.log(1, "fill name field with \"" + deviceName + "\"", true);
        nameField.sendKeys(deviceName);

        Base.log(1, "hide keyboard");
        base.hideKeyboard();

        Base.log(1, "fill ID field with \"" + devID + "\"", true);
        idField.sendKeys(devID);
    }

    public void setRoom(int numOfRoom) {
        if (numOfRoom > 0) {
            Base.log(1, "click Set Room button");
            setRoomButtonElement.click();

            Base.log(1, "wating for rooms popUp appear");
            base.wait.element(roomObject, 10, true);

            Base.log(1, "set room number as \"" + numOfRoom + "\"", true);
            allRoomObjects.get(numOfRoom - 1).click();
        } else {
            Base.log(3, "invalid number of Room, number has to be > 0");
            Base.log(3, "set number of Room to 1 as default");
            setRoom(1);
        }
    }

    public boolean checkIsNewAdded(String roomName) {
        if (base.nav.scrollToElementWith.name(roomName, false)) {
            Base.log(1, "new device with name \"" + roomName + "\" is added successfully");
            result = true;
        }else {
            Base.log(3, "device with name \"" + roomName + "\" is not added");
            result = false;
        }
        return result;
    }

    public String getFirstDeviceName(){
        String roomXpath = "*[@resource-id = 'com.ajaxsystems:id/room']";
        String nameXpath = "*[@resource-id = 'com.ajaxsystems:id/name']";
        String xPath = "//" + roomXpath + "/preceding-sibling::" + nameXpath;
        return driver.findElementByXPath(xPath).getText();
    }

    public boolean deleteAllDevices() {
        String successText = base.getLocalizeTextForKey("Deleting_success1");
        int counter = 0;
        try {
            while (true) {
                if (base.wait.element(base.dashboardHeader.getMenuDrawer(), 5, true)){
                    String devName = getFirstDeviceName();
                    goToDeviceSettingsPage();
                    base.nav.scrollBottom();
                    unpairButtonClick();
                    base.nav.confirmIt();

                    if (base.wait.elementWithText(successText, 10, true)){
                        base.wait.element(base.dashboardHeader.getMenuDrawer(), 5, true);
                        base.check.isDeletedBy("name", devName);
                        Base.log(1, "device with name \"" + devName + "\" is deleted successfully and SUCCESS text is shown");
                        counter++;
                    }else {
                        Base.log(3, "SUCCESS text is not shown");
                        return false;
                    }

                }else if (base.nav.getCancelButton().isDisplayed()){
                    base.nav.cancelIt();
                }else {
                    break;
                }
            }
        }catch (NoSuchElementException e){
            Base.log(1, "NoSuchElementException: \n\n" + e + "\n");
        }
        if (counter > 0) {
            Base.log(1, "element are not found, number of deleted elements: " + counter);
            result = true;
        }else {
            Base.log(3, "Test impossible, because precondition isn't valid - no one element found");
            result = false;
        }
        return result;
    }



    public boolean dellBy(String by) {
        boolean result = false;

        switch (by){
            case "room":
                break;
            case "name":
                break;
            case "all":
                break;
            default:
        }

//        if (nav.scrollToElementWith("name", "up", roomName, false)) {
//            Base.log(1, "new device with name \"" + roomName + "\" is added successfully");
//            result = true;
//        }else {
//            Base.log(3, "device with name \"" + roomName + "\" is not added");
//            result = false;
//        }
        return result;
    }




}
