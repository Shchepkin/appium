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

public class C52257_Name {
    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());

        //init data
        Map settings = base.getJsonMapCollection("fieldsNegativeName.json", "settings");
        String pass = base.getStringValue(settings, "pass");
        String login = base.getStringValue(settings, "login");
        String phone = base.getStringValue(settings, "phone");
        String server = base.getStringValue(settings, "server");
        String country = base.getStringValue(settings, "country");

        //actions
        base.introPage.setServer(server);
        base.nav.gotoPage.registration();
        base.regPage.fillFields("", login, pass, login, pass, phone, country);
        base.regPage.confirmAgreementCheckBox();
        base.sql.getDelete("Login", login);
    }

    @DataProvider
    public Iterator<Object[]> dataProviderIterator() {return base.getDataProviderIterator("fieldsNegativeName.json");}

    @Test(dataProvider = "dataProviderIterator")
    public void parameters (Map param) {
        String key = base.getStringValue(param, "key");
        String expectedText = base.getLocalizeTextForKey(key);
        String notification = base.getStringValue(param, "notification");
        String name = base.getStringValue(param, "name");

        Base.log(1, notification, true);
        Base.log(1, "Test data:", true);
        Base.log(1, "name: \"" + name + "\"", true);

        Base.log(1, "START TEST", true);
        base.regPage.fillFields(name, "", "", "", "", "", "");

        if (key.equalsIgnoreCase("character_limit_exceeded")){
            Assert.assertTrue(base.wait.text(expectedText, 10, true), "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"\n");
        }else {
            base.nav.tapButton.next();
            Assert.assertTrue(base.wait.text(expectedText, 20, true), "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"\n");
        }
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
