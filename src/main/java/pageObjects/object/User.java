package pageObjects.object;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pageObjects.Base;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class User {

    @AndroidFindBy(id = "com.ajaxsystems:id/active")
    private WebElement active;

    @AndroidFindBy(id = "com.ajaxsystems:id/status")
    private WebElement userStatus;

    @AndroidFindBy(id = "com.ajaxsystems:id/invites")
    private WebElement inviteUsersField;

    @AndroidFindBy(id = "com.ajaxsystems:id/add")
    private WebElement addButtonFromContactList;

    @AndroidFindBy(id = "com.ajaxsystems:id/delete")
    private WebElement deleteButton;
    private String deleteButtonId = "com.ajaxsystems:id/delete";

    @AndroidFindBy(xpath = "//android.widget.TextView")
    private ArrayList<WebElement> allTextObjects;

    private ArrayList<String> usersForContactList, usersForEmailField, usersForMixedAdd;

    public ArrayList<String> getUsersForEmailField() {
        return usersForEmailField;
    }
    public ArrayList<String> getUsersForContactList() {
        return usersForContactList;
    }
    public ArrayList<String> getUsersForMixedAdd() {
        return usersForMixedAdd;
    }
    public WebElement getDeleteButton() {
        return deleteButton;
    }
    public WebElement getAddButtonFromContactList() {
        return addButtonFromContactList;
    }

//----------------------------------------------------------------------------------------------------------------------

    private Base base;
    private AndroidDriver driver;
    private boolean result;
    private String sendInvitesButtonText, inviteFailText, adminStatusText, userStatusText;

    public Registration registration;
    public Add add = new Add();
    public Delete delete =new Delete();

    public User(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        registration = new Registration();
        inviteFailText = base.getLocalizeTextForKey("invite_has_not_been_sent_to_following_emails");
        userStatusText = base.getLocalizeTextForKey("user");
        adminStatusText = base.getLocalizeTextForKey("admin");
        usersForMixedAdd = base.getJsonStringArray("emails.json", "usersForMixedAdd");
        usersForEmailField = base.getJsonStringArray("emails.json", "usersForEmailField");
        usersForContactList = base.getJsonStringArray("emails.json", "usersForContactList");
        sendInvitesButtonText = base.getLocalizeTextForKey("send_invites");

        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.TIMEOUT, TimeUnit.SECONDS), this);
    }
//----------------------------------------------------------------------------------------------------------------------

    public class Add {
        public boolean fromEmailField() {
            String emailListString = "";
            Base.log(1, "tap Send Invites Button", true);
            if (!base.nav.scroll.toElementWith.text(sendInvitesButtonText, true)) return false;
            Base.log(1, "concat all emails from array to the one string");
            for (String userEmail : usersForEmailField) {
                Base.log(1, "concat email \"" + userEmail + "\" to the string");
                emailListString = emailListString.concat(userEmail + " ");
            }
            Base.log(1, "send emails to the Users field", true);
            inviteUsersField.sendKeys(emailListString);
            return sendInvitation();
        }

        public boolean fromContactList() {
            Base.log(1, "tap Send Invites button", true);
            if (!base.nav.scroll.toElementWith.text(sendInvitesButtonText, true)) return false;

            Base.log(1, "tap Add From Contact List button", true);
            addButtonFromContactList.click();

            for (String userEmail : usersForContactList) {
                if(base.nav.scroll.toElementWith.email(userEmail, true)){
                    Base.log(1, "user with email \"" + userEmail + "\" is activated in the Contacts List", true);
                }else {
                    Base.log(3, "user with email \"" + userEmail + "\" is not activated in the Contacts List", true);
                    return false;
                }
            }

            Base.log(1, "tap Save button", true);
            base.nav.nextButtonClick();

            return sendInvitation();
        }

        public boolean fromDifferentWays() {
            String registeredUser = usersForMixedAdd.get(0);
            String unregisteredUser = usersForMixedAdd.get(1);
            String userForContactList = usersForMixedAdd.get(2);
            Base.log(1, "registered user: " + registeredUser, true);
            Base.log(1, "unregistered user: " + unregisteredUser, true);
            Base.log(1, "user from Contact list: " + registeredUser, true);

            Base.log(1, "tap Send Invites button", true);
            if (!base.nav.scroll.toElementWith.text(sendInvitesButtonText, true)) return false;

            Base.log(1, "fill email field with \"" + registeredUser + " " + unregisteredUser + "\"", true);
            inviteUsersField.sendKeys(registeredUser + " " + unregisteredUser);

            Base.log(1, "tap Add From Contact List button");
            addButtonFromContactList.click();

            if(base.nav.scroll.toElementWith.email(userForContactList, true)){
                Base.log(1, "user with email \"" + userForContactList + "\" is activated in the Contacts List", true);
            }else {
                Base.log(3, "user with email \"" + userForContactList + "\" is not activated in the Contacts List", true);
                return false;
            }

            Base.log(1, "tap Save button", true);
            base.nav.nextButtonClick();
           return sendInvitation();
        }

        private boolean sendInvitation(){
            Base.log(1, "tap Send Invitation button", true);
            base.nav.nextButtonClick();

            if (base.check.isPresent.snackBar(2)) return false;

            Base.log(1, "confirm proposition");
            base.nav.confirmIt();

            Base.log(1, "check is error snackBar present");
            if (base.check.isPresent.snackBar(2)) return false;

            base.wait.invisibilityOfWaiter();

            Base.log(1, "check is UserList opened");
            return base.wait.element(userStatus, 5, true);
        }
    }


    public class Delete {
        public boolean masterUser(){
           return deleteAllActive(adminStatusText);
        }

        public boolean allGuests(){
           return deleteAllActive(userStatusText);
        }

        public boolean allPending() {
            int counter = 0;
            String successText = base.getLocalizeTextForKey("Com_executed1");

            while (true) {
                Base.log(1, "tap User Delete button", true);
                if(base.nav.scroll.toElementWith.id(deleteButtonId, true)){
                    base.nav.confirmIt();
                    if (base.wait.elementWithText(successText, 40, true)){
                        Base.log(1, "SUCCESS text \"" + successText + "\" is shown", true);
                    } else {
                        Base.log(1, "SUCCESS text \"" + successText + "\" is not shown");
                    }
                    counter++;
                } else {
                    break;
                }
            }
            if (counter < 1){
                Base.log(3, "Test impossible, because precondition isn't valid - no one pending user was found", true);
                return false;
            }
            Base.log(1, "pending users are not found, number of deleted users: " + counter);
            return true;
        }

        private boolean deleteAllActive(String status) {
            String successText = null;
            int counter = 0;

            if (status.equalsIgnoreCase(userStatusText)){
                successText = base.getLocalizeTextForKey("user_guest_detach");
            }else if (status.equalsIgnoreCase(adminStatusText)){
                successText = base.getLocalizeTextForKey("Detach_success1");
            }else {
                Base.log(3,"localized text for User Status was not found", true);
                return false;
            }

            String statusXpath = "*[contains(@resource-id,'com.ajaxsystems:id/status') and @text='" + status + "']";
            String settingsButtonXpath = "*[@resource-id = 'com.ajaxsystems:id/settings']";
            String firstLinearLayout = "android.widget.LinearLayout[1]";
            String xPath = "//" + statusXpath + "/ancestor::" + firstLinearLayout + "//" + settingsButtonXpath;
            String emailXpath = "/ancestor::" + firstLinearLayout + "//*[@resource-id = 'com.ajaxsystems:id/mail']";

            try {
                WebElement settingsButton = driver.findElementByXPath(xPath);
                while (true) {
                    if(base.wait.element(settingsButton, 5, true)){
                        String email = driver.findElementByXPath(xPath + emailXpath).getText();

                        Base.log(1, "delete user with email \"" + email + "\"", true);
                        settingsButton.click();

                        Base.log(1, "tap delete button");
                        base.nav.scroll.toElementWith.id(deleteButtonId, true);
                        base.nav.confirmIt();
                        if (base.wait.elementWithText(successText, 40, true)){
                            Base.log(1, "SUCCESS text \"" + successText + "\" is shown", true);
                        } else {
                            Base.log(1, "SUCCESS text \"" + successText + "\" is not shown");
                        }
                        counter++;
                    } else {break;}
                }
            }catch (NoSuchElementException e){
                Base.log(1, "NoSuchElementException: \n\n" + e + "\n");
            }
            if (counter < 1){
                Base.log(3, "Test impossible, because precondition isn't valid - no one user with status \"" + status + "\" are not found", true);
                return false;
            }
            Base.log(1, "active users with status \"" + status + "\" are not found, number of deleted users: " + counter);
            return true;
        }
    }

//----------------------------------------------------------------------------------------------------------------------

    //  TODO registration withMistakeInEmail
    //  TODO registration withMistakeInPhone
    //  TODO registration withMistakeInPhoneAndEmail
    //  TODO registration withCredsFromExistingUser
    //  TODO registration withResendValidationKeys

    public class Registration{
        String login = base.getCredsWithKey("login");
        String pass = base.getCredsWithKey("password");
        String server = base.getCredsWithKey("server");
        String phone = base.getCredsWithKey("phone");
        String userName = base.getCredsWithKey("userName");
        String smsToken, emailToken;

        public boolean fullProcess() {
            if (!base.regPage.registrationProcess(login, pass, server, phone, userName)) return false;
            Base.log(1, "error message is not shown");
            base.validationCodePage.getValidationCodes("Phone", "%" + phone + "%");

            smsToken = base.validationCodePage.getTokenMap().get("smsToken").toString();
            emailToken = base.validationCodePage.getTokenMap().get("emailToken").toString();

            base.validationCodePage.fillTokensValue(smsToken, emailToken);
            base.nav.confirmIt();

            Base.log(1, "waiting for Welcome Page with dashboard link");
            if (!base.wait.element(base.regPage.getDashboardLink(), 30, true)) return false;

            Base.log(1, "Welcome Page is shown, so go to the dashboard", true);
            base.regPage.dashboardLinkClick();
            return base.wait.menuIconOrPinPopUp(60);
        }

        public boolean withMistakeInEmail(){return true;}

        public boolean withMistakeInPhone(){return true;}

        public boolean withMistakeInPhoneAndEmail(){return true;}

        public boolean withCredsFromExistingUser(){return true;}

        public boolean withResendValidationKeys(){return true;}

    }

//----------------------------------------------------------------------------------------------------------------------
//
//    public boolean checkIsDeleteIconPresent(String pendingUserName) {
//        String nameElementXpath = "//*[contains(@resource-id,'com.ajaxsystems:id/name') and @text='" + pendingUserName + "']";
//        String deleteElementXpath = "/ancestor::android.widget.LinearLayout[1]//*[@resource-id = 'com.ajaxsystems:id/delete']";
//
//        if (base.nav.scroll.toElementWith.name(pendingUserName, false)) {
//
//            try {
//                base.wait.element(driver.findElementByXPath(nameElementXpath + deleteElementXpath), 5, true);
//                Base.log(1, "new user with email \"" + pendingUserName + "\" has the DELETE button");
//                result = true;
//
//            }catch (Exception e){
//                Base.log(3, "new user with email \"" + pendingUserName + "\" has no the DELETE button");
//                result = false;
//            }
//        }
//        return result;
//    }

//    public boolean isDeleteIconPresent(String pendingUserName) {
//        WebElement pendingUserForCheck = findPendingFrom("name", pendingUserName);
//
//        if (base.nav.scrollToElement(pendingUserForCheck, "up")) {
//            Base.log(1, "new user with email \"" + pendingUserName + "\" has the DELETE button");
//            result = true;
//
//        } else {
//            Base.log(3, "new user with email \"" + pendingUserName + "\" has no the DELETE button");
//            result = false;
//        }
//        return result;
//    }

//    private WebElement findPendingFrom(String from, String pendingUserName){
//        String nameElementXpath = "*[contains(@resource-id,'com.ajaxsystems:id/name') and @text='" + pendingUserName + "']";
//        String deleteElementXpath = "*[@resource-id = 'com.ajaxsystems:id/delete']";
//        String firstLinearLayout = "android.widget.LinearLayout[1]";
//        String xPath = null;
//        switch (from){
//            case "delete": xPath = "//" + deleteElementXpath + "/ancestor::" + firstLinearLayout + "//" + nameElementXpath;
//                break;
//            case "name": xPath = "//" + nameElementXpath + "/ancestor::" + firstLinearLayout + "//" + deleteElementXpath;
//                break;
//            default: Base.log(3, "invalid parameter");
//                break;
//        }
//        WebElement pendingUserElement = driver.findElementByXPath(xPath);
////        base.nav.scrollToElement(pendingUserElement, "up");
//        return pendingUserElement;
//    }

}