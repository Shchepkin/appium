package pageObjects.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pageObjects.Base;

import java.util.concurrent.TimeUnit;

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
    private Base base;
    private AppiumDriver driver;

    public AccountMenuPage(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.TIMEOUT, TimeUnit.SECONDS), this);
    }
//----------------------------------------------------------------------------------------------------------------------

    public WebElement getLogoutBtn() {
        return logoutBtn;
    }
}
