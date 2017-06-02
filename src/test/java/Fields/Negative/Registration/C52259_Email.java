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

public class C52259_Email {
    private Base base;
    private String server, country, phone, pass, passConfirm, name;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());
        Map settings = base.getJsonMapCollection("fieldsEmailNegative.json", "settings");
        name = base.getStringValue(settings, "name");
        phone = base.getStringValue(settings, "phone");
        server = base.getStringValue(settings, "server");
        country = base.getStringValue(settings, "country");
        pass = base.getStringValue(settings, "pass");
        passConfirm = pass;
    }

    @DataProvider
    public Iterator<Object[]> dataProviderIterator() {return base.getDataProviderIterator("fieldsEmailNegative.json");}

    @Test(dataProvider = "dataProviderIterator")
    public void parameters (Map param) {
        base.getDriver().resetApp();
        String login, loginConfirm;

        String expectedText = base.getLocalizeTextForKey(base.getStringValue(param, "key"));
        String notification = base.getStringValue(param, "notification");
        boolean expectedResult = (boolean)param.get("expected");
        boolean confirmAgreement = (boolean) param.get("agreement");

        login = base.getStringValue(param, "login");
        try {loginConfirm = base.getStringValue(param, "login2");
        }catch (Exception e){loginConfirm = login;}

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
