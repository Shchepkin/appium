package registrationPage;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.*;
import utils.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.MalformedURLException;

public class positive {
    private AndroidDriver driver;
    private IntroPage introPage;
    private AuthorizationPage authorizationPage;
    private RegistrationPage registrationPage;
    private DashboardHeader dashboardHeader;
    private MenuMainPage menuPage;
    private MenuAccountPage accountPage;
    private Check assertion;
    private AddImagePage addImagePage;
    private ValidationCodePage validationCodePage;
    private Navigation navigation;
    private Email email;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_" })
    @BeforeClass
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_) throws MalformedURLException, InterruptedException {
        AppiumSetup appiumSetup = new AppiumSetup(deviceName_, UDID_, platformVersion_, URL_);
        driver = appiumSetup.getDriver();

        Reporter.log("Create objects of pages", true);
        introPage = new IntroPage(driver);
        authorizationPage = new AuthorizationPage(driver);
        dashboardHeader = new DashboardHeader(driver);
        menuPage = new MenuMainPage(driver);
        accountPage = new MenuAccountPage(driver);
        registrationPage = new RegistrationPage(driver);
        addImagePage = new AddImagePage(driver);
        validationCodePage = new ValidationCodePage(driver);
        navigation = new Navigation(driver);
        assertion = new Check(driver);
    }

    @Test()
    public void Registration_with_correct_data_to_the_Develop_server() throws IOException, MessagingException {

        Reporter.log("Precondition:\n1. Go to the authorization page and set server type to Develop ... ", true);
        introPage.goToAuthorization();
        authorizationPage.longTapLoginButton();
        authorizationPage.serverDevelop.click();
        Reporter.log("Done", true);

        Reporter.log("2. Comeback to the Registration Page ... ", true);
        authorizationPage.backBtn.click();
        introPage.registrationBtn.click();
        Reporter.log("Done", true);

        Reporter.log("\nSteps:\n1. Set UserPic icon ... ", true);
        registrationPage.userPic.click();
        addImagePage.thumbnail.get(3).click();
        addImagePage.saveBtn.click();
        Reporter.log("Done", true);

        Reporter.log("2. Fill the Name field ... ", true);
        registrationPage.nameField.sendKeys("Test name");
        Reporter.log("Done", true);

        Reporter.log("3. Fill the e-mail field with correct data and confirm it ... ", true);
        registrationPage.emailField.sendKeys("develop.ajax.sys@i.ua");
        registrationPage.emailConfirmField.sendKeys("develop.ajax.sys@i.ua");
        Reporter.log("Done", true);

        Reporter.log("4. Fill the phone number field with correct data ... ", true);
        registrationPage.phoneField.sendKeys("979901726");
        Reporter.log("Done", true);

        Reporter.log("5. Fill the password field with correct data and confirm it ... ", true);
        navigation.swipeUp();
        registrationPage.passwordField.sendKeys("qwe123");
        navigation.swipeUp();
        registrationPage.passwordConfirmField.sendKeys("qwe123");
        Reporter.log("Done", true);

        Reporter.log("6. Tap the registration button ... ", true);
        registrationPage.registrationBtn.click();
        Reporter.log("Done", true);

        // Assert is Validation Code page opened
        Reporter.log("7. Check the Validation Code page is opened ... ", true);
        assertion.checkIsDisplayed(validationCodePage.smsCode);
        Reporter.log("Done", true);

        // check auto Loading Code From Sms
        Reporter.log("8. Check the Code From Sms is auto Loaded in the SmsCode field ... ", true);
        validationCodePage.autoLoadCode(validationCodePage.smsCode, 180);
        Reporter.log("Done", true);

        Reporter.log("9. Start email test", true);
        email = new Email("pop.i.ua", "develop.ajax.sys@i.ua", "ajax123");
        email.checkNewMessage();
        String emailCode = email.getValidationCode();

        Reporter.log("Fill the emailCode field and tap registration button", true);
        validationCodePage.emailCode.sendKeys(emailCode);
        validationCodePage.okBtn.click();
        Reporter.log("Done", true);
        email.deleteAllMessage();
        assertion.checkIsDisplayed(registrationPage.dashboard);
    }

    @Test()
    public void Registration_without_validation() {
        String login = "qweqweqweqweqwe@i.ua";
        String pass = "qwe123";
        String phone = "683669947";
        registrationPage.fakeRegistration(login, pass, phone);
        waitElement(validationCodePage.smsCode);
        validationCodePage.cancelBtn.click();
        waitElement(authorizationPage.forgotPasswordBtn);
        String actual = authorizationPage.loginField.getText();
        String expected = login;
        Assert.assertEquals(expected, actual);
    }


    private void logOut() {
        dashboardHeader.menuDrawer.click();
        menuPage.accountBtn.click();
        accountPage.logoutBtn.click();
        assertion.checkIsDisplayed(authorizationPage.loginBtn);
    }


    public void waitElement (WebElement element) {
        WebDriverWait iWait = new WebDriverWait (driver, 60);
        iWait.until(ExpectedConditions.visibilityOf(element));
    }

    @AfterClass
    public void endSuit() {
        driver.quit();
    }
}
