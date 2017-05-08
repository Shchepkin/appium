package Registration;

import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.Base;

import java.util.Iterator;
import java.util.Map;

/**
 * PRECONDITION
 * - account exist and validated
 * -
 */

public class C50112_Negative_Registration {

    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());
    }

    @Test()
    public void With_Creds_From_Existing_User () {
        Base.log(1, "START TEST");
        Assert.assertTrue(base.user.registration.withCredsFromExistingUser());
    }

    @Test()
    public void With_Mistake_In_Phone_And_Email () {
        Base.log(1, "START TEST");
        Assert.assertTrue(base.user.registration.withMistakeInPhoneAndEmail());
    }

    @DataProvider
    public Iterator<Object[]> dataProviderIterator() {
        return base.getDataProviderIterator("negativeRegistrationData.json");
    }

    @Test(dataProvider = "dataProviderIterator", enabled = false)
    public void parameters (Map param) {

        String comment = param.get("comment").toString();
        String userName = param.get("userName").toString();
        String country = param.get("country").toString();
        String phone = param.get("phone").toString();
        String login = param.get("email").toString();
        String pass = param.get("pass").toString();
        String expectedText;

        Base.log(1, comment, true);
        Base.log(1, "login: \"" + login + "\"", true);
        Base.log(1, "password: \"" + pass + "\"", true);

        System.out.println("");
        base.loginPage.loginToTheServer(login, pass);

        if (login.isEmpty() || pass.isEmpty()){
            expectedText = base.getLocalizeTextForKey("please_fill_in_all_of_the_required_fields");
        }else {
            expectedText = base.getLocalizeTextForKey("Login_bad_credentials0");
        }

        Assert.assertTrue(base.wait.visibilityOfSnackBarWithText(expectedText, 15));
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
