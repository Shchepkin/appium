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

public class C52065_Check_Registration_Fields {
    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());
    }

    @DataProvider
    public Iterator<Object[]> dataProviderIterator() {return base.getDataProviderIterator("registrationDataProvider.json");}

    @Test(dataProvider = "dataProviderIterator")
    public void parameters (Map param) {
        base.getDriver().resetApp();
        String expectedText = "";
        String server = "Develop";

        String expectedTextKey = base.getStringValue(param, "key");
        String notification = base.getStringValue(param, "notification");
        String country = base.getStringValue(param, "country");
        String login = base.getStringValue(param, "login");
        String phone = base.getStringValue(param, "phone");
        String pass = base.getStringValue(param, "pass");
        String name = base.getStringValue(param, "name");
        boolean expectedResult = (boolean)param.get("expected");
        boolean confirmAgreement = (boolean) param.get("agreement");

        if (!expectedTextKey.isEmpty()) { expectedText = base.getLocalizeTextForKey(expectedTextKey); }

        Base.log(1, notification, true);
        Base.log(1, "Test data:", true);
        Base.log(1, "country: \"" + country + "\"", true);
        Base.log(1, "login: \"" + login + "\"", true);
        Base.log(1, "phone: \"" + phone + "\"", true);
        Base.log(1, "pass: \"" + pass + "\"", true);
        Base.log(1, "name: \"" + name + "\"", true);

        Base.log(1, "START TEST", true);
        boolean actualResult = base.user.registration.withData(login, pass, server, phone, country, name, expectedText, false, confirmAgreement);
        Assert.assertEquals(expectedResult, actualResult, "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"\n");
        if (expectedResult){base.sql.getDelete("Login", login);}
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
