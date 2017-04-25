package pages;

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
            base.nav.confirmIt();
            Base.log(4, "Method is finished");
        }

        public void disarm() {
            Base.log(4, "Method is started");
            base.nav.gotoPage.Remote();
            base.remotePage.clickDisarmButton();
            Base.log(4, "Method is finished");
        }

        public void partialArm() {
            Base.log(4, "Method is started");
            base.nav.gotoPage.Remote();

            base.remotePage.clickPartialArmButton();
            base.nav.confirmIt();
            Base.log(4, "Method is finished");
        }

        public void alarm(){
            Base.log(4, "Method is started");

            Base.log(1, "go to Remote page");
            base.nav.gotoPage.Remote();

            base.remotePage.clickAlarmButton();

            base.wait.element(base.remotePage.getSpaceControlImage(), 10, true);
            Base.log(4, "Method is finished");
        }
    }

    public void goToTheUserInvitationPage() {
        Base.log(4, "Method is started");
        sendInvitesButtonText = base.getLocalizeTextForKey("send_invites");

        Base.log(1, "click on hub");
        hubImageOnDeviceList.click();

        Base.log(1, "click Hub Settings button");
        settingsButton.click();

        Base.log(1, "click Users tab");
        hubSettingsUsersImage.click();

        base.wait.element(userStatus, 10, true);
        base.nav.scrollToElementWith("text", "up", sendInvitesButtonText, true);
        Base.log(4, "Method is finished");
    }

    public void goToTheUserlistPage() {
        Base.log(4, "Method is started");

        Base.log(1, "click on Hub tab");
        hubImageOnDeviceList.click();

        Base.log(1, "click Hub Settings button");
        settingsButton.click();

        Base.log(1, "click Users tab");
        hubSettingsUsersImage.click();

        base.wait.element(userStatus, 10, true);
        Base.log(4, "Method is finished");
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

        base.wait.invisibilityOfWaiter(true);
        Assert.assertFalse(base.check.isErrorPresent(2), "Hub adding failed!");

        Assert.assertTrue(base.wait.element(base.dashboardHeader.getGprsImage(), 10, true));
        Base.log(1, "hub successfully added!");
        Base.log(4, "Method is finished");
    }

    public class DeleteFrom {

        public boolean hubSettings(boolean withCheckLocalizedText) {
            return deleteFrom("hub", withCheckLocalizedText);
        }

        public boolean masterUserSettings(boolean withCheckLocalizedText) {
            return deleteFrom("master", withCheckLocalizedText);
        }

        private boolean deleteFrom(String pageName, boolean withCheckLocalizedText) {
            String expectedText;
            String actualText;

            switch (pageName){
                case "hub":
                    base.nav.gotoPage.hubSettings();
                    base.nav.scrollBottom();
                    unpairButton.click();

                    if (withCheckLocalizedText){
                        Base.log(1, "get expected and actual localized text");
                        actualText = base.popUp.getContentText().replaceAll("(\").*(\")", "");
                        expectedText = base.getLocalizeTextForKey("remove_hub_from_this_account").replaceAll("(\").*(\")", "");

                        System.out.println("actual: " + actualText);
                        System.out.println("expected: " + expectedText);

                        Base.log(1, "checking of localized text");
                        Assert.assertEquals(expectedText, actualText, "expected text is not equals actual");

                        Base.log(1, "checking of localized text is successfully passed");
                    }

                    base.nav.confirmIt();
                    base.wait.invisibilityOfWaiter();

                    if (withCheckLocalizedText){
                        base.check.localizedTextFor.hubUnpair();
                    }
                    break;

                case "master":
                    base.nav.gotoPage.userList();
                    base.hub.settingsButton.click();
                    base.nav.scrollBottom();
                    base.user.getDeleteButton().click();

                    if (withCheckLocalizedText){
                        Base.log(1, "get expected and actual localized text");
                        actualText = base.popUp.getContentText();
                        expectedText = base.getLocalizeTextForKey("you_are_about_to_revoke_hub_access_for_user_are_you_sure");

                        System.out.println("actual: " + actualText);
                        System.out.println("expected: " + expectedText);

                        Base.log(1, "checking of localized text");
                        Assert.assertEquals(expectedText, actualText, "expected text is not equals actual");

                        Base.log(1, "checking of localized text is successfully passed");
                    }

                    base.nav.confirmIt();
                    base.wait.invisibilityOfWaiter();

                    if (withCheckLocalizedText){
                        base.check.localizedTextFor.hubUnpair();
                    }
                    break;
            }
            return true;
        }
    }
}
