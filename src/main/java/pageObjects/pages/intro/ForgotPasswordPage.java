package pageObjects.pages.intro;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pageObjects.Base;

import java.util.concurrent.TimeUnit;

public class ForgotPasswordPage{

    @AndroidFindBy(uiAutomator = "new UiSelector().class(\"android.widget.TextView\").text(\"Forgot password?\")")
    private WebElement titleEn;

    @AndroidFindBy(id = "com.ajaxsystems:id/mail")
    private WebElement emailField;

    @AndroidFindBy(id = "com.ajaxsystems:id/code")
    private WebElement countryCode;

    @AndroidFindBy(id = "com.ajaxsystems:id/phone")
    private WebElement phoneField;

    @AndroidFindBy(id = "com.ajaxsystems:id/cancel")
    private WebElement canselBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/ok1")
    private WebElement okBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/message")
    private WebElement toast;

//----------------------------------------------------------------------------------------------------------------------
    private Base base;
    private AndroidDriver driver;

    public ForgotPasswordPage(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.TIMEOUT, TimeUnit.SECONDS), this);
    }
//----------------------------------------------------------------------------------------------------------------------

}
