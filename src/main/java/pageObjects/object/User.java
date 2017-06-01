package pageObjects.object;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pageObjects.Base;

import java.util.ArrayList;
import java.util.Map;
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

//----------------------------------------------------------------------------------------------------------------------

    public ArrayList<String> getUsersForEmailField() {
        return usersForEmailField;
    }
    public ArrayList<String> getUsersForContactList() {
        return usersForContactList;
    }
    public ArrayList<String> getUsersForMixedAdd() {
        return usersForMixedAdd;
    }
    public WebElement getAddButtonFromContactList() {
        return addButtonFromContactList;
    }
    public WebElement getDeleteButton() {
        return deleteButton;
    }

//----------------------------------------------------------------------------------------------------------------------

    private Base base;
    private AndroidDriver driver;

    private String sendInvitesButtonText, inviteFailText, adminStatusText, userStatusText;
    private ArrayList<String> usersForContactList, usersForEmailField, usersForMixedAdd;

    public Add add;
    public Delete delete;
    public Registration registration;

    public User(Base base) {
        this.base = base;
        this.driver = base.getDriver();

        add = new Add();
        delete = new Delete();
        registration = new Registration();
        inviteFailText = base.getLocalizeTextForKey("invite_has_not_been_sent_to_following_emails");
        userStatusText = base.getLocalizeTextForKey("user");
        adminStatusText = base.getLocalizeTextForKey("admin");
        usersForMixedAdd = base.getJsonStringArray("emails.json", "usersForMixedAdd");
        usersForEmailField = base.getJsonStringArray("emails.json", "usersForEmailField");
        usersForContactList = base.getJsonStringArray("emails.json", "usersForContactList");
        sendInvitesButtonText = base.getLocalizeTextForKey("send_invites");
        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.DEFAULT_TIMEOUT, TimeUnit.SECONDS), this);
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

            base.nav.tapButton.save();

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
            base.nav.tapButton.save();
           return sendInvitation();
        }

        private boolean sendInvitation(){
            base.nav.tapButton.sendInvitation();
            if (base.check.isPresent.snackBar(2)) {return false;}

            Base.log(1, "confirm proposition");
            base.nav.confirmIt();

            Base.log(1, "check is error snackBar present");
            if (base.check.isPresent.snackBar(2)) {return false;}
            base.wait.invisibilityOfWaiter();

            Base.log(1, "check is UserList opened");
            return base.wait.element(userStatus, 5, true);
        }
    }

//----------------------------------------------------------------------------------------------------------------------

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
    public class Registration{
        private String pass, login, loginConfirm, passConfirm, phone, server, country, userName;
        private Map registrationData = base.getJsonMapCollection("registrationData.json", base.getDeviceName());
        private Map dataWithMistake = base.getJsonMapCollection("registrationData.json", "dataWithMistake");
        private Map existingUser = base.getJsonMapCollection("registrationData.json", "existingUser");

        public WithFake withFake = new WithFake();
        public WithExisting withExisting = new WithExisting();

        private void initRegistrationData(){
            pass = registrationData.get("password").toString();
            login = registrationData.get("login").toString();
            phone = registrationData.get("phone").toString();
            server = registrationData.get("server").toString();
            country = registrationData.get("country").toString();
            userName = registrationData.get("userName").toString();
            passConfirm = registrationData.get("passwordConfirm").toString();
            loginConfirm = registrationData.get("loginConfirm").toString();
        }

        public boolean validateWithExistingCodes(){
            initRegistrationData();
            Base.log(1, "delete user if phone already exist at the server");
            base.sql.getDelete("Phone", "%" + phone + "%");

            if (!base.regPage.registrationProcess(login, pass, loginConfirm, passConfirm, server, phone, country, userName, "", false, true)) {return false;}
            resendFromLoginPage(login, false);
            base.validationPage.validateBy.phone(phone);
            return base.wait.menuIconOrPinPopUp(60);
        }

        public boolean fullProcess(boolean repeatAfterCancel) {
            initRegistrationData();
            Base.log(1, "delete user if phone already exist at the server");
            base.sql.getDelete("Phone", "%" + phone + "%");

            if (!base.regPage.registrationProcess(login, pass, loginConfirm, passConfirm, server, phone, country, userName, "", true, true)) return false;
            Base.log(1, "error message is not shown");

            if (repeatAfterCancel){
                base.nav.cancelIt();
                Base.log(1, "repeat registration with the same data", true);
                if (!base.regPage.registrationProcess(login, pass, loginConfirm, passConfirm, server, phone, country, userName, "", true, true)) return false;
                Base.log(1, "error message is not shown");
            }
            base.validationPage.validateBy.phone(phone);
            return registrationResult();
        }

        public boolean withData(String login, String pass, String loginConfirm, String passConfirm, String server, String phone, String country, String userName,  String errorMessage, boolean setUserPic, boolean confirmAgreement) {
            if (!base.regPage.registrationProcess(login, pass, loginConfirm, passConfirm, server, phone, country, userName, errorMessage, setUserPic, confirmAgreement)) return false;
            Base.log(1, "error message is not shown");
            base.validationPage.validateBy.phone(phone);
            return registrationResult();
        }

        public boolean withSpecificEmail (String specificEmail, String errorMessage){
            initRegistrationData();
            Base.log(1, "delete users if they already exist at the server");
            base.sql.getDelete("Phone", "%" + phone + "%");
            base.sql.getDelete("Login","%" + specificEmail + "%");

            if (!base.regPage.registrationProcess(specificEmail, pass, specificEmail, passConfirm, server, phone, country, userName, errorMessage, true, true)) return false;
            Base.log(1, "error message is not shown");
            base.validationPage.validateBy.phone(phone);
            return registrationResult();
        }

        public class WithFake {
            String fakeEmail = dataWithMistake.get("login").toString();
            String fakePhone = dataWithMistake.get("phone").toString();
            String fakeCountry = dataWithMistake.get("country").toString();
            String errorMessage = base.getLocalizeTextForKey("User_reg_not_found0");

            public boolean email(boolean resendFromLoginPage, boolean checkIsLoginPresentInField){
                initRegistrationData();
                return withMistakeIn("email", resendFromLoginPage, checkIsLoginPresentInField);
            }

            public boolean phone(boolean resendFromLoginPage, boolean checkIsLoginPresentInField){
                initRegistrationData();
                return withMistakeIn("phone", resendFromLoginPage, checkIsLoginPresentInField);
            }

            public boolean both(boolean resendFromLoginPage, boolean checkIsLoginPresentInField){
                initRegistrationData();
                return withMistakeIn("both", resendFromLoginPage, checkIsLoginPresentInField);
            }

            private boolean withMistakeIn(String whereIsMistake, boolean resendFromLoginPage, boolean checkIsLoginPresentInField) {
                Base.log(1, "delete user if users already exist at the server");
                base.sql.getDelete("Phone", "%" + phone + "%");
                base.sql.getDelete("Phone", "%" + fakePhone + "%");
                base.sql.getDelete("Login", "%" + fakeEmail + "%");

                switch (whereIsMistake) {
                    case "phone":
                        if (!base.regPage.registrationProcess(login, pass, loginConfirm, passConfirm, server, fakePhone, fakeCountry, userName, "", false, true)) {return false;}
                        if (resendFromLoginPage){ if (!resendFromLoginPage(login, checkIsLoginPresentInField)){return false;}}
                        break;

                    case "email":
                        if (!base.regPage.registrationProcess(fakeEmail, pass, fakeEmail, passConfirm, server, phone, country, userName, "", false, true)) {return false;}
                        if (resendFromLoginPage){if (!resendFromLoginPage(fakeEmail, checkIsLoginPresentInField)){return false;}}
                        break;

                    case "both":
                        if (!base.regPage.registrationProcess(fakeEmail, pass, fakeEmail, passConfirm, server, fakePhone, fakeCountry, userName, "", false, true)) {return true;}
                        if (resendFromLoginPage){if (!resendFromLoginPage(fakeEmail, checkIsLoginPresentInField)){return true;}}
                        return base.validationPage.resendCode(login, phone, country, errorMessage);

                    default:
                        Base.log(3, "wrong data, expected \"phone\", \"email\" or \"both\"");
                        return false;
                }

                if (!base.validationPage.resendCode(login, phone, country, "")) return false;

                Base.log(1, "error message is not shown");
                base.validationPage.validateBy.phone(phone);
                if (resendFromLoginPage) {return base.wait.menuIconOrPinPopUp(60);}
                else {return registrationResult();}
            }
        }

        public class WithExisting{
            public boolean email(boolean resendFromLoginPage){
                initRegistrationData();
                return withDataFromExistingUser("email", resendFromLoginPage);
            }

            public boolean phone(boolean resendFromLoginPage){
                initRegistrationData();
                return withDataFromExistingUser("phone", resendFromLoginPage);
            }

            public boolean both(boolean resendFromLoginPage){
                initRegistrationData();
                return withDataFromExistingUser("both", resendFromLoginPage);
            }

            private boolean withDataFromExistingUser(String whichExistingDataUse, boolean resendFromLoginPage){
                initRegistrationData();
                String errorMessage = base.getLocalizeTextForKey("User_reg_phone_or_mail_exist0");
                String existingUserPhone = existingUser.get("phone").toString();
                String existingUserCountry = existingUser.get("country").toString();
                String existingUserLogin = existingUser.get("login").toString();

                Base.log(1, "delete users if phone already exist at the server");
                base.sql.getDelete("Phone", "%" + phone + "%");

                switch (whichExistingDataUse) {
                    case "phone":
                        if (resendFromLoginPage) {
                            return resendWithDataFromExistingUser(login, existingUserPhone, existingUserCountry, errorMessage);
                        } else {
                            return base.regPage.registrationProcess(login, pass, loginConfirm, passConfirm, server, existingUserPhone, existingUserCountry, userName, errorMessage, false, true);
                        }

                    case "email":
                        if (resendFromLoginPage) {
                            return resendWithDataFromExistingUser(existingUserLogin, phone, country, errorMessage);
                        } else {
                            return base.regPage.registrationProcess(existingUserLogin, pass, existingUserLogin, passConfirm, server, phone, country, userName, errorMessage, false, true);
                        }

                    case "both":
                        if (resendFromLoginPage) {
                            return resendWithDataFromExistingUser(existingUserLogin, existingUserPhone, existingUserCountry, errorMessage);
                        } else {
                            return base.regPage.registrationProcess(existingUserLogin, pass, existingUserLogin, passConfirm, server, existingUserPhone, existingUserCountry, userName, errorMessage, false, true);
                        }

                    default:
                        Base.log(3, "wrong data for using, expected \"phone\", \"email\" or \"both\"");
                        return false;
                }
            }
        }

        //--------------------------------------------------------------------------------------------------------------
        // Helpers
        //--------------------------------------------------------------------------------------------------------------

        private boolean resendWithDataFromExistingUser(String resendEmail, String resendPhone, String resendCountry, String errorMessage){
            base.regPage.registrationProcess(login, pass, loginConfirm, passConfirm, server, phone, country, userName, "", false, true);

            if (!resendFromLoginPage(login, true)){return true;}
            return base.validationPage.resendCode(resendEmail, resendPhone, resendCountry, errorMessage);
        }

        private boolean registrationResult(){
            Base.log(1, "waiting for Welcome Page with dashboard link");
            if (!base.wait.element(base.regPage.getDashboardLink(), 30, true)) return false;

            Base.log(1, "Welcome Page is shown, so go to the dashboard", true);
            base.regPage.dashboardLinkClick();
            if (base.check.isPresent.snackBar(3)) {return false;}
            return base.wait.menuIconOrPinPopUp(60);
        }

        private boolean resendFromLoginPage(String resendLogin, boolean checkIsLoginPresentInField){
            base.nav.cancelIt();

            if (checkIsLoginPresentInField){
                if (!base.check.isPresent.loginInEmailField(login)){return false;}
            }

            base.loginPage.loginToTheServer(resendLogin, pass);
            base.wait.invisibilityOfLoaderLogo(true);

            if (!base.wait.text(base.getLocalizeTextForKey("this_account_was_not_yet_validated"), 5, true)) {return false;}
            base.nav.confirmIt();
            return true;
        }
    }
//----------------------------------------------------------------------------------------------------------------------


}