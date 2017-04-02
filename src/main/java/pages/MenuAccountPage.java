package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by installer on 1/21/17.
 */
public class MenuAccountPage extends Base{
    public final AppiumDriver driver;

    @AndroidFindBy(id = "com.ajaxsystems:id/logout")
    public WebElement logoutBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/edit")
    public WebElement editBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/editText")
    public WebElement editText;

    @AndroidFindBy(id = "com.ajaxsystems:id/logoutText")
    public WebElement logoutText;

    @AndroidFindBy(id = "com.ajaxsystems:id/editImage")
    public WebElement editImage;

    @AndroidFindBy(id = "com.ajaxsystems:id/image")
    public WebElement accountImage;

    @AndroidFindBy(id = "com.ajaxsystems:id/name")
    public WebElement accountName;

    @AndroidFindBy(id = "com.ajaxsystems:id/mail")
    public WebElement accountMail;

    @AndroidFindBy(id = "com.ajaxsystems:id/logoutImage")
    public WebElement logoutImage;

    @AndroidFindBy(id = "com.ajaxsystems:id/back")
    public WebElement backBtn;

    public MenuAccountPage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}
