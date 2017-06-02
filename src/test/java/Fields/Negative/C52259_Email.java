package Fields.Negative;

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

public class C52259_Email {
    private Base base;
    private String server;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());
        server = base.getJsonMapCollection("fieldsEmailNegative.json", "settings").get("server").toString();
    }

    @DataProvider
    public Iterator<Object[]> dataProviderIterator() {return base.getDataProviderIterator("fieldsEmailNegative.json");}

    @Test(dataProvider = "dataProviderIterator")
    public void parameters (Map param) {
        base.getDriver().resetApp();
        String loginConfirm, passConfirm;
        String expectedText = "";

        String expectedTextKey = base.getStringValue(param, "key");
        String notification = base.getStringValue(param, "notification");
        String country = base.getStringValue(param, "country");
        String login = base.getStringValue(param, "login");
        String phone = base.getStringValue(param, "phone");
        String pass = base.getStringValue(param, "pass");
        String name = base.getStringValue(param, "name");
        boolean expectedResult = (boolean)param.get("expected");
        boolean confirmAgreement = (boolean) param.get("agreement");

        try {loginConfirm = base.getStringValue(param, "login2");
        }catch (Exception e){loginConfirm = login;}

        try {passConfirm = base.getStringValue(param, "pass2");
        }catch (Exception e){passConfirm = pass;}

        if (!expectedTextKey.isEmpty()) { expectedText = base.getLocalizeTextForKey(expectedTextKey); }

        Base.log(1, notification, true);
        Base.log(1, "Test data:", true);
        Base.log(1, "login: \"" + login + "\"", true);
        Base.log(1, "login confirm: \"" + loginConfirm + "\"", true);

        Base.log(1, "START TEST", true);
        boolean actualResult = base.user.registration.withData(login, pass, loginConfirm, passConfirm, server, phone, country, name, expectedText, false, confirmAgreement);
        Assert.assertEquals(expectedResult, actualResult, "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"\n");
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
