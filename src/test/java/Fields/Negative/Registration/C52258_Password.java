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

public class C52258_Password {
    private Base base;
    private String server, country, phone, login, loginConfirm, name;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());
        Map settings = base.getJsonMapCollection("fieldsPasswordNegative.json", "settings");
        name = base.getStringValue(settings, "name");
        phone = base.getStringValue(settings, "phone");
        server = base.getStringValue(settings, "server");
        country = base.getStringValue(settings, "country");
        login = base.getStringValue(settings, "pass");
        loginConfirm = login;
    }

    @DataProvider
    public Iterator<Object[]> dataProviderIterator() {return base.getDataProviderIterator("fieldsPasswordNegative.json");}

    @Test(dataProvider = "dataProviderIterator")
    public void parameters (Map param) {
        base.getDriver().resetApp();
        String pass, passConfirm;

        String expectedText = base.getLocalizeTextForKey(base.getStringValue(param, "key"));
        String notification = base.getStringValue(param, "notification");
        boolean expectedResult = (boolean)param.get("expected");
        boolean confirmAgreement = (boolean) param.get("agreement");

        pass = base.getStringValue(param, "pass");
        try {passConfirm = base.getStringValue(param, "pass2");
        }catch (Exception e){passConfirm = pass;}

        Base.log(1, notification, true);
        Base.log(1, "Test data:", true);
        Base.log(1, "pass: \"" + pass + "\"", true);
        Base.log(1, "pass confirm: \"" + passConfirm + "\"", true);

        Base.log(1, "START TEST", true);
        boolean actualResult = base.user.registration.withData(login, pass, loginConfirm, passConfirm, server, phone, country, name, expectedText, false, confirmAgreement);
        Assert.assertEquals(expectedResult, actualResult, "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"\n");
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
