package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

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
    private final Base base;
    private final AppiumDriver driver;

    public RegistrationPage(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
//----------------------------------------------------------------------------------------------------------------------

    public WebElement getDashboardLink() {
        return dashboardLink;
    }
    public WebElement getRegistrationButtonLink() {
        return registrationButtonLink;
    }

//====================================================================================

    public void fakeRegistration(String email, String password, String phone, String server) {
        Base.log("Method is started");
        base.introPage.goToAuthorization();
        base.loginPage.chooseServer(server);
        base.nav.goBack();
        base.introPage.goToRegistration();
        nameField.sendKeys("fakeRegistration");
        emailField.sendKeys(email);
        emailConfirmField.sendKeys(email);
        phoneField.sendKeys(phone);
        base.nav.swipeUp();
        passwordField.sendKeys(password);
        base.nav.swipeUp();
        passwordConfirmField.sendKeys(password);
        registrationButtonLink.click();
    }



    public void fillFields(String name, String email, String password, String phone, String server) {
        Base.log("Method is started");
        base.introPage.goToAuthorization();
        base.loginPage.chooseServer(server);
        base.nav.goBack();
        base.introPage.goToRegistration();
        nameField.sendKeys(name);
        emailField.sendKeys(email);
        emailConfirmField.sendKeys(email);
        phoneField.sendKeys(phone);
        base.nav.swipeUp();
        passwordField.sendKeys(password);
        base.nav.swipeUp();
        passwordConfirmField.sendKeys(password);
    }

    public void fillFields(String name, String email, String password, String phone) {
        Base.log("Method is started");

        Base.log("fill name with: \"" + name + "\"");
        nameField.sendKeys(name);

        Base.log("fill and confirm email with: \"" + email + "\"");
        emailField.sendKeys(email);
        base.nav.swipeUp();
        emailConfirmField.sendKeys(email);

        Base.log("fill phone with: \"" + phone + "\"");
        phoneField.sendKeys(phone);

        Base.log("fill and confirm password with: \"" + password + "\"");
        base.nav.swipeUp();
        passwordField.sendKeys(password);
        base.nav.swipeUp();
        passwordConfirmField.sendKeys(password);

        Base.log("Method is finished");
    }


    public void confirmAgrimentCheckBox() {
        Base.log("Method is started");
        base.nav.swipeUp();
        userAgreementCheckbox.click();
        Base.log("Method is finished");
    }

    public void setUserPic(int imageNumber) {
        Base.log("Method is started");
        userPic.click();
        base.addImagePage.setImageFromGallery(imageNumber);
        Base.log("Method is finished");
    }

    public void setUserPic(int type, int imageNumber) {
        Base.log("Method is started");

        switch (type){
            case 1: Base.log("add image from camera");
                userPic.click();
                base.addImagePage.setImageFromCamera();
                break;
            case 2: Base.log("add image from gallery");
                userPic.click();
                base.addImagePage.setImageFromGallery(imageNumber);
                break;
            default: Base.log("without image");
                break;
        }
        Base.log("Method is finished");
    }

    public void setPhoneCountryCode() {
        Base.log("Method is started");
    }

}
