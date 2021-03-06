package Fields.Positive.Registration;

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

public class C52255_Email {
    private Base base;
    private String server, phone, pass, name;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());
        Map setting = base.getJsonMapCollection("fieldsPositiveEmail.json", "settings");
        server = base.getStringValue(setting, "server");
        phone = base.getStringValue(setting, "phone");
        pass = base.getStringValue(setting, "pass");
        name = base.getStringValue(setting, "name");
        base.sql.getDelete("Phone", "%" + phone + "%");
    }

    @DataProvider
    public Iterator<Object[]> dataProviderIterator() {return base.getDataProviderIterator("fieldsPositiveEmail.json");}

    @Test(dataProvider = "dataProviderIterator")
    public void parameters (Map param) {
        base.getDriver().resetApp();
        String notification = base.getStringValue(param, "notification");
        String login = base.getStringValue(param, "login");

        Base.log(1, notification, true);
        Base.log(1, "Test data:", true);
        Base.log(1, "login: \"" + login + "\"", true);

        Base.log(1, "START TEST", true);
        boolean actualResult = base.regPage.registrationProcess(login, pass, login, pass, server, phone, "", name, "", false, true);
        Assert.assertTrue(actualResult, "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"\n");
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
