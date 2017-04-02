package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.Check;
import utils.Navigation;
//import utils.Setup;

/**
 * Created by installer on 1/21/17.
 */
public class Hub extends Base{
    public final AppiumDriver driver;
    private Check check;
    private Navigation nav;
    private String sendInvitesButtonText;

    @AndroidFindBy(id = "com.ajaxsystems:id/usersImage")
    private WebElement hubSettingsUsersImage;

    @AndroidFindBy(id = "com.ajaxsystems:id/usersText")
    private WebElement hubSettingsUsersText;

    @AndroidFindBy(id = "com.ajaxsystems:id/image")
    private WebElement hubImageOnDeviceList;

    @AndroidFindBy(id = "com.ajaxsystems:id/settings")
    private WebElement hubSettingsBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/status")
    private WebElement userStatus;



    public Hub(AppiumDriver driver, String locale_) {
        this.driver = driver;
        nav = new Navigation(driver);
        check = new Check(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public Hub(AppiumDriver driver) {
        this.driver = driver;
        this.nav = new Navigation(driver);
        this.check = new Check(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void goToTheUserInvitationPage() {
        log("method is started");
        sendInvitesButtonText = getLocalizeTextForKey("send_invites");

        log("click on hub");
        hubImageOnDeviceList.click();

        log("click Hub Settings button");
        hubSettingsBtn.click();

        log("click Users tab");
        hubSettingsUsersImage.click();

        check.waitElement(userStatus, 10, true);
        nav.scrollToElementWith("text", "up", sendInvitesButtonText, true);
        log("method is finished");
    }

    public void goToTheUserlistPage() {
        log("method is started");

        log("click on Hub tab");
        hubImageOnDeviceList.click();

        log("click Hub Settings button");
        hubSettingsBtn.click();

        log("click Users tab");
        hubSettingsUsersImage.click();

        check.waitElement(userStatus, 10, true);
        log("method is finished");
    }
}
