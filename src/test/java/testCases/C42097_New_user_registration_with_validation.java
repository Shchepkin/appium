package testCases;

import io.appium.java_client.AppiumDriver;
import org.testng.Assert;
import pages.*;

public class C42097_New_user_registration_with_validation extends Base {

    private String name, login, pass, phone, server;

    public C42097_New_user_registration_with_validation(AppiumDriver driver, String locale_) {
        super(driver, locale_);
        log("Method is started");

        login = "ajaxsys32@bigmir.net";
        phone = "977275625";
        pass = "qwe";
        name = "name";
        server = "Develop";

        log("start from Intro Page and click Registration button");
        introPage.setServer(server);
        introPage.goToRegistration();

        log("registration process");
        registrationPage.setUserPic(1);
        registrationPage.fillFields(name, login, pass, phone);
        registrationPage.confirmAgriment();

        check.clickElementAndWaitingPopup(registrationPage.registrationBtn, 5, 3, false);

        log("waiting for Validation Code Page");
        Assert.assertTrue(check.waitElement(validationCodePage.smsCode, 60, true));

        log("get and fill Validation Codes");
        validationCodePage.getAndFillValidationCodes("Phone", "%" + phone + "%");
        validationCodePage.okBtn.click();

        log("waiting for Welcome Page with dashboard link");
        Assert.assertTrue(check.waitElement(registrationPage.dashboard, 30, true));

        log("Welcome Page is shown, so go to the dashboard");
        check.clickElementAndWaitingPopup(registrationPage.dashboard, 5, 3, false);

        log("waiting for Pincode PopUp");
        if (check.waitElement(popUp.cancelButton, 15, true)) {
            log("Pincode PopUp is shown with text: \"" + popUp.contentText.getText() + "\", so click CANCEL button");
            popUp.cancelButton.click();
        }

        Assert.assertTrue(check.waitElement(dashboardHeader.menuDrawer, 15, true), "Login failed!\n");
        log("TEST IS FINISHED");

        log("close driver");
        driver.quit();
    }
}
