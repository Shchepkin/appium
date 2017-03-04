package registration;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.*;
import utils.*;

import java.util.Map;

public class positive {
    private AppiumDriver driver;
    private Map tokenMap;
    private Hub hub;
    private Check check;
    private Email email;
    private PopUp popUp;
    private IntroPage introPage;
    private Dashboard dashboard;
    private Navigation nav;
    private MenuMainPage menuPage;
    private AddImagePage addImagePage;
    private DashboardHeader dashboardHeader;
    private MenuAccountPage accountPage;
    private RegistrationPage registrationPage;
    private AuthorizationPage authorizationPage;
    private ValidationCodePage validationCodePage;
    private DashboardRoomsPage roomsPage;
    private String expected, actual;
    private WebElement[] elements;
    private boolean result;
    private Setup s = new Setup();
    private Sql sql = new Sql();

//    private static final String name = "qwertyuiopqwertyuiop";
//    private static final String login = "test.email.ajax@i.ua";
//    private static final String pass = "qwe123";
//    private static final String phone = "683669947";
//    private static final String server = "Develop";

    private String name, login, pass, phone, server;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appPath_", "locale_" })
    @BeforeMethod
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_, String locale_){
        s.log("Method is started");
        s = new Setup(deviceName_, UDID_, platformVersion_, URL_, appPath_, locale_);
        driver = s.getDriver();

        s.log("Create objects of pages");
        hub = new Hub(driver);
        nav = new Navigation(driver);
        check = new Check(driver);
        popUp = new PopUp(driver);
        menuPage = new MenuMainPage(driver);
        introPage = new IntroPage(driver);
        dashboard = new Dashboard(driver);
        accountPage = new MenuAccountPage(driver);
        addImagePage = new AddImagePage(driver);
        dashboardHeader = new DashboardHeader(driver);
        registrationPage = new RegistrationPage(driver);
        authorizationPage = new AuthorizationPage(driver);
        validationCodePage = new ValidationCodePage(driver);
        roomsPage = new DashboardRoomsPage(driver);
    }


    // C29030 =================================================================================================
    @Test(priority = 1, enabled = false)
    public void C29030_New_user_registration_with_validation() {
        s.log("TEST IS STARTED");

        s.log("start from Intro Page and click Registration button");
        introPage.setServer(server);
        introPage.registrationBtn.click();

        s.log("waiting for User Agreement Dialog and tap OK");
        Assert.assertTrue(check.waitElement(popUp.userAgreement, 30, true));
        nav.nextBtn.click();

        s.log("registration process");
        registrationPage.setUserPic(1);
        registrationPage.fillFields(name, login, pass, phone);

        check.clickElementAndWaitingPopup(registrationPage.registrationBtn, 5, 3, false);

        s.log("waiting for Validation Code Page");
        Assert.assertTrue(check.waitElement(validationCodePage.smsCode, 60, true));

        s.log("get and fill Validation Codes");
        validationCodePage.getAndFillValidationCodes("Phone", "%" + phone + "%");
        validationCodePage.okBtn.click();

        s.log("waiting for Welcome Page with dashboard link");
        Assert.assertTrue(check.waitElement(registrationPage.dashboard, 30, true));

        s.log("Welcome Page is shown, so go to the dashboard");
        check.clickElementAndWaitingPopup(registrationPage.dashboard, 5, 3, false);

        s.log("waiting for Pincode PopUp");
        if(check.waitElement(popUp.cancelButton, 15, true)) {
            s.log("Pincode PopUp is shown with text: \"" + popUp.contentText.getText() + "\", so click CANCEL button");
            popUp.cancelButton.click();
        }

        Assert.assertTrue(check.waitElement(dashboardHeader.menuDrawer, 15, true), "Login failed!\n");
        s.log("TEST IS FINISHED");
    }

    // C29047 =================================================================================================
    @Test(priority = 2, enabled = false)
    public void C29047_Login_to_the_existing_account() {
        s.log("TEST IS STARTED");
        login = "ajax1@i.ua";
        pass = "qwe";
        server = "Develop";

        s.log("start from IntroPage");
        introPage.loginBtn.click();
        authorizationPage.loginToTheServer(login, pass, server);

        s.log("waiting for Pincode PopUp");
        if(check.waitElement(popUp.cancelButton, 15, true)) {
            s.log("Pincode PopUp is shown with text: \"" + popUp.contentText.getText() + "\", so click CANCEL button");
            expected = s.getLocalizeTextForKey("do_you_want_to_enable_passcode");
            actual = popUp.contentText.getText();
            Assert.assertEquals(expected, actual, "Text on Pincode PopUp is wrong!");
            popUp.cancelButton.click();
        }

        s.log("check whether login was successfully");
        Assert.assertTrue(check.waitElement(dashboardHeader.menuDrawer, 60, true));
        s.log("TEST IS FINISHED");
    }

    // C29051 =================================================================================================
    @Test(priority = 1, enabled = false)
    public void C29051_Add_new_Hub_manually  () {
        s.log("TEST IS STARTED");

        login = "ajax1@i.ua";
        pass = "qwe";
        server = "Develop";
        String hubName = "1495";
        String hubKey = "00001495DDFB55691000";

        s.log("start from IntroPage");
        introPage.loginBtn.click();
        authorizationPage.loginToTheServer(login, pass, server);

        s.log("waiting for Pincode PopUp");

        elements = new WebElement[]{dashboardHeader.menuDrawer, popUp.cancelButton};
        if (check.waitElements(elements, 3) == 2){popUp.cancelButton.click();}

        dashboard.plusBtn.click();
        nav.nextBtn.click();
        dashboard.nameField.sendKeys(hubName);
        dashboard.hubKey.sendKeys(hubKey);
        dashboard.addBtn.click();

        elements = new WebElement[]{dashboardHeader.hubImage, popUp.cancelButton};
        if (check.waitElements(elements, 3) == 2){popUp.cancelButton.click();}
        Assert.assertTrue(check.waitElement(dashboardHeader.hubImage, 15, true));
        s.log("hub successfully added!");
        s.log("TEST IS FINISHED");
    }

    // C29109 =================================================================================================
    @Test(priority = 1, enabled = false)
    public void C29109_Add_new_room() {
        s.log("TEST IS STARTED");
        pass = "qwe123";
        name = "room_number_";
        login = "ajax1@i.ua";
        server = "Production";

        s.log("start from IntroPage");
        introPage.loginBtn.click();
        authorizationPage.loginToTheServer(login, pass, server);

        s.log("waiting for Pincode PopUp");
        check.waitElementWithoutPin(dashboardHeader.menuDrawer, 3);

        s.log("tap the Room Page button in the footer");
        dashboard.footerRooms.click();

        s.log("add Room without image");
        roomsPage.addRoom("Without image", 0);

        s.log("add Room with image from camera");
        roomsPage.addRoom("Camera image", 1);

        s.log("add Room with image from popup gallery");
        roomsPage.addRoom("Gallery image", 2, 2);

        s.log("TEST IS FINISHED");
    }

    // C29109 =================================================================================================
    @Test(priority = 1, enabled = true)
    public void C29116_Add_new_guest_user() {
        s.log("TEST IS STARTED");
        pass = "qwe";
        login = "ajax1@i.ua";
        server = "Glim";
        String sendInvitesButtonText = s.getLocalizeTextForKey("send_invites");

        s.log("start from IntroPage");
        introPage.loginBtn.click();
        authorizationPage.loginToTheServer(login, pass, server);

        s.log("waiting for Pincode PopUp");
        check.waitElementWithoutPin(dashboardHeader.menuDrawer, 3);

        hub.goToTheUserList();
//        nav.scrollScreenToTheEnd("up");
        nav.scrollToElementWithText("up", sendInvitesButtonText, true);

        System.exit(0);


        s.log("TEST IS FINISHED");
    }

    @Test(priority = 2, enabled = false)
    public void Login_to_the_not_validated_account() {
        s.log("TEST IS STARTED");

        authorizationPage.loginToTheServer(login, pass, server);

        s.log("wait for message this_account_was_not_yet_validated");
        Assert.assertTrue(check.waitElement(popUp.dialogMessage, 60, true));

        String expected = s.getLocalizeTextForKey("this_account_was_not_yet_validated");
        String actual = popUp.dialogMessage.getText();
        s.log("actual: " + actual);
        s.log("expected: " + expected);
        Assert.assertEquals(expected, actual);

        s.log("additional validation of text on the popUp for key \"confirm\"");
        Assert.assertEquals(s.getLocalizeTextForKey("confirm"), popUp.okBtn.getText());

        s.log("additional validation of text on the popUp for key \"cancel\"");
        Assert.assertEquals(s.getLocalizeTextForKey("cancel"), popUp.cancelBtn.getText());

        tokenMap = sql.getTokenMap("Phone", phone);
        System.out.println("SMS: " + tokenMap.get("smsToken"));
        System.out.println("Email: " + tokenMap.get("emailToken"));

        s.log("TEST IS FINISHED");
    }



    @Test(priority = 1, enabled = false)
    public void Test() {
        s.log("TEST IS STARTED");
        registrationPage.fillFields(name, login, pass, phone, server);

        s.log("TEST IS FINISHED");
    }



    private void logOut() {
        dashboardHeader.menuDrawer.click();
        menuPage.accountBtn.click();
        accountPage.logoutBtn.click();
        check.waitElement(introPage.registrationBtn, 20, true);
    }

    private void logIn() {
        s.log("start from IntroPage");
        introPage.loginBtn.click();
        authorizationPage.loginToTheServer(login, pass, server);
    }

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appPath_", "locale_" })
    @Test(priority = 1, enabled = false)
    public void reg_100_users(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_, String locale_) {
        s.log("TEST IS STARTED");
        pass = "qwe123";
        server = "Develop";

        s.log("start from Intro Page and click Registration button");

        for (int i = 42; i <= 100; i++) {
            if (i > 42){
                setup(deviceName_, UDID_, platformVersion_, URL_, appPath_, locale_);
            }
            introPage.setServer(server);
            s.log(3, "Registration user #" + i);

            s.log(3, "Click to the Registration button");
            introPage.registrationBtn.click();

            s.log("waiting for User Agreement Dialog and tap OK");
            check.waitElement(nav.nextBtn, 5, true);
            nav.nextBtn.click();

            s.log("registration process");
            name = "test.user." + i;
            login = "test.email.ajax" + i + "@i.ua";

            if (i < 10) {phone = "68000000" + i;}
            else if (i >= 10 && i < 100) {phone = "6800000" + i;}
            else if (i >= 100 && i < 1000) {phone = "680000" + i;}
            else if (i >= 1000 && i < 10000) {phone = "68000" + i;}
            else if (i >= 10000 && i < 100000) {phone = "6800" + i;}
            else if (i >= 100000 && i < 1000000) {phone = "680" + i;}
            else {phone = "68" + i;}

            registrationPage.fillFields(name, login, pass, phone);
            check.clickElementAndWaitingPopup(registrationPage.registrationBtn, 5, 2, false);

            s.log("waiting for Validation Code Page");
            check.waitElement(validationCodePage.smsCode, 30, true);

            s.log("get and fill Validation Codes");
            validationCodePage.getAndFillValidationCodes("Login", login);
            validationCodePage.okBtn.click();

            s.log("waiting for Welcome Page with dashboard link");
            check.waitElement(registrationPage.dashboard, 30, true);

            s.log("close driver");
            driver.quit();
        }
        s.log("TEST IS FINISHED");
    }
    @AfterMethod
    public void endSuit() {
        s.log("close driver");
        driver.quit();
    }
}
