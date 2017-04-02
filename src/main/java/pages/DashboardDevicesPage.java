package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.Wait;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by installer on 1/21/17.
 */
public class DashboardDevicesPage extends Base{

//**********************************************************************************************************************
    @AndroidFindBy(id = "com.ajaxsystems:id/footerTitle")
    private WebElement addDeviceButton;

    public void addDeviceButtonClick(){
        addDeviceButton.click();
    }
//**********************************************************************************************************************
    @AndroidFindBy(id = "com.ajaxsystems:id/rooms")
    private WebElement setRoomButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/image")
    private ArrayList<WebElement> allRoomObjects;

    @AndroidFindBy(id = "com.ajaxsystems:id/type")
    private WebElement roomObject;

    public void setRoom(int numOfRoom) {
        if (numOfRoom > 0) {
            setRoomButton.click();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            wait.element(roomObject, 5, true);

            log("set room number as \"" + numOfRoom + "\"");
            allRoomObjects.get(numOfRoom - 1).click();
        } else {
            log(3, "invalid number of Room, number has to be > 0");
            System.exit(0);
        }
    }
//**********************************************************************************************************************
    @AndroidFindBy(id = "com.ajaxsystems:id/name")
    private WebElement nameField;

    @AndroidFindBy(id = "com.ajaxsystems:id/id")
    private WebElement idField;

    public void fillFieldsWith(String deviceName, String devID){
        log("fill name field with \"" + deviceName + "\"");
        nameField.sendKeys(deviceName);

        hideKeyboard();

        log("fill ID field with \"" + devID + "\"");
        idField.sendKeys(devID);
    }
//**********************************************************************************************************************
    public DashboardDevicesPage(AppiumDriver driver) {
        this.driver = driver;
        wait = new Wait(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}
