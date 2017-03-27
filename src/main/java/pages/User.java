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

    private ArrayList<String> usersForContactList = s.getJsonStringArray("emails.json", "usersForContactList");

    public ArrayList<String> getUsersForContactList() {
        return usersForContactList;
    }


    @AndroidFindBy(id = "com.ajaxsystems:id/invites")
    private WebElement inviteUsersField;

    @AndroidFindBy(id = "com.ajaxsystems:id/add")
    private WebElement addButtonFromContactList;

    @AndroidFindBy(xpath = "//android.widget.TextView")
    private ArrayList<WebElement> allTextObjects;


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
    public boolean addFromEmailField(String userEmail) {
        s.log("Method is started");
        result = false;

        nav.scrollToElementWith("text", "up", sendInvitesButtonText, true);

        s.log("fill email field with \"" + userEmail + "\"");
        inviteUsersField.sendKeys(userEmail);

        s.log("click add button and confirm proposition");
        check.clickElementAndWaitingPopup(nav.getNextButton(), true);

        s.log("wait PopUp with ERROR text");
        if (!popUp.waitLoaderPopUpWithText(inviteFailText, 20, true)) {
            s.log("there is no ERROR message");

            s.log("check whether new user is added");
            if(nav.scrollToElementWith("text", "up", userEmail,false)){
                result = true;
            }

        } else {
            s.log(3, "ERROR text is shown");
            result = false;
        }

        s.log("Method is finished");
        return result;
    }

//**********************************************************************************************************************
    public boolean addFromContactList(String userEmail) {
        s.log("Method is started");
        result = false;

        s.log("searching and clicking the Send Invites Button");
        nav.scrollToElementWith("text", "up", sendInvitesButtonText, true);

        s.log("click the Add From Contact List Button");
        addButtonFromContactList.click();

        nav.scrollToElementWith("text", "up", userEmail, true);
        nav.nextButtonClick();

        s.log("click add button and confirm proposition");
        check.clickElementAndWaitingPopup(nav.getNextButton(), true);

        s.log("wait PopUp with ERROR text");
        if (!popUp.waitLoaderPopUpWithText(inviteFailText, 20, true)) {
            s.log("there is no ERROR message");

            s.log("check whether new user is added");
            if(nav.scrollToElementWith("text", "up", userEmail,false)){
                result = true;
            }
        } else {
            s.log(3, "ERROR text is shown");
            result = false;
        }

        s.log("Method is finished");
        return result;
    }

//**********************************************************************************************************************
    public boolean addFromEmailField() {
        s.log("Method is started");
        result = false;
        String emailListString = "";
        int counter = 0;

        ArrayList<String> manyUsersForEmailField = s.getJsonStringArray("emails.json", "usersForEmailField");

        s.log("click send Invites Button");
        nav.scrollToElementWith("text", "up", sendInvitesButtonText, true);

        s.log("concat all emails from array to the one string");
        for (String userEmail : manyUsersForEmailField) {
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

            check.isElementDisplayed(nav.backBtn, 15);

            s.log("check whether new user is added");
            for (String userEmail : manyUsersForEmailField) {
                if (nav.scrollToElementWith("text", "up", userEmail, false)) {
                    counter++;
                }
            }

            if (counter == manyUsersForEmailField.size()) {
                s.log("all new user is shown in the userList");
                result = true;
            }else {
                result = false;
            }
        }
        s.log("Method is finished");
        return result;
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
        int counter = 0;
        for (String userEmail : usersForContactList) {
            if(counter > 0){
                s.log("scrolling to the start of list");
                nav.scrollTop();
            }
            counter++;

            s.log("search email \"" + userEmail + "\" in the Contacts List and tap them");
            for (int i = 1; i <3 ; i++) {
                if(nav.scrollToElementWith("email", "up", userEmail, true)) {

                    if(check.forSnackBarIsPresent(2)){
                        wait.element(nav.getNextButton(), 10, true);
                        break;
                    }

                    if(!checkIsActive(userEmail)){
                        s.log(3, "search email \"" + userEmail + "\" in the Contacts List and tap them, try count " + i);
                    } else {
                        break;
                    }
                }
            }


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

    private boolean checkIsActive(String userEmail) {
            String emailElementXpath = "//*[contains(@resource-id,'com.ajaxsystems:id/mail') and @text='" + userEmail + "']";
            String activeElementXpath = "/ancestor::android.widget.FrameLayout[1]//*[@resource-id = 'com.ajaxsystems:id/active']";

        try {
            wait.element(driver.findElement(By.xpath(emailElementXpath + activeElementXpath)), 5, true);
            s.log("element is activated successfully");
            result = true;

        }catch (NoSuchElementException e){
            s.log(3, "element activation failed");
            e.printStackTrace();
            result = false;
        }

        return result;
    }
//**********************************************************************************************************************

    public boolean checkIsNewUsersAdded(ArrayList<String> userList) {
        result = false;
        int counter = 0;

        for (String userEmail : userList) {

            s.log("scrolling to the start of list");
            nav.scrollTop();

            if (nav.scrollToElementWith("text", "up", userEmail, false)) {
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

}
