package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.Check;
import utils.Navigation;
import utils.PopUp;
import utils.Setup;

import java.util.ArrayList;

import static jdk.nashorn.internal.objects.NativeString.substring;

public class User {
    public final AppiumDriver driver;
    private Navigation nav;
    private Setup s;
    private Check check;
    private PopUp popUp;

    @AndroidFindBy(id = "com.ajaxsystems:id/invites")
    private WebElement inviteUsersField;

    @AndroidFindBy(xpath = "//android.widget.TextView")
    private ArrayList<WebElement> allTextObjects;


    public User(AppiumDriver driver, String locale_) {
        this.driver = driver;
        s = new Setup(locale_);
        nav = new Navigation(driver);
        check = new Check(driver);
        popUp = new PopUp(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }


    public void fillUserEmailsField(String inviteEmail) {
        inviteUsersField.sendKeys(inviteEmail);
        nav.nextBtn.click();
    }

    public void addUserWithEmail(String inviteEmail) {
        s.log("Method is started");
        String tmp = s.getLocalizeTextForKey("invite_has_not_been_sent_to_following_emails");
        String inviteFailText = tmp.substring(0, tmp.length() - 5);

        s.log("fill email field with \"" + inviteEmail + "\"");
        inviteUsersField.sendKeys(inviteEmail);

        s.log("click add button and waiting popUp");
        check.clickElementAndWaitingPopup(nav.nextBtn, true);

        s.log("click add button and waiting popUp");
        popUp.waitLoaderPopUpWithText(inviteFailText, 10, true);
        s.log("Method is finished");
    }
}
