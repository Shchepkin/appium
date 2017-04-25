package Login;

import org.testng.Assert;
import org.testng.annotations.*;
import pages.Base;

import java.util.Iterator;
import java.util.Map;

/**
 * PRECONDITION
 * - account exist and validated
 */

public class C46225_Negative_Login {

    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());
        String server = base.getCredsWithKey("server");

        base.introPage.goToAuthorization();
        base.loginPage.chooseServer(server);
    }

    @DataProvider
    public Iterator<Object[]> dataProviderIterator() {
        return base.getDataProviderIterator("negativeLoginData.json");
    }

    @Test(dataProvider = "dataProviderIterator")
    public void parameters (Map param) {

        String comment = param.get("comment").toString();
        String login = param.get("login").toString();
        String pass = param.get("pass").toString();
        String expectedText;

        Base.log(1, comment, true);
        Base.log(1, "login: \"" + login + "\"", true);
        Base.log(1, "password: \"" + pass + "\"", true);

        System.out.println("");
        base.loginPage.loginToTheServer(login, pass);

        if (login.isEmpty() || pass.isEmpty()){
            expectedText = base.getLocalizeTextForKey("please_fill_in_all_of_the_required_fields");
        }else {
            expectedText = base.getLocalizeTextForKey("Login_bad_credentials0");
        }

        Assert.assertTrue(base.wait.visibilityOfSnackBarWithText(expectedText, 15));
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
