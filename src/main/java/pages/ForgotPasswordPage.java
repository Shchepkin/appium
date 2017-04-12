package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class ForgotPasswordPage{

    @AndroidFindBy(uiAutomator = "new UiSelector().class(\"android.widget.TextView\").text(\"Forgot password?\")")
    public WebElement titleEn;

    @AndroidFindBy(id = "com.ajaxsystems:id/mail")
    public WebElement emailField;

    @AndroidFindBy(id = "com.ajaxsystems:id/code")
    public WebElement countryCode;

    @AndroidFindBy(id = "com.ajaxsystems:id/phone")
    public WebElement phoneField;

    @AndroidFindBy(id = "com.ajaxsystems:id/cancel")
    public WebElement canselBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/ok1")
    public WebElement okBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/message")
    public WebElement toast;

//----------------------------------------------------------------------------------------------------------------------
    private final Base base;
    private final AppiumDriver driver;

    public ForgotPasswordPage(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
//----------------------------------------------------------------------------------------------------------------------

}
