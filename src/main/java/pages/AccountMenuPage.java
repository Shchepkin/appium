package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class AccountMenuPage{

    @AndroidFindBy(id = "com.ajaxsystems:id/logout")
    private WebElement logoutBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/edit")
    private WebElement editBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/editText")
    private WebElement editText;

    @AndroidFindBy(id = "com.ajaxsystems:id/logoutText")
    private WebElement logoutText;

    @AndroidFindBy(id = "com.ajaxsystems:id/editImage")
    private WebElement editImage;

    @AndroidFindBy(id = "com.ajaxsystems:id/image")
    private WebElement accountImage;

    @AndroidFindBy(id = "com.ajaxsystems:id/name")
    private WebElement accountName;

    @AndroidFindBy(id = "com.ajaxsystems:id/mail")
    private WebElement accountMail;

    @AndroidFindBy(id = "com.ajaxsystems:id/logoutImage")
    private WebElement logoutImage;

//----------------------------------------------------------------------------------------------------------------------
    private final Base $;
    private final AppiumDriver driver;

    public AccountMenuPage(Base base) {
        $ = base;
        this.driver = $.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
//----------------------------------------------------------------------------------------------------------------------

}
