package pageObjects.pages.intro;

import io.appium.java_client.AppiumDriver;
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
    private final Base base;
    private final AppiumDriver driver;

    public RegistrationPage(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.TIMEOUT, TimeUnit.SECONDS), this);
    }
//----------------------------------------------------------------------------------------------------------------------

    public void registrationButtonClick() {
        Base.log(1, "tap Registration Button");
        registrationButtonLink.click();
    }

    public void dashboardLinkClick() {
        Base.log(1, "tap Dashboard Link");
        dashboardLink.click();
    }

//====================================================================================

    public void fillFields(String name, String email, String password, String phone) {
        Base.log(1, "fill name with: \"" + name + "\"");
        nameField.sendKeys(name);

        Base.log(1, "fill and confirm email with: \"" + email + "\"");
        emailField.sendKeys(email);
        base.nav.swipeUp();
        emailConfirmField.sendKeys(email);

        Base.log(1, "fill phone with: \"" + phone + "\"");
        base.nav.swipeUp();
        phoneField.sendKeys(phone);

        Base.log(1, "fill and confirm password with: \"" + password + "\"");
        base.nav.swipeUp();
        passwordField.sendKeys(password);
        base.nav.swipeUp();
        passwordConfirmField.sendKeys(password);
    }


    public void confirmAgreementCheckBox() {
        base.nav.swipeUp();
        userAgreementCheckbox.click();
    }

    public void setUserPic(int imageNumber) {
        Base.log(1, "tap User Pic icon");
        userPic.click();
        base.addImagePage.setImageFromGallery(imageNumber);
    }

    public void setUserPic(int type, int imageNumber) {
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

    public void setPhoneCountryCode() {
        Base.log(4, "Method is started");
    }

    public boolean registrationProcess(String login, String pass, String server, String phone, String userName) {
        Base.log(1, "delete user if phone already exist at the server");
        base.sql.getDelete("Phone", "%" + phone + "%");

        Base.log(1, "start from Intro Page and click Registration button", true);
        base.introPage.setServer(server);
        base.nav.gotoPage.Registration();

        Base.log(1, "registration process", true);
        setUserPic(1);
        fillFields(userName, login, pass, phone);
        confirmAgreementCheckBox();
        registrationButtonClick();

        Base.log(1, "check is error message present on page");
        if (base.check.isPresent.errorMessageOrSnackBar(10)) return false;

        Base.log(1, "waiting for Validation Code Page");
        return base.wait.element(base.validationCodePage.getSmsCodeField(), 60, true);
    }

}