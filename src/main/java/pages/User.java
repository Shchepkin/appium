package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import java.util.ArrayList;

public class User{

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

    @AndroidFindBy(xpath = "//android.widget.TextView")
    private ArrayList<WebElement> allTextObjects;

    private ArrayList<String> usersForContactList;
    private ArrayList<String> usersForEmailField;
    private ArrayList<String> usersForMixedAdd;

    public ArrayList<String> getUsersForEmailField() {
        return usersForEmailField;
    }
    public ArrayList<String> getUsersForContactList() {
        return usersForContactList;
    }
    public ArrayList<String> getUsersForMixedAdd() {
        return usersForMixedAdd;
    }

//----------------------------------------------------------------------------------------------------------------------

    private final Base base;
    private final AppiumDriver driver;
    private boolean result;

    private String sendInvitesButtonText, inviteFailText, adminStatusText, userStatusText;

    public User(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        sendInvitesButtonText = base.getLocalizeTextForKey("send_invites");
        inviteFailText = base.getLocalizeTextForKey("invite_has_not_been_sent_to_following_emails");
        adminStatusText = base.getLocalizeTextForKey("admin");
        userStatusText = base.getLocalizeTextForKey("user");

        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
//----------------------------------------------------------------------------------------------------------------------

    public void fillUserEmailsField(String inviteEmail) {
        inviteUsersField.sendKeys(inviteEmail);
        base.nav.nextButtonClick();
    }

//----------------------------------------------------------------------------------------------------------------------

    public void addFromEmailField(String userEmail) {
        Base.log("Method is started");
        result = false;

        base.nav.scrollToElementWith("text", "up", sendInvitesButtonText, true);

        Base.log("fill email field with \"" + userEmail + "\"");
        inviteUsersField.sendKeys(userEmail);

        Base.log("click add button and confirm proposition");
        base.check.clickElementAndWaitingPopup(base.nav.getNextButton(), true);

        Base.log("click add button and confirm proposition");
        base.check.clickElementAndWaitingPopup(base.nav.getNextButton(), true);

        Assert.assertFalse(base.check.forSnackBarIsPresent(3), "SnackBar is shown with error text \n");
        base.wait.invisibilityOfWaiter(true);
        Assert.assertTrue(base.wait.element(userStatus, 10, true), "User page is not shown \n");

        Base.log("Method is finished");
    }

//----------------------------------------------------------------------------------------------------------------------

    public void addFromContactList(String userEmail) {
        Base.log("Method is started");
        result = false;

        Base.log("searching and clicking the Send Invites Button");
        base.nav.scrollToElementWith("text", "up", sendInvitesButtonText, true);

        Base.log("click the Add From Contact List Button");
        addButtonFromContactList.click();

        base.nav.scrollToElementWith("email", "up", userEmail, true);
        base.nav.nextButtonClick();

        Base.log("click add button and confirm proposition");
        base.check.clickElementAndWaitingPopup(base.nav.getNextButton(), true);

        Assert.assertFalse(base.check.forSnackBarIsPresent(3), "SnackBar is shown with error text \n");
        base.wait.invisibilityOfWaiter(true);
        Assert.assertTrue(base.wait.element(userStatus, 10, true), "User page is not shown \n");

        Base.log("Method is finished");
    }

//----------------------------------------------------------------------------------------------------------------------

    public boolean addFromEmailField() {
        Base.log("Method is started");
        result = false;
        String emailListString = "";
        int counter = 0;

        Base.log("click send Invites Button");
        base.nav.scrollToElementWith("text", "up", sendInvitesButtonText, true);

        Base.log("concat all emails from array to the one string");
        for (String userEmail : usersForEmailField) {
            Base.log("concat email \"" + userEmail + "\" to the string");
            emailListString = emailListString.concat(userEmail + " ");
        }

        Base.log("send emailList String to the Users Field");
        inviteUsersField.sendKeys(emailListString);

        Base.log("click Add button");
        base.nav.nextButtonClick();
        if(!base.check.forSnackBarIsPresent(3)) {

            Base.log("confirm proposition");
            base.nav.confirmIt();
            base.wait.invisibilityOfWaiter();

            base.check.isElementDisplayed(userStatus, 15);

            Base.log("check whether new user is added");
            for (String userEmail : usersForEmailField) {
                if (base.nav.scrollToElementWith("text", "up", userEmail, false)) {
                    counter++;
                }
            }

            if (counter == usersForEmailField.size()) {
                Base.log("all new user is shown in the userList");
                result = true;
            }else {
                result = false;
            }
        }else {
            result = false;
        }
        Base.log("Method is finished");
        return result;
    }

//----------------------------------------------------------------------------------------------------------------------

    public void addUserListFromEmailField(String nameOfJsonCollection) {
        Base.log("Method is started");
        Base.log(3, "sendInvitesButtonText: \"" + sendInvitesButtonText + "\"");
        ArrayList<String> userListFromJson = base.getJsonStringArray("emails.json", nameOfJsonCollection);
        String emailListString = "";

        Base.log("click send Invites Button");
        base.nav.scrollToElementWith("text", "up", sendInvitesButtonText, true);

        Base.log("concat all emails from array to the one string");
        for (String userEmail : userListFromJson) {
            Base.log("concat email \"" + userEmail + "\" to the string");
            emailListString = emailListString.concat(userEmail + " ");
        }

        Base.log("send emailList String to the Users Field");
        inviteUsersField.sendKeys(emailListString);

        Base.log("click Add button");
        base.nav.nextButtonClick();

        Assert.assertFalse(base.check.forSnackBarIsPresent(3), "SnackBar is shown with error text \n");
        base.wait.invisibilityOfWaiter(true);
        Assert.assertTrue(base.wait.element(userStatus, 15, true), "User page is not shown \n");

        Base.log("Method is finished");
    }

//----------------------------------------------------------------------------------------------------------------------

    public void addFromContactList() {
        Base.log("Method is started");
        Base.log(3, "sendInvitesButtonText: \"" + sendInvitesButtonText + "\"");
        result = false;

        Base.log("searching and clicking the Send Invites Button");
        base.nav.scrollToElementWith("text", "up", sendInvitesButtonText, true);

        Base.log("click the Add From Contact List Button");
        addButtonFromContactList.click();

        Base.log("start Add user process");
        int firstTime = 0;
        for (String userEmail : usersForContactList) {

            if(firstTime > 0){
                Base.log("scrolling to the start of list");
                base.nav.scrollTop();
            }
            firstTime++;

            Base.log("activate user with email \"" + userEmail + "\" in the Contacts List");
            activateUserInContactListBy("email", userEmail);
        }

        Base.log("click Save button");
        base.nav.nextButtonClick();

        Base.log("click add button and confirm proposition");
        base.check.clickElementAndWaitingPopup(base.nav.getNextButton(), true);

        Assert.assertFalse(base.check.forSnackBarIsPresent(3), "SnackBar is shown with error text \n");
        base.wait.invisibilityOfWaiter();
        Assert.assertTrue(base.wait.element(userStatus, 10, true), "User page is not shown \n");

        Base.log("Method is finished");
    }

//----------------------------------------------------------------------------------------------------------------------

    private boolean activateUserInContactListBy(String typeBy, String userEmail) {
        String emailElementXpath = "//*[contains(@resource-id,'com.ajaxsystems:id/mail') and @text='" + userEmail + "']";
        String activeElementXpath = "/ancestor::android.widget.FrameLayout[1]//*[@resource-id = 'com.ajaxsystems:id/active']";

        for (int i = 1; i < 3; i++) {
            if (base.nav.scrollToElementWith(typeBy, "up", userEmail, true)) {
                base.check.forSnackBarIsPresent(2);
                try {
                    base.wait.element(driver.findElement(By.xpath(emailElementXpath + activeElementXpath)), 5, true);
                    Base.log("element \"" + userEmail + "\" is activated successfully");
                    result = true;
                    break;

                } catch (NoSuchElementException e) {
                    Base.log(3, "activation element \"" + userEmail + "\" is failed, try count " + i);
                    Base.log(3, "stacktrace: \n" + e + "\n");
                    result = false;
                }
            }
        }
        return result;
    }

//----------------------------------------------------------------------------------------------------------------------

    public void addMixedUsers() {
        Base.log("Method is started");

        String registeredUser = usersForMixedAdd.get(0);
        String unregisteredUser = usersForMixedAdd.get(1);
        String userForContactList = usersForMixedAdd.get(2);

        base.nav.scrollToElementWith("text", "up", sendInvitesButtonText, true);

        Base.log("fill email field with \"" + registeredUser + " " + unregisteredUser + "\"");
        inviteUsersField.sendKeys(registeredUser + " " + unregisteredUser);

        Base.log("click the Add From Contact List Button");
        addButtonFromContactList.click();

        Base.log("activate user with email \"" + userForContactList + "\" in the Contacts List");
        activateUserInContactListBy("email", userForContactList);

        Base.log("click Save button");
        base.nav.nextButtonClick();

        Base.log("click add button and confirm proposition");
        base.check.clickElementAndWaitingPopup(base.nav.getNextButton(), true);

        Assert.assertFalse(base.check.forSnackBarIsPresent(3), "SnackBar is shown with error text \n");
        base.wait.invisibilityOfWaiter(true);
        Assert.assertTrue(base.wait.element(userStatus, 10, true), "User page is not shown \n");

        Base.log("Method is finished");
    }

//----------------------------------------------------------------------------------------------------------------------

    /**
     * @param byType    - name, email, text
     * @param userList
     * @return
     */

    public boolean checkIsNewUsersAddedBy(String byType, ArrayList<String> userList) {
        result = false;
        int counter = 0;
        int firstTime = 0;

        for (String userEmail : userList) {

            if(firstTime > 0){
                Base.log("scrolling to the start of list");
                base.nav.scrollTop();
            }
            firstTime++;

            if (base.nav.scrollToElementWith(byType, "up", userEmail, false)) {
                Base.log("new user with email \"" + userEmail + "\" is added successfully");
                counter++;
            }
        }

        Base.log(3, "added " + counter + " new users from " + userList.size());
        if (counter == userList.size()) {
            Base.log("all users are added");
            result = true;
        } else {
            Base.log(3, "not all users are added");
            result = false;
        }
        return result;
    }

//----------------------------------------------------------------------------------------------------------------------

    public boolean checkIsDeleteIconPresent(String pendingUserName) {
        String nameElementXpath = "//*[contains(@resource-id,'com.ajaxsystems:id/name') and @text='" + pendingUserName + "']";
        String deleteElementXpath = "/ancestor::android.widget.LinearLayout[1]//*[@resource-id = 'com.ajaxsystems:id/delete']";

        if (base.nav.scrollToElementWith("name", "up", pendingUserName, false)) {

            try {
                base.wait.element(driver.findElementByXPath(nameElementXpath + deleteElementXpath), 5, true);
                Base.log("new user with email \"" + pendingUserName + "\" has the DELETE button");
                result = true;

            }catch (Exception e){
                Base.log(3, "new user with email \"" + pendingUserName + "\" has no the DELETE button");
                result = false;
            }
        }
        return result;
    }

//----------------------------------------------------------------------------------------------------------------------

    public boolean isDeleteIconPresent(String pendingUserName) {
        WebElement pendingUserForCheck = findPendingFrom("name", pendingUserName);

        if (base.nav.scrollToElement(pendingUserForCheck, "up")) {
            Base.log("new user with email \"" + pendingUserName + "\" has the DELETE button");
            result = true;

        } else {
            Base.log(3, "new user with email \"" + pendingUserName + "\" has no the DELETE button");
            result = false;
        }
        return result;
    }

//----------------------------------------------------------------------------------------------------------------------

    public void deletePendingByName(String pendingUserName) {
        WebElement pendingUserForDelete = findPendingFrom("name", pendingUserName);
        base.nav.scrollToElement(pendingUserForDelete, "up");
        pendingUserForDelete.click();
    }

//----------------------------------------------------------------------------------------------------------------------

    public boolean deleteAllPending() {
        result = false;
        int counter = 0;
        String successText = base.getLocalizeTextForKey("Com_executed1");
        while (true) {

            if(base.nav.scrollToElement(deleteButton, "up")){

                Base.log("tap User Delete button and confirm popUp proposition");
                deleteButton.click();
                base.nav.confirmIt();

                base.wait.invisibilityOfWaiter(true);

                Assert.assertTrue(base.wait.elementWithText(successText, 5, true), "SUCCESS text is not shown");
                Base.log("SUCCESS text is shown");
                counter++;
            } else {
                break;
            }
        }
        Assert.assertTrue(counter > 0, "Test impossible, because precondition isn't valid - no one pending user was found\n");
        Base.log("pending users are not found, number of deleted users: " + counter);
        result = true;
        return result;
    }

//----------------------------------------------------------------------------------------------------------------------

    public void deleteMasterUser(){
        deleteAllActive(adminStatusText);
    }

    public void deleteAllGuests(){
        deleteAllActive(userStatusText);
    }

//----------------------------------------------------------------------------------------------------------------------

    private void deleteAllActive(String status) {
        String successText = null;
        int counter = 0;
        Assert.assertNotNull(status,"expected object: \"User Status localized text\", ");

        if (status.equalsIgnoreCase(userStatusText)){
            successText = base.getLocalizeTextForKey("user_guest_detach");
        }else if (status.equalsIgnoreCase(adminStatusText)){
            successText = base.getLocalizeTextForKey("Detach_success1");
        }else {
            Assert.fail("localized text for User Status was not found");
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
                    Base.log(1, "delete user with email \"" + email + "\"");
                    settingsButton.click();
//                    base.nav.scrollToElement(deleteButton, "up");
                    base.nav.scrollBottom();
                    Base.log(1, "tap delete button");
                    deleteButton.click();
                    base.nav.confirmIt();
                    Assert.assertTrue(base.wait.elementWithText(successText, 10, true), "SUCCESS text is not shown");
                    Base.log(1, "user with email \"" + email + "\" is deleted successfully and SUCCESS text is shown");
                    counter++;
                } else {break;}
            }
        }catch (NoSuchElementException e){
            Base.log(1, "NoSuchElementException: \n\n" + e + "\n");
        }
        Assert.assertTrue(counter > 0, "Test impossible, because precondition isn't valid - no one user with status \"" + status + "\" are not found\n");
        Base.log(1, "active users with status \"" + status + "\" are not found, number of deleted users: " + counter);
    }

//----------------------------------------------------------------------------------------------------------------------

    private WebElement findPendingFrom(String from, String pendingUserName){
        String nameElementXpath = "*[contains(@resource-id,'com.ajaxsystems:id/name') and @text='" + pendingUserName + "']";
        String deleteElementXpath = "*[@resource-id = 'com.ajaxsystems:id/delete']";
        String firstLinearLayout = "android.widget.LinearLayout[1]";
        String xPath = null;
        switch (from){
            case "delete": xPath = "//" + deleteElementXpath + "/ancestor::" + firstLinearLayout + "//" + nameElementXpath;
                break;
            case "name": xPath = "//" + nameElementXpath + "/ancestor::" + firstLinearLayout + "//" + deleteElementXpath;
                break;
            default: Base.log(3, "invalid parameter");
                break;
        }
        WebElement pendingUserElement = driver.findElementByXPath(xPath);
        base.nav.scrollToElement(pendingUserElement, "up");
        return pendingUserElement;
    }

//----------------------------------------------------------------------------------------------------------------------
}