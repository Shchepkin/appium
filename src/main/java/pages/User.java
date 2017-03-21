package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.*;

import java.util.ArrayList;

import static jdk.nashorn.internal.objects.NativeString.substring;

public class User {
    public final AppiumDriver driver;
    private Navigation nav;
    private Setup s;
    private Check check;
    private PopUp popUp;
    private Wait wait;
    private boolean result;
    private String sendInvitesButtonText;
    private String inviteFailText;

    private String oneUserForEmailField = "test.email.ajax1@i.ua";
    private String oneUserForContactList = "test.email.ajax2@i.ua";

    private String [] manyUsersForEmailField = {
            "test.email.ajax3@i.ua",
            "test.email.ajax4@i.ua",
            "test.email.ajax5@i.ua"
    };
    private String [] manyUsersForContactList = {
            "test.email.ajax6@i.ua",
            "test.email.ajax7@i.ua",
            "test.email.ajax8@i.ua"
    };


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

        } else s.log(3, "ERROR text is shown");

        s.log("Method is finished");
        return result;
    }

    public boolean addOneFromContactList() {
        s.log("Method is started");
        result = false;
        nav.scrollToElementWithText("up", sendInvitesButtonText, true);
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
        } else s.log(3, "ERROR text is shown");

        s.log("Method is finished");
        return result;
    }

    public boolean addManyFromEmailField() {
        s.log("Method is started");
        result = false;
        String emailListString = "";
        int counter = 0;

        s.log("click send Invites Button");
        nav.scrollToElementWithText("up", sendInvitesButtonText, true);

        s.log("concat emails to one string");
        for (String userEmail : manyUsersForEmailField) {

            s.log("concat email \"" + userEmail + "\" to list");
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

            check.isElementDisplayed(nav.backBtn);

            s.log("check whether new user is added");
            for (String userEmail : manyUsersForEmailField) {
                if (nav.scrollToElementWithText("up", userEmail, false)) {
                    counter++;
                }
            }

            if (counter == manyUsersForEmailField.length) {
                s.log("all new user is shown in the userList");
                result = true;
            }
        }
        s.log("Method is finished");
        return result;
    }

    public boolean addManyFromContactList() {
        s.log("Method is started");
        result = false;
        int counter = 0;
        nav.scrollToElementWithText("up", sendInvitesButtonText, true);

        for (String userEmail : manyUsersForContactList) {
            s.log("find email \"" + userEmail + "\" in the Contacts List");
            nav.scrollToElementWithText("up", userEmail, true);
        }

        nav.nextBtn.click();

        s.log("click add button and confirm proposition");
        check.clickElementAndWaitingPopup(nav.nextBtn, true);

        s.log("wait PopUp with ERROR text");
        if (!popUp.waitLoaderPopUpWithText(inviteFailText, 20, true)) {
            s.log("there is no ERROR message");

            s.log("check whether new user is added");

            for (String userEmail : manyUsersForEmailField) {
                if(nav.scrollToElementWithText("up", userEmail,false)) {
                    counter++;
                }
            }

            if(counter == 3) {
                result = true;
            }

        } else s.log(3, "ERROR text is shown");

        s.log("Method is finished");
        return result;
    }

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
        } else s.log(3, "ERROR text is shown");

        s.log("Method is finished");
        return result;
    }

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
        if (!popUp.waitLoaderPopUpWithText(inviteFailText, 20, true)) {
            s.log("there is no ERROR message");
            result = true;
        } else s.log(3, "ERROR text is shown");

        s.log("Method is finished");
        return result;
    }
}
