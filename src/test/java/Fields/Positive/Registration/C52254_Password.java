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

public class C52254_Password {
    private Base base;
    private String server, phone, login, name;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());
        Map setting = base.getJsonMapCollection("fieldsPositivePassword.json", "settings");
        server = base.getStringValue(setting, "server");
        login = base.getStringValue(setting, "login");
        phone = base.getStringValue(setting, "phone");
        name = base.getStringValue(setting, "name");
        base.sql.getDelete("Phone", "%" + phone + "%");
    }

    @DataProvider
    public Iterator<Object[]> dataProviderIterator() {return base.getDataProviderIterator("fieldsPositivePassword.json");}

    @Test(dataProvider = "dataProviderIterator")
    public void parameters (Map param) {
        base.getDriver().resetApp();
        String notification = base.getStringValue(param, "notification");
        String pass = base.getStringValue(param, "pass");

        Base.log(1, notification, true);
        Base.log(1, "Test data:", true);
        Base.log(1, "password: \"" + login + "\"", true);

        Base.log(1, "START TEST", true);
        boolean actualResult = base.regPage.registrationProcess(login, pass, login, pass, server, phone, "", name, "", false, true);
        Assert.assertTrue(actualResult, "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"\n");
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
