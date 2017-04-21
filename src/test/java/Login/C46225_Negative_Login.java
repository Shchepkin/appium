package Login;

import org.testng.Assert;
import org.testng.annotations.*;
import pages.Base;

import java.util.Iterator;
import java.util.Map;

/**
 * PRECONDITION
 * - account exist and validated
 * -
 */

public class C46225_Negative_Login {

    private String login, pass, server, expectedText, actual;
    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());
    }

    @DataProvider
    public Iterator<Object[]> dataProviderIterator() {
        return base.getDataProviderIterator("negativeLoginData.json");
    }

    @Test(dataProvider = "dataProviderIterator")
    public void parameters (Map param) {
        Base.log(1, "test started");

        login = param.get("login").toString();
        pass = param.get("pass").toString();
        server = base.getCredsWithKey("server");

        Base.log(1,"comment: " + param.get("comment"));
        Base.log(1,"login: \"" + login + "\"");
        Base.log(1,"password: " + pass + "\"");

        base.loginPage.loginToTheServer(login, pass, server);
        Assert.assertTrue(base.wait.visibilityOfSnackBar(90, true), "SnackBar is not shown");
        Base.log(1, "test finished");
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
