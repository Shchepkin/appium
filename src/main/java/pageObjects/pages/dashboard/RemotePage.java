package pageObjects.pages.dashboard;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pageObjects.Base;

import java.util.concurrent.TimeUnit;

public class RemotePage {

    @AndroidFindBy(id = "com.ajaxsystems:id/arm")
    private WebElement armButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/disarm")
    private WebElement disarmButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/partial")
    private WebElement partialArmButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/alarm")
    private WebElement alarmButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/securityText")
    private WebElement securityText;



    @AndroidFindBy(id = "com.ajaxsystems:id/spaceControl")
    private WebElement spaceControlImage;

    public WebElement getAlarmButton() {
        return alarmButton;
    }
    public WebElement getPartialArmButton() {
        return partialArmButton;
    }
    public WebElement getDisarmButton() {
        return disarmButton;
    }
    public WebElement getArmButton() {
        return armButton;
    }
    public WebElement getSpaceControlImage() {
        return spaceControlImage;
    }

//----------------------------------------------------------------------------------------------------------------------
    private final Base base;
    private final AppiumDriver driver;

    public RemotePage(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.TIMEOUT, TimeUnit.SECONDS), this);
    }
//----------------------------------------------------------------------------------------------------------------------


    public void clickAlarmButton(){
        Base.log(1, "tap Alarm Button");
        alarmButton.click();
    }

    public void clickArmButton(){
        Base.log(1, "tap Arm Button");
        armButton.click();
    }

    public void clickDisarmButton(){
        Base.log(1, "tap Disarm Button");
        disarmButton.click();
    }

    public void clickPartialArmButton(){
        Base.log(1, "tap PartialArm Button");
        partialArmButton.click();
    }


}
