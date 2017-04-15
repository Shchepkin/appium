package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class DashboardRemotePage{

    @AndroidFindBy(id = "com.ajaxsystems:id/arm")
    private WebElement armButton;
    public WebElement getArmButton() {
        return armButton;
    }

    @AndroidFindBy(id = "com.ajaxsystems:id/disarm")
    private WebElement disarmButton;
    public WebElement getDisarmButton() {
        return disarmButton;
    }

    @AndroidFindBy(id = "com.ajaxsystems:id/partial")
    private WebElement partialArmButton;
    public WebElement getPartialArmButton() {
        return partialArmButton;
    }

    @AndroidFindBy(id = "com.ajaxsystems:id/alarm")
    private WebElement alarmButton;
    public WebElement getAlarmButton() {
        return alarmButton;
    }

    @AndroidFindBy(id = "com.ajaxsystems:id/securityText")
    private WebElement securityText;

    @AndroidFindBy(id = "com.ajaxsystems:id/spaceControl")
    private WebElement spaceControlImage;

//----------------------------------------------------------------------------------------------------------------------
    private final Base base;
    private final AppiumDriver driver;
    private boolean result;

    public DashboardRemotePage(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.TIMEOUT, TimeUnit.SECONDS), this);
    }
//----------------------------------------------------------------------------------------------------------------------

    public boolean goToTheRemotePage(){
        Base.log(1, "method is started");
        result = false;

        base.dashboard.goToTheRemotePage();
        if (spaceControlImage.isDisplayed()) {
            result = true;
        }
        Base.log(1, "Method is finished");
        return result;
    }

    public void clickAlarmButton(){
        alarmButton.click();
    }

    public void clickArmButton(){
        armButton.click();
    }

    public void clickDisarmButton(){
        disarmButton.click();
    }

    public void clickPartialArmButton(){
        partialArmButton.click();
    }


}
