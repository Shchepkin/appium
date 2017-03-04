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
    private Setup s = new Setup();
    private Check check;
    private PopUp popUp;

    @AndroidFindBy(id = "com.ajaxsystems:id/invites")
    private WebElement inviteUsersField;

    @AndroidFindBy(xpath = "//android.widget.TextView")
    private ArrayList<WebElement> allTextObjects;


    public User(AppiumDriver driver, String locale_) {
        this.driver = driver;
        nav = new Navigation(driver);
        check = new Check(driver);
        popUp = new PopUp(driver);
        s = new Setup(locale_);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }


    public void fillUserEmailsField(String inviteEmail) {
        inviteUsersField.sendKeys(inviteEmail);
        nav.nextBtn.click();
    }

    public void addUserWithEmail(String inviteEmail) {
        String tmp = s.getLocalizeTextForKey("invite_has_not_been_sent_to_following_emails");
        String inviteFailText = tmp.substring(0, tmp.length() - 5);

        inviteUsersField.sendKeys(inviteEmail);
        check.clickElementAndWaitingPopup(nav.nextBtn, true);

        popUp.waitLoaderPopUpWithText(inviteFailText, 10, true);

    }
}
