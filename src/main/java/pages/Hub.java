package pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;
// TODO remove all asserts
public class Hub{

    @AndroidFindBy(id = "com.ajaxsystems:id/usersImage")
    private WebElement hubSettingsUsersImage;

    @AndroidFindBy(id = "com.ajaxsystems:id/usersText")
    private WebElement hubSettingsUsersText;

    @AndroidFindBy(id = "com.ajaxsystems:id/image")
    private WebElement hubImageOnDeviceList;

    @AndroidFindBy(id = "com.ajaxsystems:id/settings")
    private WebElement settingsButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/status")
    private AndroidElement userStatus;

    @AndroidFindBy(id = "com.ajaxsystems:id/unpair")
    private AndroidElement unpairButton;

    public WebElement getHubSettingsUsersImage() {
        return hubSettingsUsersImage;
    }
    public WebElement getHubImageOnDeviceList() {
        return hubImageOnDeviceList;
    }
    public WebElement getSettingsButton() {
        return settingsButton;
    }
    public AndroidElement getUnpairButton() {
        return unpairButton;
    }
    public AndroidElement getUserStatus() {
        return userStatus;
    }
//----------------------------------------------------------------------------------------------------------------------
    private final Base base;
    private final AndroidDriver driver;
    private String sendInvitesButtonText,armedText, disarmedText, patrialArmedText;
    public DeleteFrom deleteFrom = new DeleteFrom();
    public AddNew addNew = new AddNew();
    public Security security = new Security();

    public Hub(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        armedText = base.getLocalizeTextForKey("armed");
        disarmedText = base.getLocalizeTextForKey("disarmed");
        patrialArmedText = base.getLocalizeTextForKey("partially_armed");
        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.TIMEOUT, TimeUnit.SECONDS), this);
    }
//----------------------------------------------------------------------------------------------------------------------

    public class AddNew {
        public void fromMenu(){}
        public void fromPlusButton(){}
    }

    public class Security {
        public void arm() {
            Base.log(4, "Method is started");
            base.nav.gotoPage.Remote();
            base.remotePage.clickArmButton();

            Base.log(1, "confirm if popUp appear");
            base.nav.confirmIt();

            if (base.wait.elementWithText(base.getLocalizeTextForKey("armed"), 5 , true)){
                Base.log(1, "Hub is armed successfully", true);
            }else {
                Base.log(3, "Hub is not armed", true);
            }
            Base.log(4, "Method is finished");
        }
        public void disarm() {
            Base.log(4, "Method is started");

            Base.log(1, "goto Remote Page");
            base.nav.gotoPage.Remote();

            base.remotePage.clickDisarmButton();

            if (base.wait.elementWithText(base.getLocalizeTextForKey("disarmed"), 5 , true)){
                Base.log(1, "Hub is disarmed successfully", true);
            }else {
                Base.log(3, "Hub is not disarmed", true);
            }
            Base.log(4, "Method is finished");
        }
        public void partialArm() {
            Base.log(4, "Method is started");
            base.nav.gotoPage.Remote();

            base.remotePage.clickPartialArmButton();

            Base.log(1, "confirm if popUp appear");
            base.nav.confirmIt();

            if (base.wait.elementWithText(base.getLocalizeTextForKey("partially_armed"), 5 , true)){
                Base.log(1, "Hub is partially armed successfully", true);
            }else {
                Base.log(3, "Hub is not partially armed", true);
            }
            Base.log(4, "Method is finished");
        }
        public void alarm(){
            Base.log(4, "Method is started");
            Base.log(1, "go to Remote page");
            base.nav.gotoPage.Remote();
            base.remotePage.clickAlarmButton();
            Base.log(4, "Method is finished");
        }
    }

    public void addNewManual() {
        Base.log(4, "Method is started");

        Base.log(1, "get creds for hubName and hubMasterKey");
        String hubName = base.getCredsWithKey("hubName");
        String hubMasterKey = base.getCredsWithKey("hubMasterKey");


        if (base.wait.element(base.dashboardHeader.getMenuDrawer(), 2, true)){
            if (base.wait.element(base.dashboard.getPlusButton(), 2, true)){
                Base.log(1, "add Hub by Plus Button");
                base.dashboard.plusButtonClick();
            }else {
                Base.log(1, "add Hub from Main Menu");

                Base.log(1, "tap Main Menu icon");
                base.dashboardHeader.getMenuDrawer().click();

                Base.log(1, "tap Add Hub button");
                base.menuPage.addHubButtonClick();
            }
        }

        Base.log(1, "choose manual Hub adding ");
        base.nav.nextButtonClick();

        base.dashboard.fillFieldsWith(hubName, hubMasterKey);
        base.nav.confirmIt();

        base.wait.invisibilityOfWaiter();
        Assert.assertFalse(base.check.isErrorPresent(2), "Hub adding failed!");

        Assert.assertTrue(base.wait.element(base.dashboardHeader.getGprsImage(), 10, true));
        Base.log(1, "hub successfully added!");
        Base.log(4, "Method is finished");
    }

    public class DeleteFrom {
        public boolean hubSettings(boolean withCheckLocalizedText) {
            Base.log(1, "go to the hubSettings", true);
            base.nav.gotoPage.hubSettings();
            base.nav.scrollBottom();

            Base.log(1, "tap Delete button", true);
            unpairButton.click();

            if (withCheckLocalizedText){
                if(!base.check.localizedTextFor.confirmLoader.hubDeleteFromHubSettings()) return false;
            }
            base.nav.confirmIt();
            base.wait.invisibilityOfWaiter();

            return base.check.localizedTextFor.successMessage.hubDelete();
        }
        public boolean masterUserSettings(boolean withCheckLocalizedText) {
            Base.log(1, "go to the userList", true);
            base.nav.gotoPage.userList();

            Base.log(1, "tap first settings button in the UserList", true);
            settingsButton.click();

            base.nav.scrollBottom();

            Base.log(1, "tap Delete Button", true);
            base.user.getDeleteButton().click();

            if (withCheckLocalizedText){
                if(!base.check.localizedTextFor.confirmLoader.hubDeleteFromMasterSettings()) return false;
            }
            base.nav.confirmIt();
            base.wait.invisibilityOfWaiter();

            return base.check.localizedTextFor.successMessage.hubDelete();
        }
    }
}
