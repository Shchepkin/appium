package pageObjects.pages.intro;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pageObjects.Base;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RegistrationPage{

    @AndroidFindBy(id = "com.ajaxsystems:id/photo")
    private WebElement userPic;

    @AndroidFindBy(id = "com.ajaxsystems:id/name")
    private WebElement nameField;

    @AndroidFindBy(id = "com.ajaxsystems:id/login")
    private WebElement emailField;

    @AndroidFindBy(id = "com.ajaxsystems:id/loginConfirm")
    private WebElement emailConfirmField;

    @AndroidFindBy(id = "com.ajaxsystems:id/code")
    private WebElement phoneCountryCode;

    @AndroidFindBy(id = "com.ajaxsystems:id/text")
    private List<WebElement> phoneCountryCodeList;

    @AndroidFindBy(id = "com.ajaxsystems:id/phone")
    private AndroidElement phoneField;

    @AndroidFindBy(id = "com.ajaxsystems:id/password")
    private WebElement passwordField;

    @AndroidFindBy(id = "com.ajaxsystems:id/passwordConfirm")
    private WebElement passwordConfirmField;

    @AndroidFindBy(id = "com.ajaxsystems:id/agreement")
    private WebElement userAgreementCheckbox;
    private String userAgreementCheckboxId = "com.ajaxsystems:id/agreement";

    @AndroidFindBy(id = "com.ajaxsystems:id/next")
    private WebElement registrationButtonLink;

    @AndroidFindBy(id = "com.ajaxsystems:id/dashboard")
    private WebElement dashboardLink;

//----------------------------------------------------------------------------------------------------------------------
    public WebElement getDashboardLink() {
        return dashboardLink;
    }
    public WebElement getRegistrationButtonLink() {
        return registrationButtonLink;
    }
//----------------------------------------------------------------------------------------------------------------------
    private Base base;
    private AndroidDriver driver;

    public RegistrationPage(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.DEFAULT_TIMEOUT, TimeUnit.SECONDS), this);
    }
//----------------------------------------------------------------------------------------------------------------------

    public void registrationButtonClick() {
        Base.log(1, "tap Registration Button", true);
        registrationButtonLink.click();
    }

    public void dashboardLinkClick() {
        Base.log(1, "tap Dashboard Link", true);
        dashboardLink.click();
    }

    public boolean registrationProcess(String login, String pass, String loginConfirm, String passConfirm, String server, String phone, String country, String userName, String errorMessage, boolean setUserPic, boolean confirmAgreement) {
        base.introPage.setServer(server);
        base.nav.gotoPage.registration();
        if (setUserPic){setUserPic(1);}
        fillFields(userName, login, pass, loginConfirm, passConfirm, phone, country);
        if (confirmAgreement) {confirmAgreementCheckBox();}
        registrationButtonClick();

        Base.log(1, "check is error message present on page");
        if (!errorMessage.isEmpty()) {return !base.wait.text(errorMessage, 20, true);}
        if (base.check.isPresent.errorMessageOrSnackBar(10)) return false;

        Base.log(1, "waiting for Validation Code Page");
        return base.wait.element(base.validationPage.getSmsCodeField(), 60, true);
    }

//----------------------------------------------------------------------------------------------------------------------

    private void fillFields(String name, String email, String password, String emailConfirm, String passwordConfirm, String phone, String country) {

        if (!name.isEmpty()) {
            Base.log(1, "fill name with: \"" + name + "\"", true);
            nameField.sendKeys(name);
            base.hideKeyboard();
        }

        if (!email.isEmpty()) {
            Base.log(1, "fill email with: \"" + email + "\"", true);
            emailField.sendKeys(email);
            base.hideKeyboard();

        }

        if (!emailConfirm.isEmpty()) {
            Base.log(1, "fill confirm email with: \"" + email + "\"", true);
            emailConfirmField.sendKeys(emailConfirm);
            base.hideKeyboard();
        }

        if (!phone.isEmpty()) {
            Base.log(1, "fill phone with: \"" + phone + "\"", true);
            phoneField.sendKeys(phone);
            base.hideKeyboard();
        }

        if (!country.isEmpty()){base.popUp.setPhoneCountryCode(country);}

        if (!password.isEmpty()) {
            Base.log(1, "fill password with: \"" + password + "\"", true);
            passwordField.sendKeys(password);
            base.hideKeyboard();
        }

        if (!password.isEmpty()) {
            Base.log(1, "fill confirm password with: \"" + password + "\"", true);
            passwordConfirmField.sendKeys(passwordConfirm);
            base.hideKeyboard();
        }
    }

    private void confirmAgreementCheckBox() {
        Base.log(1, "confirm agreement check box", true);
        base.nav.scroll.toElementWith.id(userAgreementCheckboxId, true);
    }

    private void setUserPic(int imageNumber) {
        Base.log(1, "set UserPic", true);
        Base.log(1, "tap User Pic icon");
        userPic.click();
        base.addImagePage.setImageFromGallery(imageNumber);
    }

    private void setUserPic(int type, int imageNumber) {
        switch (type){
            case 1: Base.log(1, "add image from camera");
                userPic.click();
                base.addImagePage.setImageFromCamera();
                break;
            case 2: Base.log(1, "add image from gallery");
                userPic.click();
                base.addImagePage.setImageFromGallery(imageNumber);
                break;
            default: Base.log(1, "without image");
                break;
        }
    }

    private boolean waitForError (String errorType) {
        switch (errorType){
            case "snack" :
                return base.check.isPresent.snackBar(10);
            case "error" :
                return base.check.isPresent.error(10);
            default:
                Base.log(3, "unknown error type for waiting \"" + errorType + "\"");
                return false;
        }
    }

}
