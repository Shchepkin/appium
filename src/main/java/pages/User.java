package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.*;

import java.util.ArrayList;

public class User {
    public final AppiumDriver driver;
    private Setup s = new Setup();
    private Navigation nav;
    private Check check;
    private PopUp popUp;
    private Wait wait;
    private boolean result;
    private String sendInvitesButtonText;
    private String inviteFailText;

    @AndroidFindBy(id = "com.ajaxsystems:id/active")
    private WebElement active;

    @AndroidFindBy(id = "com.ajaxsystems:id/status")
    private WebElement userStatus;

    @AndroidFindBy(id = "com.ajaxsystems:id/invites")
    private WebElement inviteUsersField;

    @AndroidFindBy(id = "com.ajaxsystems:id/add")
    private WebElement addButtonFromContactList;

    @AndroidFindBy(xpath = "//android.widget.TextView")
    private ArrayList<WebElement> allTextObjects;

    private ArrayList<String> usersForContactList = s.getJsonStringArray("emails.json", "usersForContactList");
    private ArrayList<String> usersForEmailField = s.getJsonStringArray("emails.json", "usersForEmailField");
    private ArrayList<String> usersForMixedAdd = s.getJsonStringArray("emails.json", "usersForMixedAdd");

    public ArrayList<String> getUsersForEmailField() {
        return usersForEmailField;
    }
    public ArrayList<String> getUsersForContactList() {
        return usersForContactList;
    }
    public ArrayList<String> getUsersForMixedAdd() {
        return usersForMixedAdd;
    }


    public User(AppiumDriver driver, String locale_) {
        this.driver = driver;
        s = new Setup(locale_);
        nav = new Navigation(driver);
        check = new Check(driver);
        popUp = new PopUp(driver);
        wait = new Wait(driver, locale_);
        sendInvitesButtonText = s.getLocalizeTextForKey("send_invites");

        inviteFailText = s.getLocalizeTextForKey("invite_has_not_been_sent_to_following_emails")
                          .substring(0, s.getLocalizeTextForKey("invite_has_not_been_sent_to_following_emails").length() - 5);

        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

//======================================================================================================================
//
//======================================================================================================================

    public void fillUserEmailsField(String inviteEmail) {
        inviteUsersField.sendKeys(inviteEmail);
        nav.nextButtonClick();
    }

//**********************************************************************************************************************
    public void addFromEmailField(String userEmail) {
        s.log("Method is started");
        result = false;

        nav.scrollToElementWith("text", "up", sendInvitesButtonText, true);

        s.log("fill email field with \"" + userEmail + "\"");
        inviteUsersField.sendKeys(userEmail);

        s.log("click add button and confirm proposition");
        check.clickElementAndWaitingPopup(nav.getNextButton(), true);

        s.log("click add button and confirm proposition");
        check.clickElementAndWaitingPopup(nav.getNextButton(), true);

        Assert.assertFalse(check.forSnackBarIsPresent(3), "SnackBar is shown with error text \n");
        wait.invisibilityOfWaiter(true);
        Assert.assertTrue(wait.element(userStatus, 10, true), "User page is not shown \n");

        s.log("Method is finished");
    }

//**********************************************************************************************************************
    public void addFromContactList(String userEmail) {
        s.log("Method is started");
        result = false;

        s.log("searching and clicking the Send Invites Button");
        nav.scrollToElementWith("text", "up", sendInvitesButtonText, true);

        s.log("click the Add From Contact List Button");
        addButtonFromContactList.click();

        nav.scrollToElementWith("email", "up", userEmail, true);
        nav.nextButtonClick();

        s.log("click add button and confirm proposition");
        check.clickElementAndWaitingPopup(nav.getNextButton(), true);

        Assert.assertFalse(check.forSnackBarIsPresent(3), "SnackBar is shown with error text \n");
        wait.invisibilityOfWaiter(true);
        Assert.assertTrue(wait.element(userStatus, 10, true), "User page is not shown \n");

        s.log("Method is finished");
    }

//**********************************************************************************************************************

    public boolean addFromEmailField() {
        s.log("Method is started");
        result = false;
        String emailListString = "";
        int counter = 0;

        s.log("click send Invites Button");
        nav.scrollToElementWith("text", "up", sendInvitesButtonText, true);

        s.log("concat all emails from array to the one string");
        for (String userEmail : usersForEmailField) {
            s.log("concat email \"" + userEmail + "\" to the string");
            emailListString = emailListString.concat(userEmail + " ");
        }

        s.log("send emailList String to the Users Field");
        inviteUsersField.sendKeys(emailListString);

        s.log("click Add button");
        nav.nextButtonClick();
        if(!check.forSnackBarIsPresent(3)) {

            s.log("confirm proposition");
            nav.confirmIt();
            wait.invisibilityOfWaiter(true);

            check.isElementDisplayed(userStatus, 15);

            s.log("check whether new user is added");
            for (String userEmail : usersForEmailField) {
                if (nav.scrollToElementWith("text", "up", userEmail, false)) {
                    counter++;
                }
            }

            if (counter == usersForEmailField.size()) {
                s.log("all new user is shown in the userList");
                result = true;
            }else {
                result = false;
            }
        }else {
            result = false;
        }
        s.log("Method is finished");
        return result;
    }

//**********************************************************************************************************************

    public void addUserListFromEmailField(String nameOfJsonCollection) {
        s.log("Method is started");
        ArrayList<String> userListFromJson = s.getJsonStringArray("emails.json", nameOfJsonCollection);
        String emailListString = "";

        s.log("click send Invites Button");
        nav.scrollToElementWith("text", "up", sendInvitesButtonText, true);

        s.log("concat all emails from array to the one string");
        for (String userEmail : userListFromJson) {
            s.log("concat email \"" + userEmail + "\" to the string");
            emailListString = emailListString.concat(userEmail + " ");
        }

        s.log("send emailList String to the Users Field");
        inviteUsersField.sendKeys(emailListString);

        s.log("click Add button");
        nav.nextButtonClick();

        Assert.assertFalse(check.forSnackBarIsPresent(3), "SnackBar is shown with error text \n");
        wait.invisibilityOfWaiter(true);
        Assert.assertTrue(wait.element(userStatus, 15, true), "User page is not shown \n");

        s.log("Method is finished");
    }

//**********************************************************************************************************************

    public void addFromContactList() {
        s.log("Method is started");
        result = false;

        s.log("searching and clicking the Send Invites Button");
        nav.scrollToElementWith("text", "up", sendInvitesButtonText, true);

        s.log("click the Add From Contact List Button");
        addButtonFromContactList.click();

        s.log("start Add user process");
        int firstTime = 0;
        for (String userEmail : usersForContactList) {

            if(firstTime > 0){
                s.log("scrolling to the start of list");
                nav.scrollTop();
            }
            firstTime++;

            s.log("activate user with email \"" + userEmail + "\" in the Contacts List");
            activateUserInContactListBy("email", userEmail);
        }

        s.log("click Save button");
        nav.nextButtonClick();

        s.log("click add button and confirm proposition");
        check.clickElementAndWaitingPopup(nav.getNextButton(), true);

        Assert.assertFalse(check.forSnackBarIsPresent(3), "SnackBar is shown with error text \n");
        wait.invisibilityOfWaiter(true);
        Assert.assertTrue(wait.element(userStatus, 10, true), "User page is not shown \n");

        s.log("Method is finished");
    }

//**********************************************************************************************************************

    private boolean activateUserInContactListBy(String typeBy, String userEmail) {
            String emailElementXpath = "//*[contains(@resource-id,'com.ajaxsystems:id/mail') and @text='" + userEmail + "']";
            String activeElementXpath = "/ancestor::android.widget.FrameLayout[1]//*[@resource-id = 'com.ajaxsystems:id/active']";

        for (int i = 1; i < 3; i++) {
            if (nav.scrollToElementWith(typeBy, "up", userEmail, true)) {
                check.forSnackBarIsPresent(2);
                try {
                    wait.element(driver.findElement(By.xpath(emailElementXpath + activeElementXpath)), 5, true);
                    s.log("element \"" + userEmail + "\" is activated successfully");
                    result = true;
                    break;

                } catch (NoSuchElementException e) {
                    s.log(3, "activation element \"" + userEmail + "\" is failed, try count " + i);
                    s.log(3, "stacktrace: \n" + e + "\n");
                    result = false;
                }
            }
        }
        return result;
    }

//**********************************************************************************************************************

    public void addMixedUsers() {
        s.log("Method is started");
        String registeredUser = usersForMixedAdd.get(0);
        String unregisteredUser = usersForMixedAdd.get(1);
        String userForContactList = usersForMixedAdd.get(2);

        nav.scrollToElementWith("text", "up", sendInvitesButtonText, true);

        s.log("fill email field with \"" + registeredUser + " " + unregisteredUser + "\"");
        inviteUsersField.sendKeys(registeredUser + " " + unregisteredUser);

        s.log("click the Add From Contact List Button");
        addButtonFromContactList.click();

        s.log("activate user with email \"" + userForContactList + "\" in the Contacts List");
        activateUserInContactListBy("email", userForContactList);

        s.log("click Save button");
        nav.nextButtonClick();

        s.log("click add button and confirm proposition");
        check.clickElementAndWaitingPopup(nav.getNextButton(), true);

        Assert.assertFalse(check.forSnackBarIsPresent(3), "SnackBar is shown with error text \n");
        wait.invisibilityOfWaiter(true);
        Assert.assertTrue(wait.element(userStatus, 10, true), "User page is not shown \n");

        s.log("Method is finished");
    }

//**********************************************************************************************************************

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
                s.log("scrolling to the start of list");
                nav.scrollTop();
            }
            firstTime++;

            if (nav.scrollToElementWith(byType, "up", userEmail, false)) {
                s.log("new user with email \"" + userEmail + "\" is added successfully");
                counter++;
            }
        }

        s.log(3, "added " + counter + " new users from " + userList.size());
        if (counter == userList.size()) {
            s.log("all users are added");
            result = true;
        } else {
            s.log(3, "not all users are added");
            result = false;
        }
        return result;
    }

    public boolean checkDeleteIconIsPresent(String userName) {
        String nameElementXpath = "//*[contains(@resource-id,'com.ajaxsystems:id/name') and @text='" + userName + "']";
        String deleteElementXpath = "/ancestor::android.widget.LinearLayout[1]//*[@resource-id = 'com.ajaxsystems:id/delete']";

        if (nav.scrollToElementWith("name", "up", userName, false)) {
            try {
                wait.element(driver.findElement(By.xpath(nameElementXpath + deleteElementXpath)), 5, true);
                s.log("new user with email \"" + userName + "\" has the DELETE button");
                result = true;

            }catch (Exception e){
                s.log(3, "new user with email \"" + userName + "\" has no the DELETE button");
                result = false;
            }
        }
        return result;
    }

}
