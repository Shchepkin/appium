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

    private String login, pass, server, expectedText, actualText;
    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());
        server = base.getCredsWithKey("server");

        base.introPage.goToAuthorization();
        base.loginPage.chooseServer(server);
    }

    @DataProvider
    public Iterator<Object[]> dataProviderIterator() {
        return base.getDataProviderIterator("negativeLoginData.json");
    }

    @Test(dataProvider = "dataProviderIterator")
    public void parameters (Map param) {

        login = param.get("login").toString();
        pass = param.get("pass").toString();

        System.out.println(param.get("comment"));
        System.out.println("login: \"" + login + "\"");
        System.out.println("password: \"" + pass + "\"");

        System.out.println("");
        base.loginPage.loginToTheServer(login, pass);

        Assert.assertTrue(base.wait.visibilityOfSnackBar(90, true), "SnackBar is not shown");

        if (login.isEmpty() || pass.isEmpty()){
            expectedText = base.getLocalizeTextForKey("please_fill_in_all_of_the_required_fields");
        }else {
            expectedText = base.getLocalizeTextForKey("Login_bad_credentials0");
        }

        actualText = base.popUp.getSnackBarText();

        System.out.println("expected: \"" + expectedText + "\"");
        System.out.println("actual: \"" + actualText + "\"");

        Assert.assertEquals(actualText, expectedText);
        System.out.println("\n");
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
