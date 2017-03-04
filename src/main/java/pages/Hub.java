package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.Check;
import utils.Navigation;
import utils.Setup;

/**
 * Created by installer on 1/21/17.
 */
public class Hub {
    public final AppiumDriver driver;
    private Navigation nav;
    private Setup s = new Setup();
    private Check check;

    @AndroidFindBy(id = "com.ajaxsystems:id/usersImage")
    private WebElement hubSettingsUsersImage;

    @AndroidFindBy(id = "com.ajaxsystems:id/usersText")
    private WebElement hubSettingsUsersText;

    @AndroidFindBy(id = "com.ajaxsystems:id/image")
    private WebElement hubImageOnDeviceList;

    @AndroidFindBy(id = "com.ajaxsystems:id/settings")
    private WebElement hubSettingsBtn;
    String text;



    public Hub(AppiumDriver driver) {
        this.driver = driver;
        nav = new Navigation(driver);
        check = new Check(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }


    public void goToTheUserList() {
        s.log("method is started");

        s.log("click on hub");
        hubImageOnDeviceList.click();

        s.log("click Hub Settings button");
        hubSettingsBtn.click();

        s.log("click Users tab");
        hubSettingsUsersImage.click();

        check.waitElement(nav.backBtn, 10, true);
        s.log("method is finished");
    }
}
