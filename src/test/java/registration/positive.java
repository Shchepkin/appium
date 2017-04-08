//package registration;
//
//import io.appium.java_client.AppiumDriver;
//import org.openqa.selenium.WebElement;
//import org.testng.Assert;
//import org.testng.annotations.*;
//import pages.*;
//import utils.*;
//import utils.Wait;
//
//import java.util.Map;
//
//public class positive {
//    private AppiumDriver driver;
//    private Map tokenMap;
//    private Hub hub;
//    private User user;
//    private Check check;
//    private Email email;
//    private PopUp popUp;
//    private Wait wait;
//    private IntroPage introPage;
//    private Dashboard dashboard;
//    private Navigation nav;
//    private MenuMainPage menuPage;
//    private AddImagePage addImagePage;
//    private DashboardHeader dashboardHeader;
//    private MenuAccountPage accountPage;
//    private RegistrationPage regPage;
//    private AuthorizationPage loginPage;
//    private ValidationCodePage validationCodePage;
//    private DashboardRoomsPage roomsPage;
//    private DashboardRemotePage remotePage;
//    private String expected, actual;
//    private WebElement[] elements;
//    private boolean result;
//    private Setup s = new Setup();
//    private Sql sql = new Sql();
//    private String name, login, pass, phone, server;
//
//    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appPath_", "locale_" })
//    @BeforeMethod
//    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_, String locale_){
//        s.log("Method is started");
//        s = new Setup(deviceName_, UDID_, platformVersion_, URL_, appPath_, locale_);
//        driver = s.getDriver();
//
//        s.log("Create objects of pages");
//        hub = new Hub(driver, locale_);
//        nav = new Navigation(driver);
//        user = new User(driver, locale_);
//        wait = new Wait(driver, locale_);
//        check = new Check(driver);
//        popUp = new PopUp(driver);
//        menuPage = new MenuMainPage(driver);
//        introPage = new IntroPage(driver);
//        dashboard = new Dashboard(driver);
//        roomsPage = new DashboardRoomsPage(driver);
//        remotePage = new DashboardRemotePage(driver);
//        accountPage = new MenuAccountPage(driver);
//        addImagePage = new AddImagePage(driver);
//        dashboardHeader = new DashboardHeader(driver);
//        regPage = new RegistrationPage(driver);
//        loginPage = new AuthorizationPage(driver);
//        validationCodePage = new ValidationCodePage(driver);
//    }
//
//    // C42097 =================================================================================================
//    @Test(priority = 1, enabled = false)
//    public void C42097_New_user_registration_with_validation() {
//        s.log("TEST IS STARTED");
//        login = "ajaxsys32@bigmir.net";
//        phone = "977275625";
//        pass = "qwe";
//        name = "name";
//        server = "Develop";
//
//        s.log("start from Intro Page and click Registration button");
//        introPage.setServer(server);
//        introPage.goToRegistration();
//
////        s.log("waiting for User Agreement Dialog and tap OK");
////        Assert.assertTrue(check.waitElement(popUp.userAgreement, 30, true));
////        nav.nextBtn.click();
//
//        s.log("registration process");
//        regPage.setUserPic(1);
//        regPage.fillFields(name, login, pass, phone);
//        regPage.confirmAgrimentCheckBox();
//
//        check.clickElementAndWaitingPopup(regPage.registrationBtn, 5, 3, false);
//
//        s.log("waiting for Validation Code Page");
//        Assert.assertTrue(check.waitElement(validationCodePage.smsCode, 60, true));
//
//        s.log("get and fill Validation Codes");
//        validationCodePage.getAndFillValidationCodes("Phone", "%" + phone + "%");
//        validationCodePage.okBtn.click();
//
//        s.log("waiting for Welcome Page with dashboard link");
//        Assert.assertTrue(check.waitElement(regPage.dashboard, 30, true));
//
//        s.log("Welcome Page is shown, so go to the dashboard");
//        check.clickElementAndWaitingPopup(regPage.dashboard, 5, 3, false);
//
//        s.log("waiting for Pincode PopUp");
//        if(check.waitElement(popUp.cancelButton, 15, true)) {
//            s.log("Pincode PopUp is shown with text: \"" + popUp.contentText.getText() + "\", so click CANCEL button");
//            popUp.cancelButton.click();
//        }
//
//        Assert.assertTrue(check.waitElement(dashboardHeader.menuDrawer, 15, true), "Login failed!\n");
//        s.log("TEST IS FINISHED");
//    }
//
//    // C42098 =================================================================================================
//    @Test(priority = 2, enabled = false)
//    public void C42098_Login_to_the_existing_account() {
//        s.log("TEST IS STARTED");
//        login = "ajax1@i.ua";
//        pass = "qwe";
//        server = "Develop";
//
//        s.log("start from IntroPage");
//        introPage.goToAuthorization();
//        loginPage.loginToTheServer(login, pass, server);
//
//        s.log("waiting for Pincode PopUp");
//        if(check.waitElement(popUp.cancelButton, 15, true)) {
//            s.log("Pincode PopUp is shown with text: \"" + popUp.contentText.getText() + "\", so click CANCEL button");
//            expected = s.getLocalizeTextForKey("do_you_want_to_enable_passcode");
//            actual = popUp.contentText.getText();
//            Assert.assertEquals(expected, actual, "Text on Pincode PopUp is wrong!");
//            popUp.cancelButton.click();
//        }
//
//        s.log("check whether login was successfully");
//        Assert.assertTrue(check.waitElement(dashboardHeader.menuDrawer, 60, true));
//        s.log("TEST IS FINISHED");
//    }
//
//    // C42099 =================================================================================================
//    @Test(priority = 1, enabled = false)
//    public void C42099_Add_new_Hub_manually  () {
//        s.log("TEST IS STARTED");
//
//        login = "ajax1@i.ua";
//        pass = "qwe";
//        server = "Develop";
//        String hubName = "1495";
//        String hubKey = "00001495DDFB55691000";
//
//        s.log("start from IntroPage");
//        introPage.goToAuthorization();
//        loginPage.loginToTheServer(login, pass, server);
//
//        s.log("waiting for Pincode PopUp");
//
//        elements = new WebElement[]{dashboardHeader.menuDrawer, popUp.cancelButton};
//        if (check.waitElements(elements, 3) == 2){popUp.cancelButton.click();}
//
//        dashboard.plusBtn.click();
//        nav.nextButtonClick();
//        dashboard.nameField.sendKeys(hubName);
//        dashboard.hubKeyField.sendKeys(hubKey);
//        dashboard.addBtn.click();
//
//        elements = new WebElement[]{dashboardHeader.hubImage, popUp.cancelButton};
//        if (check.waitElements(elements, 3) == 2){popUp.cancelButton.click();}
//        Assert.assertTrue(check.waitElement(dashboardHeader.hubImage, 15, true));
//        s.log("hub successfully added!");
//        s.log("TEST IS FINISHED");
//    }
//
//    // C42100 =================================================================================================
//    @Test(priority = 1, enabled = false)
//    public void C42100_Add_new_room() {
//        s.log("TEST IS STARTED");
//        pass = "qwe123";
//        name = "room_number_";
//        login = "ajax1@i.ua";
//        server = "Production";
//
//        s.log("start from IntroPage");
//        introPage.goToAuthorization();
//        loginPage.loginToTheServer(login, pass, server);
//
//        s.log("waiting for Pincode PopUp");
//        check.waitElementWithoutPin(dashboardHeader.menuDrawer, 3);
//
//        s.log("tap the Room Page button in the footer");
//        dashboard.footerRooms.click();
//
//        s.log("add Room without image");
//        roomsPage.addRoom("Without image", 0);
//
//        s.log("add Room with image from camera");
//        roomsPage.addRoom("Camera image", 1);
//
//        s.log("add Room with image from popup gallery");
//        roomsPage.addRoom("Gallery image", 2, 2);
//
//        s.log("TEST IS FINISHED");
//    }
//
//    // C42102 =================================================================================================
//    @Test(priority = 1, enabled = true)
//    public void C42102_Add_new_guest_user() {
//        s.log("TEST IS STARTED");
//
//        pass = "qwe";
//        login = "ajax1@i.ua";
//        server = "Develop";
//
//        s.log("start from IntroPage");
//        introPage.goToAuthorization();
//        loginPage.loginToTheServer(login, pass, server);
//
//        s.log("waiting for Pincode PopUp");
//        check.waitElementWithoutPin(dashboardHeader.menuDrawer, 3);
//
//        hub.goToTheUserlistPage();
//
//        user.addFromEmailField();
//        Assert.assertTrue(user.checkIsNewUsersAddedBy("text", user.getUsersForEmailField()), "Add users from Email Field is failed");
//
//        user.addFromContactList();
//        Assert.assertTrue(user.checkIsNewUsersAddedBy("text", user.getUsersForContactList()), "Add users from Contact List is failed");
//
//        user.addMixedUsers();
//        Assert.assertTrue(user.checkIsNewUsersAddedBy("text", user.getUsersForMixedAdd()), "Add users with mixed style is failed");
//
//        String unregisteredUserEmail = user.getUsersForMixedAdd().get(1);
//        Assert.assertTrue(user.checkIsDeleteIconPresent(unregisteredUserEmail), "Unregistered user has no DELETE icon");
//
//        s.log("TEST IS FINISHED");
//    }
//
//    // C42176 =================================================================================================
//    @Test(priority = 1, enabled = false)
//    public void C42176_Virtual_Space_Control() {
//        s.log("TEST IS STARTED");
//        pass = "qwe";
//        login = "ajax1@i.ua";
//        server = "Develop";
//        String armedText = s.getLocalizeTextForKey("armed");
//        String disarmedText = s.getLocalizeTextForKey("disarmed");
//        String patrialArmedText = s.getLocalizeTextForKey("partially_armed");
//
//        s.log("start from IntroPage");
//        introPage.goToAuthorization();
//        loginPage.loginToTheServer(login, pass, server);
//
//        s.log("waiting for Pincode PopUp");
//        check.waitElementWithoutPin(dashboardHeader.menuDrawer, 3);
//
//        s.log("go to the Remote Page");
//        remotePage.goToTheRemotePage();
//
//        s.log("click Disarm Button");
//        remotePage.disarmBtn.click();
//        Assert.assertTrue(wait.elementWithText(disarmedText, 10, true), "Text \"" + disarmedText + "\" is not found");
//
//        s.log("click Arm Button and confirm if there is shown popUp");
//        check.clickElementAndWaitingPopup(remotePage.armBtn, true);
//        Assert.assertTrue(wait.elementWithText(armedText, 10, true), "Text \"" + armedText + "\" is not found");
//
//        s.log("click Partial Arm Button and confirm if there is shown popUp");
//        check.clickElementAndWaitingPopup(remotePage.partialArmBtn, true);
//        Assert.assertTrue(wait.elementWithText(patrialArmedText, 10, true), "Text \"" + patrialArmedText + "\" is not found");
//
//        s.log("click Disarm Button");
//        remotePage.disarmBtn.click();
//        Assert.assertTrue(wait.elementWithText(disarmedText, 10, true), "Text \"" + disarmedText + "\" is not found");
//
//        s.log("TEST IS FINISHED");
//    }
//
//    @Test(priority = 2, enabled = false)
//    public void Login_to_the_not_validated_account() {
//        s.log("TEST IS STARTED");
//
//        loginPage.loginToTheServer(login, pass, server);
//
//        s.log("wait for message this_account_was_not_yet_validated");
//        Assert.assertTrue(check.waitElement(popUp.dialogMessage, 60, true));
//
//        String expected = s.getLocalizeTextForKey("this_account_was_not_yet_validated");
//        String actual = popUp.dialogMessage.getText();
//        s.log("actual: " + actual);
//        s.log("expected: " + expected);
//        Assert.assertEquals(expected, actual);
//
//        s.log("additional validation of text on the popUp for key \"confirm\"");
//        Assert.assertEquals(s.getLocalizeTextForKey("confirm"), popUp.okBtn.getText());
//
//        s.log("additional validation of text on the popUp for key \"cancel\"");
//        Assert.assertEquals(s.getLocalizeTextForKey("cancel"), popUp.cancelBtn.getText());
//
//        tokenMap = sql.getTokenMap("Phone", phone);
//        System.out.println("SMS: " + tokenMap.get("smsToken"));
//        System.out.println("Email: " + tokenMap.get("emailToken"));
//
//        s.log("TEST IS FINISHED");
//    }
//
//
//
//    @Test(priority = 1, enabled = false)
//    public void imitator() {
//        s.log("TEST IS STARTED");
//        Imitator imitator = new Imitator();
//        imitator.sendCommand("ver\r\n");
//        imitator.addDevice(203060, 2, 1);
//        imitator.getDeviceList();
//        imitator.registerDevice(203060);
//
//        s.log("TEST IS FINISHED");
//    }
//
//    @Test(priority = 1, enabled = false)
//    public void Test() {
//        s.log("TEST IS STARTED");
//        regPage.fillFields(name, login, pass, phone, server);
//
//        s.log("TEST IS FINISHED");
//    }
//
//
//
//    private void logOut() {
//        dashboardHeader.menuDrawer.click();
//        menuPage.accountBtn.click();
//        accountPage.logoutBtn.click();
//        check.waitElement(introPage.getRegistrationBtn(), 20, true);
//    }
//
//    private void logIn() {
//        s.log("start from IntroPage");
//        introPage.goToAuthorization();
//        loginPage.loginToTheServer(login, pass, server);
//    }
//
//    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_", "appPath_", "locale_" })
//    @Test(priority = 1, enabled = false)
//    public void reg_100_users(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_, String locale_) {
//        s.log("TEST IS STARTED");
//        pass = "qwe123";
//        server = "Develop";
//
//        s.log("start from Intro Page and click Registration button");
//
//        for (int i = 1; i <= 100; i++) {
//            if (i > 1){
//                setup(deviceName_, UDID_, platformVersion_, URL_, appPath_, locale_);
//            }
//            introPage.setServer(server);
//            s.log(3, "Registration user #" + i);
//
//            s.log(3, "Click to the Registration button");
//            introPage.goToRegistration();
//
//            s.log("waiting for User Agreement Dialog and tap OK");
//            check.waitElement(nav.getNextButton(), 5, true);
//            nav.nextButtonClick();
//
//            s.log("registration process");
//            name = "autotest_user" + i;
//            login = "autotest_ajax" + i + "@i.ua";
//
//            if (i < 10) {phone = "68100000" + i;}
//            else if (i >= 10 && i < 100) {phone = "6810000" + i;}
//            else if (i >= 100 && i < 1000) {phone = "681000" + i;}
//            else if (i >= 1000 && i < 10000) {phone = "68100" + i;}
//            else if (i >= 10000 && i < 100000) {phone = "6810" + i;}
//            else if (i >= 100000 && i < 1000000) {phone = "681" + i;}
//            else {phone = "68" + i;}
//
//            regPage.fillFields(name, login, pass, phone);
//            check.clickElementAndWaitingPopup(regPage.registrationBtn, 5, 2, false);
//
//            s.log("waiting for Validation Code Page");
//            check.waitElement(validationCodePage.smsCode, 30, true);
//
//            s.log("get and fill Validation Codes");
//            validationCodePage.getAndFillValidationCodes("Login", login);
//            validationCodePage.okBtn.click();
//
//            s.log("waiting for Welcome Page with dashboard link");
//            check.waitElement(regPage.dashboard, 30, true);
//
//            s.log("close driver");
//            driver.quit();
//        }
//        s.log("TEST IS FINISHED");
//    }
//    @AfterMethod
//    public void endSuit() {
//        s.log("close driver");
//        driver.quit();
//    }
//}
