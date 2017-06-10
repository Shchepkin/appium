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

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());

        //init data
        Map settings = base.getJsonMapCollection("fieldsNegativePassword.json", "settings");
        String name = base.getStringValue(settings, "name");
        String phone = base.getStringValue(settings, "phone");
        String server = base.getStringValue(settings, "server");
        String country = base.getStringValue(settings, "country");
        String login = base.getStringValue(settings, "login");

        //actions
        base.introPage.setServer(server);
        base.nav.gotoPage.registration();
        base.regPage.fillFields(name, login, "", login, "", phone, country);
        base.regPage.confirmAgreementCheckBox();
        base.sql.getDelete("Login", login);
    }

    @DataProvider
    public Iterator<Object[]> dataProviderIterator() {return base.getDataProviderIterator("fieldsNegativePassword.json");}

    @Test(dataProvider = "dataProviderIterator")
    public void parameters (Map param) {
        String pass, passConfirm;
        String expectedText = base.getLocalizeTextForKey(base.getStringValue(param, "key"));
        String notification = base.getStringValue(param, "notification");

        pass = base.getStringValue(param, "pass");
        try {passConfirm = base.getStringValue(param, "pass2");
        }catch (Exception e){passConfirm = pass;}

        Base.log(1, notification, true);
        Base.log(1, "Test data:", true);
        Base.log(1, "pass: \"" + pass + "\"", true);
        Base.log(1, "pass confirm: \"" + passConfirm + "\"", true);

        Base.log(1, "START TEST", true);
        base.regPage.fillFields("", "", pass, "", passConfirm, "", "");
        base.nav.tapButton.next();

        Assert.assertTrue(base.wait.text(expectedText, 20, true), "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"\n");
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
