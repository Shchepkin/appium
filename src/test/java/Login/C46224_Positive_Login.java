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

    private String login, pass, server, expectedText, actual;
    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());
    }

    @DataProvider
    public Object[][] dataProviderObjects() {
        return base.getDataProviderObjects("positiveLoginData.json");
    }

    @Test(dataProvider = "dataProviderObjects")
    public void Test (Map param) {
        Base.log(1, "test started");
        login = param.get("login").toString();
        pass = param.get("password").toString();
        server = base.getCredsWithKey("server");

        System.out.println("comment: " + param.get("comment"));
        System.out.println("login: " + param.get("login"));
        System.out.println("password: " + param.get("password"));

        Assert.assertTrue(base.loginPage.loginWithPinCancel(login, pass, server));
        base.loginPage.logOut();
        Base.log(1, "test finished");
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
