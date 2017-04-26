package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

// TODO remove all asserts

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
        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.TIMEOUT, TimeUnit.SECONDS), this);
    }
//----------------------------------------------------------------------------------------------------------------------

    public WebElement getDashboardLink() {
        return dashboardLink;
    }
    public WebElement getRegistrationButtonLink() {
        return registrationButtonLink;
    }

    public void registrationButtonClick() {
        Base.log(1, "tap Registration Button");
        registrationButtonLink.click();
    }

    public void dashboardLinkClick() {
        Base.log(1, "tap Dashboard Link");
        dashboardLink.click();
    }

//====================================================================================

    public void fakeRegistration(String email, String password, String phone, String server) {
        Base.log(4, "Method is started");
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
        Base.log(4, "Method is started");
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
        Base.log(4, "Method is started");

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

        Base.log(4, "Method is finished");
    }


    public void confirmAgreementCheckBox() {
        Base.log(4, "Method is started");
        base.nav.swipeUp();
        userAgreementCheckbox.click();
        Base.log(4, "Method is finished");
    }

    public void setUserPic(int imageNumber) {
        Base.log(4, "Method is started");

        Base.log(1, "tap User Pic icon");
        userPic.click();

        base.addImagePage.setImageFromGallery(imageNumber);
        Base.log(4, "Method is finished");
    }

    public void setUserPic(int type, int imageNumber) {
        Base.log(4, "Method is started");

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
        Base.log(4, "Method is finished");
    }

    public void setPhoneCountryCode() {
        Base.log(4, "Method is started");
    }

    public boolean registrationNewUser (){

            String userName, login, pass, phone, server;
                login = base.getCredsWithKey("login");
                pass = base.getCredsWithKey("password");
                server = base.getCredsWithKey("server");
                phone = base.getCredsWithKey("phone");
                userName = base.getCredsWithKey("userName");

                Base.log(1, "start from Intro Page and click Registration button");
                base.introPage.setServer(server);
                base.introPage.goToRegistration();

                Base.log(1, "registration process");
                base.regPage.setUserPic(1);
                base.regPage.fillFields(userName, login, pass, phone);
                base.regPage.confirmAgreementCheckBox();

                base.regPage.registrationButtonClick();
                base.wait.invisibilityOfWaiter();

                Base.log(1, "check is SnackBar present on page");
                Assert.assertFalse(base.wait.visibilityOfSnackBar(5, true), "SnackBar is shown with error text");

                Base.log(1, "waiting for Validation Code Page");
                base.wait.element(base.validationCodePage.getSmsCodeField(), 60, true);

                Base.log(1, "get and fill Validation Codes");
                base.validationCodePage.getAndFillValidationCodes("Phone", "%" + phone + "%");
                base.nav.confirmIt();

                Base.log(1, "waiting for Welcome Page with dashboard link");
                Assert.assertTrue(base.wait.element(base.regPage.getDashboardLink(), 30, true));

                Base.log(1, "Welcome Page is shown, so go to the dashboard");
                base.regPage.dashboardLinkClick();
                base.wait.invisibilityOfLoaderLogo(true);

                Assert.assertTrue( base.check.waitElementWithoutPin(base.dashboardHeader.getMenuDrawer(), 100), "Login failed!\n");


        return false;
    }

}
