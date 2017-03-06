package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.Setup;

/**
 * Created by installer on 1/21/17.
 */
public class DashboardRemotePage {
    public final AppiumDriver driver;
    private Setup s = new Setup();
    private Dashboard dashboard;
    private boolean result;



    @AndroidFindBy(id = "com.ajaxsystems:id/arm")
    public WebElement armBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/disarm")
    public WebElement disarmBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/partial")
    public WebElement partialArmBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/alarm")
    public WebElement alarmBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/securityText")
    public WebElement securityText;

    @AndroidFindBy(id = "com.ajaxsystems:id/spaceControl")
    private WebElement spaceControlImage;

    public DashboardRemotePage(AppiumDriver driver) {
        this.driver = driver;
        dashboard = new Dashboard(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
//======================================================================================================================

    public boolean goToTheRemotePage(){
        s.log("method is started");
        result = false;

        dashboard.footerRemote.click();
        if (spaceControlImage.isDisplayed()) {
            result = true;
        }
        s.log("Method is finished");
        return result;
    }

}
