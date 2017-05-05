package pageObjects.pages.intro;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pageObjects.Base;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ValidationCodePage{

    @AndroidFindBy(id = "com.ajaxsystems:id/smsCode")
    private WebElement smsCodeField;

    @AndroidFindBy(id = "com.ajaxsystems:id/emailCode")
    private WebElement emailCodeField;

    @AndroidFindBy(id = "com.ajaxsystems:id/codeResend")
    private WebElement codeResend;

    public WebElement getSmsCodeField() {
        return smsCodeField;
    }

//----------------------------------------------------------------------------------------------------------------------
    private final Base base;
    private final AndroidDriver driver;
    private boolean result;
    private Map tokenMap;

    public ValidationCodePage(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.TIMEOUT, TimeUnit.SECONDS), this);
    }
//----------------------------------------------------------------------------------------------------------------------

    public void getAndFillValidationCodes(String row, String value){
        tokenMap = base.sql.getTokenMap(row, value);
        Base.log(1, "SMS token: " + tokenMap.get("smsToken"), true);
        Base.log(1, "Email token: " + tokenMap.get("emailToken"), true);

        Base.log(1, "fill tokens to the fields");
        smsCodeField.sendKeys(tokenMap.get("smsToken").toString());
        emailCodeField.sendKeys(tokenMap.get("emailToken").toString());
    }

    public void fillTokensValue (String smsToken, String emailToken){
        Base.log(1, "fill tokens to the fields");
        smsCodeField.sendKeys(tokenMap.get("smsToken").toString());
        emailCodeField.sendKeys(tokenMap.get("emailToken").toString());
    }


}
