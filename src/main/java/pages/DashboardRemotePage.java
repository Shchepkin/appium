package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
//import utils.Setup;

/**
 * Created by installer on 1/21/17.
 */
public class DashboardRemotePage extends Base{
    public final AppiumDriver driver;
    private Dashboard dashboard;
    private boolean result;



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

    public DashboardRemotePage(AppiumDriver driver) {
        this.driver = driver;
        dashboard = new Dashboard(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
//======================================================================================================================

    public boolean goToTheRemotePage(){
        log("method is started");
        result = false;

        dashboard.footerRemoteClick();
        if (spaceControlImage.isDisplayed()) {
            result = true;
        }
        log("Method is finished");
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

    public WebElement getArmButton() {
        return armButton;
    }

    public WebElement getDisarmButton() {
        return disarmButton;
    }

    public WebElement getPartialArmButton() {
        return partialArmButton;
    }

    public WebElement getAlarmButton() {
        return alarmButton;
    }
}
