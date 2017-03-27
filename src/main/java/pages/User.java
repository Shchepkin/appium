package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
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

    @AndroidFindBy(id = "com.ajaxsystems:id/status")
    private WebElement userStatus;

    private String oneUserForEmailField = "test.email.ajax1@i.ua";
    private String oneUserForContactList = "test.email.ajax2@i.ua";


//    private String [] manyUsersForEmailField = {
//            "test.email.ajax3@i.ua",
//            "test.email.ajax4@i.ua",
//            "test.email.ajax5@i.ua"
//    };


//    private String [] manyUsersForContactList = {
//            "test.email.ajax6@i.ua",
//            "test.email.ajax7@i.ua",
//            "test.email.ajax8@i.ua"
//    };

//    private String oneUserForEmailField = s.getDbSettings().get("oneUserForEmailField").toString();
//    private Map oneUser = s.getJsonCollection("emails.json", "one");

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
        nav.nextBtn.click();
    }

//**********************************************************************************************************************
    public boolean addOneFromEmailField() {
        s.log("Method is started");
        result = false;

        nav.scrollToElementWithText("up", sendInvitesButtonText, true);

        s.log("fill email field with \"" + oneUserForEmailField + "\"");
        inviteUsersField.sendKeys(oneUserForEmailField);

        s.log("click add button and confirm proposition");
        check.clickElementAndWaitingPopup(nav.nextBtn, true);

        s.log("wait PopUp with ERROR text");
        if (!popUp.waitLoaderPopUpWithText(inviteFailText, 20, true)) {
            s.log("there is no ERROR message");

            s.log("check whether new user is added");
            if(nav.scrollToElementWithText("up", oneUserForEmailField,false)){
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
    public boolean addOneFromContactList() {
        s.log("Method is started");
        result = false;

        s.log("searching and clicking the Send Invites Button");
        nav.scrollToElementWithText("up", sendInvitesButtonText, true);

        s.log("click the Add From Contact List Button");
        addButtonFromContactList.click();

        nav.scrollToElementWithText("up", oneUserForContactList, true);
        nav.nextBtn.click();

        s.log("click add button and confirm proposition");
        check.clickElementAndWaitingPopup(nav.nextBtn, true);

        s.log("wait PopUp with ERROR text");
        if (!popUp.waitLoaderPopUpWithText(inviteFailText, 20, true)) {
            s.log("there is no ERROR message");

            s.log("check whether new user is added");
            if(nav.scrollToElementWithText("up", oneUserForContactList,false)){
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
    public boolean addManyFromEmailField() {
        s.log("Method is started");
        result = false;
        String emailListString = "";
        int counter = 0;

        ArrayList<String> manyUsersForEmailField = s.getJsonStringArray("emails.json", "manyUsersForEmailField");

        s.log("click send Invites Button");
        nav.scrollToElementWithText("up", sendInvitesButtonText, true);

        s.log("concat all emails from array to the one string");
        for (String userEmail : manyUsersForEmailField) {
            s.log("concat email \"" + userEmail + "\" to the string");
            emailListString = emailListString.concat(userEmail + " ");
        }

        s.log("send emailList String to the Users Field");
        inviteUsersField.sendKeys(emailListString);

        s.log("click Add button");
        nav.nextBtn.click();
        if(!check.forSnackBarIsPresent(3)) {

            s.log("confirm proposition");
            nav.confirmIt();
            wait.invisibilityOfWaiter(true);

            check.isElementDisplayed(nav.backBtn, 15);

            s.log("check whether new user is added");
            for (String userEmail : manyUsersForEmailField) {
                if (nav.scrollToElementWithText("up", userEmail, false)) {
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
    public boolean addManyFromContactList() {
        s.log("Method is started");
        result = false;
        int counter = 0;

        ArrayList<String> manyUsersForContactList = s.getJsonStringArray("emails.json", "manyUsersForContactList");

        s.log("searching and clicking the Send Invites Button");
        nav.scrollToElementWithText("up", sendInvitesButtonText, true);

        s.log("click the Add From Contact List Button");
        addButtonFromContactList.click();

        s.log("start Add user process");
        for (String userEmail : manyUsersForContactList) {

            s.log("scrolling to the start of list");
            nav.scrollTop();

            s.log("search email \"" + userEmail + "\" in the Contacts List and click them");
            nav.scrollToElementWithText("up", userEmail, true);
        }

        s.log("click Save button");
        nav.nextBtn.click();

        s.log("click add button and confirm proposition");
        check.clickElementAndWaitingPopup(nav.nextBtn, true);

        s.log("wait PopUp with ERROR text");
        if (!popUp.waitLoaderPopUpWithText(inviteFailText, 15, true)) {
            s.log("there is no ERROR message");

            s.log("check whether new user is added");
            for (String userEmail : manyUsersForContactList) {

                s.log("scrolling to the start of list");
                nav.scrollTop();

                if(nav.scrollToElementWithText("up", userEmail,false)) {
                    s.log("new user with email \"" + userEmail + "\" is added successfully");
                    counter++;
                }
            }

            s.log(3, "added " + counter + " new users from " + manyUsersForContactList.size());
            if(counter == manyUsersForContactList.size()) {
                s.log("all users are added");
                result = true;
            }else {
                s.log(3, "not all users are added");
                result = false;
            }

        } else  {
            s.log(3, "ERROR text is shown");
            result = false;
        }

        s.log("Method is finished");
        return result;
    }

//**********************************************************************************************************************
    public boolean addUserWithEmail(String inviteEmail) {
        s.log("Method is started");

        result = false;
        String tmp = s.getLocalizeTextForKey("invite_has_not_been_sent_to_following_emails");
        String inviteFailText = tmp.substring(0, tmp.length() - 5);

        s.log("fill email field with \"" + inviteEmail + "\"");
        inviteUsersField.sendKeys(inviteEmail);

        s.log("click add button and waiting popUp");
        check.clickElementAndWaitingPopup(nav.nextBtn, true);

        s.log("wait PopUp with ERROR text");
        if (!popUp.waitLoaderPopUpWithText(inviteFailText, 20, true)) {
            s.log("there is no ERROR message");
            result = true;
        } else  {
            s.log(3, "ERROR text is shown");
            result = false;
        }

        s.log("Method is finished");
        return result;
    }

//**********************************************************************************************************************
    public boolean addUserFromContactList(String [] inviteEmail) {
        s.log("Method is started");

        result = false;
        String tmp = s.getLocalizeTextForKey("invite_has_not_been_sent_to_following_emails");
        String inviteFailText = tmp.substring(0, tmp.length() - 5);

        s.log("fill email field with \"" + inviteEmail + "\"");

        s.log("fill email field with \"" + inviteEmail + "\"");
        inviteUsersField.sendKeys(inviteEmail);

        s.log("click add button and waiting popUp");
        check.clickElementAndWaitingPopup(nav.nextBtn, true);

        s.log("wait PopUp with ERROR text");
        if (!popUp.waitLoaderPopUpWithText(inviteFailText, 15, true)) {
            s.log("there is no ERROR message");
            result = true;
        } else  {
            s.log(3, "ERROR text is shown");
            result = false;
        }

        s.log("Method is finished");
        return result;
    }
}
