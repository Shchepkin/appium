package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import utils.Navigation;

import java.util.List;

public class RegistrationPage extends Base{

    private final AppiumDriver driver;
    private IntroPage introPage;
    private AuthorizationPage loginPage;
    private Navigation nav;
    private AddImagePage addImagePage;

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
    public WebElement phoneCountryCode;

    @AndroidFindBy(id = "com.ajaxsystems:id/text")
    public List<WebElement> phoneCountryCodeList;

    @AndroidFindBy(id = "com.ajaxsystems:id/phone")
    public AndroidElement phoneField;

    @AndroidFindBy(id = "com.ajaxsystems:id/password")
    public WebElement passwordField;

    @AndroidFindBy(id = "com.ajaxsystems:id/passwordConfirm")
    public WebElement passwordConfirmField;

    @AndroidFindBy(id = "com.ajaxsystems:id/agreement")
    public WebElement userAgreementCheckbox;

    @AndroidFindBy(id = "com.ajaxsystems:id/next")
    public WebElement registrationBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/dashboard")
    public WebElement dashboard;

//====================================================================================

    public void fakeRegistration(String email, String password, String phone, String server) {
        log("Method is started");
        introPage.goToAuthorization();
        nav.longTapButton(loginPage.loginBtn, 2);
        loginPage.serverDevelop.click();
        nav.goBack();
        introPage.goToRegistration();
        nameField.sendKeys("fakeRegistration");
        emailField.sendKeys(email);
        emailConfirmField.sendKeys(email);
        phoneField.sendKeys(phone);
        nav.swipeUp();
        passwordField.sendKeys(password);
        nav.swipeUp();
        passwordConfirmField.sendKeys(password);
        registrationBtn.click();
    }



    public void fillFields(String name, String email, String password, String phone, String server) {
        log("Method is started");
        introPage.goToAuthorization();
        loginPage.chooseServer(server);
        nav.goBack();
        introPage.goToRegistration();
        nameField.sendKeys(name);
        emailField.sendKeys(email);
        emailConfirmField.sendKeys(email);
        phoneField.sendKeys(phone);
        nav.swipeUp();
        passwordField.sendKeys(password);
        nav.swipeUp();
        passwordConfirmField.sendKeys(password);
    }

    public void fillFields(String name, String email, String password, String phone) {
        log("Method is started");

        log("fill name with: \"" + name + "\"");
        nameField.sendKeys(name);

        log("fill and confirm email with: \"" + email + "\"");
        emailField.sendKeys(email);
        nav.swipeUp();
        emailConfirmField.sendKeys(email);

        log("fill phone with: \"" + phone + "\"");
        phoneField.sendKeys(phone);

        log("fill and confirm password with: \"" + password + "\"");
        nav.swipeUp();
        passwordField.sendKeys(password);
        nav.swipeUp();
        passwordConfirmField.sendKeys(password);

        log("Method is finished");
    }


    public void confirmAgrimentCheckBox() {
        log("Method is started");
        nav.swipeUp();
        userAgreementCheckbox.click();
        log("Method is finished");
    }

    public void setUserPic(int imageNumber) {
        log("Method is started");
        userPic.click();
        addImagePage.setImageFromGallery(imageNumber);
        log("Method is finished");
    }

    public void setUserPic(int type, int imageNumber) {
        log("Method is started");

        switch (type){
            case 1: log("add image from camera");
                userPic.click();
                addImagePage.setImageFromCamera();
                break;
            case 2: log("add image from gallery");
                userPic.click();
                addImagePage.setImageFromGallery(imageNumber);
                break;
            default: log("without image");
                break;
        }
        log("Method is finished");
    }

    public void setPhoneCountryCode() {
        log("Method is started");


    }

    public RegistrationPage(AppiumDriver driver) {
        this.driver = driver;
        nav = new Navigation(driver);
        introPage = new IntroPage(driver);
        addImagePage = new AddImagePage(driver);
        loginPage = new AuthorizationPage(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}
