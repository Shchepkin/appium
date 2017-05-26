package pageObjects.object;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pageObjects.Base;

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
    private Base base;
    private AndroidDriver driver;
    private String sendInvitesButtonText,armedText, disarmedText, patrialArmedText, hubName, hubMasterKey;
    public Add add = new Add();
    public Security security = new Security();
    public DeleteFrom deleteFrom = new DeleteFrom();

    public Hub(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        armedText = base.getLocalizeTextForKey("armed");
        disarmedText = base.getLocalizeTextForKey("disarmed");
        patrialArmedText = base.getLocalizeTextForKey("partially_armed");
        hubName = base.getCredsWithKey("hubName");
        hubMasterKey = base.getCredsWithKey("hubMasterKey");
        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.DEFAULT_TIMEOUT, TimeUnit.SECONDS), this);
    }
//----------------------------------------------------------------------------------------------------------------------

    public class Add {
        public Manual manual = new Manual();

        public class Manual {
            public boolean fromMenu(){
                startAddingWithMenu();
                return manualType(hubName, hubMasterKey);
            }
            public boolean fromPlusButton(){
                startAddingWithPlus();
                return manualType(hubName, hubMasterKey);
            }
            public boolean fromAnyWay(){
                startAddingWithAnyWay();
                return manualType(hubName, hubMasterKey);
            }
            public boolean withNewCreds(String newHubName, String newHubMasterKey){
                addFrom("any", "manual", true, newHubName, newHubMasterKey);
                return true;
            }
        }

        private void startAddingWithAnyWay(){
            if (base.wait.element(base.header.getMenuDrawer(), 2, true)){
                if (base.wait.element(base.dashboard.getPlusButton(), 2, true)){
                    startAddingWithPlus();
                }else {
                    startAddingWithMenu();
                }
            }
        }

        private void startAddingWithPlus(){
            Base.log(1, "add Hub by Plus Button", true);
            base.dashboard.plusButtonClick();
        }

        private void startAddingWithMenu(){
            Base.log(1, "add Hub from Main Menu", true);

            Base.log(1, "tap Main Menu icon");
            base.header.getMenuDrawer().click();

            Base.log(1, "tap Add Hub button");
            base.menuPage.addHubButtonClick();
        }

        private boolean manualType(String hubName, String hubMasterKey){
            Base.log(1, "choose manual Hub adding", true);
            base.nav.nextButtonClick();

            base.dashboard.fillFieldsWith(hubName, hubMasterKey);
            base.nav.confirmIt();

            Base.log(1, "check whether error message is shown");
            if (base.check.isPresent.error(40)){return false;}
            base.wait.pinPopUp(2, false);

            if (base.wait.element(base.header.getGprsImage(), 10, true)){
                Base.log(1, "hub successfully added!");
                return true;
            }else return false;
        }

        private boolean wizardType(String hubName, String hubMasterKey){return true;}

        private boolean addFrom(String from, String type, boolean newCreds, String newHubName, String newHubMasterKey) {
            Base.log(1, "get creds for hubName and hubMasterKey");
            if (newCreds){
                hubName = newHubName;
                hubMasterKey = newHubMasterKey;
            }

            switch (from){
                case "menu" : startAddingWithMenu(); break;
                case "plus" : startAddingWithPlus(); break;
                case "any" : startAddingWithAnyWay(); break;
                default: startAddingWithAnyWay(); break;
            }

            switch (type){
                case "manual" : return manualType(hubName, hubMasterKey);
                case "wizard" : return wizardType(hubName, hubMasterKey);
                default: return manualType(hubName, hubMasterKey);
            }
        }
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

    public class DeleteFrom {
        public boolean hubSettings(boolean withCheckLocalizedText) {
            Base.log(1, "go to the hubSettings", true);
            base.nav.gotoPage.hubSettings();
//            base.nav.scrollBottom();

            Base.log(1, "tap Delete button", true);
            unpairButton.click();

            if (withCheckLocalizedText){
                if(!base.check.localizedTextFor.confirmLoader.hubDeleteFromHubSettings()) return false;
            }
            Base.log(1, "Confirm proposition", true);
            base.nav.confirmIt();
            String successMessage = base.getLocalizeTextForKey("Detach_success1");
            return base.wait.elementWithText(successMessage, 15, true);
        }
        public boolean masterUserSettings(boolean withCheckLocalizedText) {
            Base.log(1, "go to the userList", true);
            base.nav.gotoPage.userList();

            Base.log(1, "tap first settings button in the UserList", true);
            settingsButton.click();

//            base.nav.scrollBottom();

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
