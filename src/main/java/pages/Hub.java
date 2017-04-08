package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class Hub{

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

//----------------------------------------------------------------------------------------------------------------------
    private final Base $;
    private final AppiumDriver driver;
    private String sendInvitesButtonText;

    public Hub(Base base) {
        $ = base;
        this.driver = $.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
//----------------------------------------------------------------------------------------------------------------------

    public void goToTheUserInvitationPage() {
        Base.log("method is started");
        sendInvitesButtonText = $.getLocalizeTextForKey("send_invites");

        Base.log("click on hub");
        hubImageOnDeviceList.click();

        Base.log("click Hub Settings button");
        hubSettingsBtn.click();

        Base.log("click Users tab");
        hubSettingsUsersImage.click();

        $.wait.element(userStatus, 10, true);
        $.nav.scrollToElementWith("text", "up", sendInvitesButtonText, true);
        Base.log("method is finished");
    }

    public void goToTheUserlistPage() {
        Base.log("method is started");

        Base.log("click on Hub tab");
        hubImageOnDeviceList.click();

        Base.log("click Hub Settings button");
        hubSettingsBtn.click();

        Base.log("click Users tab");
        hubSettingsUsersImage.click();

        $.wait.element(userStatus, 10, true);
        Base.log("method is finished");
    }
}
