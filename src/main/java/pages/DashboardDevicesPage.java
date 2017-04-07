package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.Imitator;

import java.util.ArrayList;

public class DashboardDevicesPage{

    @AndroidFindBy(id = "com.ajaxsystems:id/footerTitle")
    private WebElement addDeviceButton;

//----------------------------------------------------------------------------------------------------------------------
    @AndroidFindBy(id = "com.ajaxsystems:id/rooms")
    private WebElement setRoomButton;

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
            setRoomButton.click();
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

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
//        imitator.getDeviceList();

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
