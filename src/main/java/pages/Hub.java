package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class Hub{

    @AndroidFindBy(id = "com.ajaxsystems:id/usersImage")
    private WebElement hubSettingsUsersImage;

    @AndroidFindBy(id = "com.ajaxsystems:id/usersText")
    private WebElement hubSettingsUsersText;

    @AndroidFindBy(id = "com.ajaxsystems:id/image")
    private WebElement hubImageOnDeviceList;

    @AndroidFindBy(id = "com.ajaxsystems:id/settings")
    private WebElement hubSettingsBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/status")
    private AndroidElement userStatus;

//----------------------------------------------------------------------------------------------------------------------
    private final Base base;
    private final AndroidDriver driver;
    private String sendInvitesButtonText,armedText, disarmedText, patrialArmedText;

    public Hub(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        armedText = base.getLocalizeTextForKey("armed");
        disarmedText = base.getLocalizeTextForKey("disarmed");
        patrialArmedText = base.getLocalizeTextForKey("partially_armed");
        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.TIMEOUT, TimeUnit.SECONDS), this);
    }
//----------------------------------------------------------------------------------------------------------------------

    public void goToTheUserInvitationPage() {
        Base.log("method is started");
        sendInvitesButtonText = base.getLocalizeTextForKey("send_invites");

        Base.log("click on hub");
        hubImageOnDeviceList.click();

        Base.log("click Hub Settings button");
        hubSettingsBtn.click();

        Base.log("click Users tab");
        hubSettingsUsersImage.click();

        base.wait.element(userStatus, 10, true);
        base.nav.scrollToElementWith("text", "up", sendInvitesButtonText, true);
        Base.log("method is finished");
    }

    public void goToTheUserlistPage() {
        Base.log("method is started");

        Base.log("click on Hub tab");
        hubImageOnDeviceList.click();

        Base.log("click Hub Settings button");
        hubSettingsBtn.click();

        Base.log("click Users tab");
        hubSettingsUsersImage.click();

        base.wait.element(userStatus, 10, true);
        Base.log("method is finished");
    }

    public void arm() {
        Base.log("method is started");

        base.dashboard.goToTheRemotePage();
        base.remotePage.clickArmButton();
        base.nav.confirmIt();

        base.wait.elementWithText(armedText, 10, true);
        Base.log("method is finished");
    }

    public void disarm() {
        Base.log("method is started");

        base.dashboard.goToTheRemotePage();
        base.remotePage.clickDisarmButton();

        base.wait.elementWithText(disarmedText, 10, true);
        Base.log("method is finished");
    }

    public void partialArm() {
        Base.log("method is started");

        base.dashboard.goToTheRemotePage();
        base.remotePage.clickPartialArmButton();
        base.nav.confirmIt();

        base.wait.elementWithText(patrialArmedText, 10, true);
        Base.log("method is finished");
    }

    public void addNew() {
        Base.log("method is started");

        Base.log("get creds for hubName and hubMasterKey");
        String hubName = base.getCredsWithKey("hubName");
        String hubMasterKey = base.getCredsWithKey("hubMasterKey");


        if (base.dashboardHeader.getMenuDrawer().isDisplayed()){
            if (base.dashboard.getPlusButton().isDisplayed()){
                Base.log("add Hub by Plus Button");
                base.dashboard.plusButtonClick();
            }else {
                Base.log("add Hub from Main Menu");
                base.dashboardHeader.getMenuDrawer().click();
                base.menuPage.addHubButtonClick();
            }
        }

        Base.log("choose manual Hub adding ");
        base.nav.nextButtonClick();

        base.dashboard.fillFieldsWith(hubName, hubMasterKey);
        base.nav.confirmIt();

        base.wait.invisibilityOfWaiter(true);
        Assert.assertFalse(base.check.isErrorPresent(2), "Hub adding failed!");

        Assert.assertTrue(base.wait.element(base.dashboardHeader.getGprsImage(), 10, true));
        Base.log("hub successfully added!");
        Base.log("method is finished");
    }
}
