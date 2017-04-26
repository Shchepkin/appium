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

public class C46224_Positive_Login {

    private String server;
    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());
        server = base.getCredsWithKey("server");
    }

    @DataProvider
    public Iterator<Object[]> dataProviderIterator() {
        return base.getDataProviderIterator("positiveLoginData.json");
    }

    @Test(dataProvider = "dataProviderIterator")
    public void parameters (Map param) {
        String comment = param.get("comment").toString();
        String login = param.get("login").toString();
        String pass = param.get("pass").toString();


        Base.log(1, comment, true);
        Base.log(1, "server: \"" + server + "\"", true);
        Base.log(1, "login: \"" + login + "\"", true);
        Base.log(1, "password: \"" + pass + "\"", true);

        Assert.assertTrue(base.loginPage.loginWithPinCancel(login, pass, server));

        Base.log(1, "reset App");
        base.getDriver().resetApp();
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
