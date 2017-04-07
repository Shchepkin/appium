package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class RegistrationPage{

    @AndroidFindBy(id = "com.ajaxsystems:id/back")
    public WebElement backBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/photo")
    public WebElement userPic;

    @AndroidFindBy(id = "com.ajaxsystems:id/name")
    public WebElement nameField;

    @AndroidFindBy(id = "com.ajaxsystems:id/login")
    public WebElement emailField;

    @AndroidFindBy(id = "com.ajaxsystems:id/loginConfirm")
    public WebElement emailConfirmField;

    @AndroidFindBy(id = "com.ajaxsystems:id/code")
    private WebElement phoneCountryCode;

    @AndroidFindBy(id = "com.ajaxsystems:id/text")
    private List<WebElement> phoneCountryCodeList;

    @AndroidFindBy(id = "com.ajaxsystems:id/phone")
    public AndroidElement phoneField;

    @AndroidFindBy(id = "com.ajaxsystems:id/password")
    public WebElement passwordField;

    @AndroidFindBy(id = "com.ajaxsystems:id/passwordConfirm")
    public WebElement passwordConfirmField;

    @AndroidFindBy(id = "com.ajaxsystems:id/agreement")
    public WebElement userAgreementCheckbox;


    @AndroidFindBy(id = "com.ajaxsystems:id/next")
    private WebElement registrationButtonLink;

    @AndroidFindBy(id = "com.ajaxsystems:id/dashboard")
    private WebElement dashboardLink;

//----------------------------------------------------------------------------------------------------------------------
    private final Base $;
    private final AppiumDriver driver;

    public RegistrationPage(Base base) {
        $ = base;
        this.driver = $.getDriver();
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
        $.introPage.goToAuthorization();
        $.loginPage.chooseServer(server);
        $.nav.goBack();
        $.introPage.goToRegistration();
        nameField.sendKeys("fakeRegistration");
        emailField.sendKeys(email);
        emailConfirmField.sendKeys(email);
        phoneField.sendKeys(phone);
        $.nav.swipeUp();
        passwordField.sendKeys(password);
        $.nav.swipeUp();
        passwordConfirmField.sendKeys(password);
        registrationButtonLink.click();
    }



    public void fillFields(String name, String email, String password, String phone, String server) {
        Base.log("Method is started");
        $.introPage.goToAuthorization();
        $.loginPage.chooseServer(server);
        $.nav.goBack();
        $.introPage.goToRegistration();
        nameField.sendKeys(name);
        emailField.sendKeys(email);
        emailConfirmField.sendKeys(email);
        phoneField.sendKeys(phone);
        $.nav.swipeUp();
        passwordField.sendKeys(password);
        $.nav.swipeUp();
        passwordConfirmField.sendKeys(password);
    }

    public void fillFields(String name, String email, String password, String phone) {
        Base.log("Method is started");

        Base.log("fill name with: \"" + name + "\"");
        nameField.sendKeys(name);

        Base.log("fill and confirm email with: \"" + email + "\"");
        emailField.sendKeys(email);
        $.nav.swipeUp();
        emailConfirmField.sendKeys(email);

        Base.log("fill phone with: \"" + phone + "\"");
        phoneField.sendKeys(phone);

        Base.log("fill and confirm password with: \"" + password + "\"");
        $.nav.swipeUp();
        passwordField.sendKeys(password);
        $.nav.swipeUp();
        passwordConfirmField.sendKeys(password);

        Base.log("Method is finished");
    }


    public void confirmAgrimentCheckBox() {
        Base.log("Method is started");
        $.nav.swipeUp();
        userAgreementCheckbox.click();
        Base.log("Method is finished");
    }

    public void setUserPic(int imageNumber) {
        Base.log("Method is started");
        userPic.click();
        $.addImagePage.setImageFromGallery(imageNumber);
        Base.log("Method is finished");
    }

    public void setUserPic(int type, int imageNumber) {
        Base.log("Method is started");

        switch (type){
            case 1: Base.log("add image from camera");
                userPic.click();
                $.addImagePage.setImageFromCamera();
                break;
            case 2: Base.log("add image from gallery");
                userPic.click();
                $.addImagePage.setImageFromGallery(imageNumber);
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
