package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.ArrayList;

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
    private final Base $;
    private final AppiumDriver driver;
    public DashboardDevicesPage(Base base) {
        $ = base;
        this.driver = $.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
//----------------------------------------------------------------------------------------------------------------------

    public void addDeviceButtonClick(){
        Base.log("click Add Device button");
        addDeviceButton.click();
    }

    public void unpairButtonClick(){
        Base.log("click delete button");
        unpairButton.click();
    }

    public void goToDeviceSettingsPage(){
        Base.log("click on Device tab (room element)");
        roomOfDeviceLocator.click();

        Base.log("click Settings Button");
        $.nav.goToSettings();
    }

    public void fillFieldsWith(String deviceName, String devID){
        Base.log("fill name field with \"" + deviceName + "\"");
        nameField.sendKeys(deviceName);

        $.hideKeyboard();

        Base.log("fill ID field with \"" + devID + "\"");
        idField.sendKeys(devID);
    }

    public void setRoom(int numOfRoom) {
        if (numOfRoom > 0) {
            Base.log("click Set Room button");
            setRoomButtonElement.click();

            $.wait.element(roomObject, 10, true);

            Base.log("set room number as \"" + numOfRoom + "\"");
            allRoomObjects.get(numOfRoom - 1).click();
        } else {
            Base.log(3, "invalid number of Room, number has to be > 0");
            System.exit(0);
        }
    }

    public void addNew(int devId, int devNumber, int devType, String devName, int roomNumber) {
        Base.log("add devices to imitator");
        $.imitator.addDevice(devId, devNumber, devType);

        Base.log("add devices \"" + devName + "\" to Hub");
        $.nav.scrollBottom();
        addDeviceButtonClick();
        fillFieldsWith(devName, String.valueOf(devId));
        $.hideKeyboard();
        setRoom(roomNumber);

        Base.log("add devices button click");
        $.nav.confirmIt();

        Base.log("device turn on");
        $.imitator.registerDevice(devId);
    }

    public boolean checkIsNewDeviceAdded(String roomName) {
        boolean result = false;

        if ($.nav.scrollToElementWith("name", "up", roomName, false)) {
            Base.log("new device with name \"" + roomName + "\" is added successfully");
            result = true;
        }else {
            Base.log(3, "device with name \"" + roomName + "\" is not added");
            result = false;
        }
        return result;
    }
    private class name{
    }

    public String getFirstDeviceName(){
        String roomXpath = "*[@resource-id = 'com.ajaxsystems:id/room']";
        String nameXpath = "*[@resource-id = 'com.ajaxsystems:id/name']";
        String xPath = "//" + roomXpath + "/preceding-sibling::" + nameXpath;
        return driver.findElementByXPath(xPath).getText();
    }

    public void deleteAllDevices() {
        String successText = $.getLocalizeTextForKey("Deleting_success1");
        int counter = 0;

        try {
            while (true) {
                if ($.wait.element($.dashboardHeader.getMenuDrawer(), 5, true)){
                    String devName = getFirstDeviceName();
                    goToDeviceSettingsPage();
                    $.nav.scrollBottom();
                    unpairButtonClick();
                    $.nav.confirmIt();
                    Assert.assertTrue($.wait.elementWithText(successText, 10, true), "SUCCESS text is not shown");
                    $.wait.element($.dashboardHeader.getMenuDrawer(), 5, true);
                    Base.log("device with name \"" + devName + "\" is deleted successfully and SUCCESS text is shown");
                    counter++;

                }else if ($.nav.getCancelButton().isDisplayed()){
                    $.nav.cancelIt();
                }else {
                    break;
                }
            }
        }catch (NoSuchElementException e){
            Base.log(1, "NoSuchElementException: \n\n" + e + "\n");
        }
        Assert.assertTrue(counter > 0, "Test impossible, because precondition isn't valid - no one device found\n");
        Base.log(1, "devices are not found, number of deleted devices: " + counter);
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
//            Base.log("new device with name \"" + roomName + "\" is added successfully");
//            result = true;
//        }else {
//            Base.log(3, "device with name \"" + roomName + "\" is not added");
//            result = false;
//        }
        return result;
    }




}
