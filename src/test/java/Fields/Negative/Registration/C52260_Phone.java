package Fields.Negative.Registration;

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

public class C52260_Phone {
    private Base base;
    private Map settings;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());

        //init data
        settings = base.getJsonMapCollection("fieldsPasswordNegative.json", "settings");
        String name = base.getStringValue(settings, "name");
        String pass = base.getStringValue(settings, "pass");
        String server = base.getStringValue(settings, "server");
        String country = base.getStringValue(settings, "country");
        String login = base.getStringValue(settings, "login");
        String loginConfirm = login;
        String passConfirm = pass;

        //actions
        base.introPage.setServer(server);
        base.nav.gotoPage.registration();
        base.regPage.fillFields(name, login, "", loginConfirm, "", phone, country);
        base.regPage.confirmAgreementCheckBox();
        base.sql.getDelete("Login", login);
    }

    @DataProvider
    public Iterator<Object[]> dataProviderIterator() {return base.getDataProviderIterator("fieldsPhoneNegative.json");}

    @Test(dataProvider = "dataProviderIterator")
    public void parameters (Map param) {
        base.getDriver().resetApp();
        String loginConfirm, passConfirm;
        String expectedText = "";
        String phone = base.getStringValue(param, "phone");

        String expectedTextKey = base.getStringValue(param, "key");
        String notification = base.getStringValue(param, "notification");
        String country = base.getStringValue(param, "country");
        base.sql.getDelete("Phone", phone);

        try {loginConfirm = base.getStringValue(param, "login2");
        }catch (Exception e){loginConfirm = login;}

        try {passConfirm = base.getStringValue(param, "pass2");
        }catch (Exception e){passConfirm = pass;}

        if (!expectedTextKey.isEmpty()) { expectedText = base.getLocalizeTextForKey(expectedTextKey); }
        if (expectedResult){

        }

        Base.log(1, notification, true);
        Base.log(1, "Test data:", true);
        Base.log(1, "country: \"" + country + "\"", true);
        Base.log(1, "phone: \"" + phone + "\"", true);

        Base.log(1, "START TEST", true);
        boolean actualResult = base.user.registration.withData(login, pass, loginConfirm, passConfirm, server, phone, country, name, expectedText, false, confirmAgreement);
        Assert.assertEquals(expectedResult, actualResult, "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"\n");
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
