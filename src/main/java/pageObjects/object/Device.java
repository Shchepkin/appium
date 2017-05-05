package pageObjects.object;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pageObjects.Base;

import java.util.concurrent.TimeUnit;

/**
 * Created by installer on 4/29/17.
 */
public class Device {

    @AndroidFindBy(id = "com.ajaxsystems:id/timerText")
    private WebElement timerText;

    public WebElement getTimerText() {
        return timerText;
    }

//----------------------------------------------------------------------------------------------------------------------
    private Base base;
    private AndroidDriver driver;

    public Device(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.TIMEOUT, TimeUnit.SECONDS), this);
    }

//----------------------------------------------------------------------------------------------------------------------
    public boolean checkIsNewAdded(String deviceName) {
        if (base.nav.scroll.toElementWith.name(deviceName, false)) {
            Base.log(1, "new device with name \"" + deviceName + "\" is added successfully", true);
            return true;
        } else {
            Base.log(3, "device with name \"" + deviceName + "\" is not added", true);
            return false;
        }
    }

    public class addNew{
        public void fromDevicesPage(){}
        public void fromRoomsPage(){}
    }


}
