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
        String login = param.get("login").toString();
        String pass = param.get("pass").toString();


        System.out.println(param.get("comment"));
        System.out.println("login   : \"" + param.get("login") + "\"");
        System.out.println("password: \"" + param.get("pass") + "\"");

        Assert.assertTrue(base.loginPage.loginWithPinCancel(login, pass, server));
        base.loginPage.logOut();
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
