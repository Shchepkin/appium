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
    private Map settings, contryMap;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());

        //init data
        settings = base.getJsonMapCollection("fieldsPhoneNegative.json", "settings");
        contryMap = base.getJsonMapCollection("deviceData.json", "country");
        String pass = base.getStringValue(settings, "pass");
        String name = base.getStringValue(settings, "name");
        String login = base.getStringValue(settings, "login");
        String server = base.getStringValue(settings, "server");

        //actions
        base.introPage.setServer(server);
        base.nav.gotoPage.registration();
        base.regPage.fillFields(name, login, pass, login, pass, "", "");
        base.regPage.confirmAgreementCheckBox();
        base.sql.getDelete("Login", login);
    }

    @DataProvider
    public Iterator<Object[]> dataProviderIterator() {return base.getDataProviderIterator("fieldsPhoneNegative.json");}

    @Test(dataProvider = "dataProviderIterator")
    public void parameters (Map param) {
        String phone = base.getStringValue(param, "phone");
        String country = contryMap.get(base.getStringValue(param, "country")).toString();
        String notification = base.getStringValue(param, "notification");
        String expectedText = base.getLocalizeTextForKey(base.getStringValue(param, "key"));

        base.sql.getDelete("Phone", phone);

        Base.log(1, notification, true);
        Base.log(1, "Test data:", true);
        Base.log(1, "phone: \"" + phone + "\"", true);
        Base.log(1, "country: \"" + country + "\"", true);

        Base.log(1, "START TEST", true);
        base.regPage.fillFields("", "", "", "", "", phone, country);
        base.nav.tapButton.next();
        Assert.assertTrue(base.wait.text(expectedText, 20, true), "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"\n");
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
