package registration;

import io.appium.java_client.android.AndroidDriver;
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
import java.util.HashMap;
import java.util.Map;

public class positive {
    private AndroidDriver driver;
    private IntroPage introPage;
    private AuthorizationPage authorizationPage;
    private RegistrationPage registrationPage;
    private DashboardHeader dashboardHeader;
    private MenuMainPage menuPage;
    private MenuAccountPage accountPage;
    private Check check;
    private AddImagePage addImagePage;
    private ValidationCodePage validationCodePage;
    private Navigation nav;
    private Email email;
    private PopUp popUp;
    private Map tokenMap;
    private Setup s = new Setup();
    private Sql sql = new Sql();

    private static final String login = "qweqweqweqweqwe@i.ua";
    private static final String pass = "qwe123";
    private static final String phone = "683669947";
    private static final String server = "Develop";

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appPath_", "locale_" })
    @BeforeClass
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_, String locale_){
        Reporter.log("Create setup", true);
        s = new Setup(deviceName_, UDID_, platformVersion_, URL_, appPath_, locale_);
        driver = s.getDriver();

        Reporter.log("Create objects of pages", true);
        introPage = new IntroPage(driver);
        authorizationPage = new AuthorizationPage(driver);
        dashboardHeader = new DashboardHeader(driver);
        menuPage = new MenuMainPage(driver);
        accountPage = new MenuAccountPage(driver);
        registrationPage = new RegistrationPage(driver);
        addImagePage = new AddImagePage(driver);
        validationCodePage = new ValidationCodePage(driver);
        nav = new Navigation(driver);
        check = new Check(driver);
        popUp = new PopUp(driver);
    }

    @Test(enabled = false)
    public void Registration_with_correct_data_to_the_Develop_server() throws IOException, MessagingException {

        Reporter.log("Precondition:\n1. Go to the authorization page and set server type to Develop ... ", true);
        introPage.goToAuthorization();
        nav.longTapButton(authorizationPage.loginBtn, 2);
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
        nav.swipeUp();
        registrationPage.passwordField.sendKeys("qwe123");
        nav.swipeUp();
        registrationPage.passwordConfirmField.sendKeys("qwe123");
        Reporter.log("Done", true);

        Reporter.log("6. Tap the registration button ... ", true);
        registrationPage.registrationBtn.click();
        Reporter.log("Done", true);

        // Assert is Validation Code page opened
        Reporter.log("7. Check the Validation Code page is opened ... ", true);
        check.isElementDisplayed(validationCodePage.smsCode);
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
        check.isElementDisplayed(registrationPage.dashboard);
    }

    @Test(enabled = false)
    public void Registration_without_validation() {
        s.log("Method is started");

        s.log("start fakeRegistration");
        registrationPage.fakeRegistration(login, pass, phone, server);

        s.log("wait for Validation Cod Page and tap Cancel button");
        Assert.assertTrue(check.waitElement(validationCodePage.smsCode, 60, true));
        validationCodePage.cancelBtn.click();

        s.log("come back to the Intro Page and go to the Authorization Page");
        nav.backBtn.click();
        introPage.loginBtn.click();

        authorizationPage.loginToTheServer(login, pass, server);

        s.log("wait for message about con");
        Assert.assertTrue(check.waitElement(popUp.dialogMessage, 60, true));

        String expected = s.getLocalizeKeys().get("this_account_was_not_yet_validated").toString();
        String actual = popUp.dialogMessage.getText();
        System.out.println(expected);
        System.out.println(actual);
        Assert.assertEquals(expected, actual);

    }

    @Test(priority = 1, enabled = true)
    public void Login_to_the_not_validated_account() {
        s.log("TEST IS STARTED");

        introPage.loginBtn.click();
        authorizationPage.loginToTheServer(login, pass, server);

        s.log("wait for message this_account_was_not_yet_validated");
        Assert.assertTrue(check.waitElement(popUp.dialogMessage, 60, true));

        String expected = s.getLocalizeKeys().get("this_account_was_not_yet_validated").toString();
        String actual = popUp.dialogMessage.getText();
        s.log("actual: " + actual);
        s.log("expected: " + expected);
        Assert.assertEquals(expected, actual);

        s.log("additional validation of text on the popUp for key \"confirm\"");
        Assert.assertEquals(s.getLocalizeKeys().get("confirm").toString(), popUp.okBtn.getText());

        s.log("additional validation of text on the popUp for key \"cancel\"");
        Assert.assertEquals(s.getLocalizeKeys().get("cancel").toString(), popUp.cancelBtn.getText());

        tokenMap = sql.getTokenMap("Phone", "%" + phone + "%");
        System.out.println("SMS: " + tokenMap.get("smsToken"));
        System.out.println("Email: " + tokenMap.get("emailToken"));

        s.log("TEST IS FINISHED");
    }



    private void logOut() {
        dashboardHeader.menuDrawer.click();
        menuPage.accountBtn.click();
        accountPage.logoutBtn.click();
        check.isElementDisplayed(authorizationPage.loginBtn);
    }


//    public void waitElement (WebElement element) {
//        WebDriverWait iWait = new WebDriverWait (driver, 60);
//        iWait.until(ExpectedConditions.visibilityOf(element));
//    }

    @AfterClass
    public void endSuit() {
        driver.quit();
    }
}
