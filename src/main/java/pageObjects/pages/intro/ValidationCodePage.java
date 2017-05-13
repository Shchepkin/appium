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

    @AndroidFindBy(id = "com.ajaxsystems:id/login")
    private WebElement emailForResend;

    @AndroidFindBy(id = "com.ajaxsystems:id/phone")
    private WebElement phoneForResend;

    @AndroidFindBy(id = "com.ajaxsystems:id/codeLayout")
    private WebElement countryCodeLayout;

    @AndroidFindBy(id = "com.ajaxsystems:id/dialogMessage")
    private WebElement dialogMessage;

    @AndroidFindBy(id = "com.ajaxsystems:id/code")
    private WebElement countryCode;


    public Map getTokenMap() {
        return tokenMap;
    }
    public WebElement getSmsCodeField() {
        return smsCodeField;
    }
//----------------------------------------------------------------------------------------------------------------------
    private Base base;
    private AndroidDriver driver;
    private Map tokenMap;
    private String smsToken, emailToken;
    public ValidateBy validateBy = new ValidateBy();

    public ValidationCodePage(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.TIMEOUT, TimeUnit.SECONDS), this);
    }
//----------------------------------------------------------------------------------------------------------------------

    public Map getValidationCodes(String row, String value){
        if (!tokenMap.isEmpty()){tokenMap.clear();}
        tokenMap = base.sql.getTokenMap(row, value);
        smsToken = tokenMap.get("smsToken").toString();
        emailToken = tokenMap.get("emailToken").toString();
        Base.log(1, "SMS token: " + smsToken, true);
        Base.log(1, "Email token: " + emailToken, true);
        return tokenMap;
    }

    public boolean resendCode(String email, String phone, String country){
        Base.log(1, "resend validation codes", true);
        codeResend.click();
        if(!base.wait.element(dialogMessage, 5, true)) return false;
        Base.log(1, "fill email with: \"" + email + "\"", true);
        emailForResend.sendKeys(email);
        Base.log(1, "fill phone with: \"" + phone + "\"", true);
        phoneForResend.sendKeys(phone);
        if (!country.isEmpty()){base.popUp.setPhoneCountryCode(country);}
        base.nav.confirmIt();

        Base.log(1, "check is error message present on page");
        if (base.check.isPresent.errorMessageOrSnackBar(10)) return false;

        Base.log(1, "waiting for Validation Code Page");
        return base.wait.element(base.validationPage.smsCodeField, 60, true);
    }

    public class ValidateBy{
        public void email(String email){
            getValidationCodes("Login", "%" + email + "%");
            validationProcess(smsToken, emailToken);
        }
        public void phone(String phone){
            getValidationCodes("Phone", "%" + phone + "%");
            validationProcess(smsToken, emailToken);
        }
        public void customMethod (String row, String value, String smsToken, String emailToken, boolean withEmptyTokens, boolean changeTokensPlaces ){
            tokenMap = getValidationCodes(row, "%" + value + "%");
            if (!withEmptyTokens) {
                if (smsToken.isEmpty()) smsToken = tokenMap.get("smsToken").toString();
                if (emailToken.isEmpty()) emailToken = tokenMap.get("emailToken").toString();
            }
            if(changeTokensPlaces){
                validationProcess(emailToken, smsToken);
            }else {
                validationProcess(smsToken, emailToken);
            }
        }
        public void customMethod (String smsToken, String emailToken, boolean changeTokensPlaces ){
            if(changeTokensPlaces){
                validationProcess(emailToken, smsToken);
            }else {
                validationProcess(smsToken, emailToken);
            }
        }
        private void validationProcess(String smsToken, String emailToken){
            Base.log(1, "fill token \"" + smsToken + "\" to the SMS field", true);
            smsCodeField.sendKeys(smsToken);
            Base.log(1, "fill token \"" + emailToken + "\" to the Email field", true);
            emailCodeField.sendKeys(emailToken);
            base.nav.confirmIt();
        }
    }
}
