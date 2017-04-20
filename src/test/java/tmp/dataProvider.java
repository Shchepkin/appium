package tmp;

import org.testng.annotations.*;
import pages.Base;

import java.util.ArrayList;
import java.util.Map;

/**
 * PRECONDITION
 * - one hub
 */
public class dataProvider {
    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);

    }

    @Test(dataProvider = "dataMethod")
    public void negativeLogin (Map param) {
        System.out.println("Start test");
        System.out.println("comment: " + param.get("comment"));
        System.out.println("login: " + param.get("login"));
        System.out.println("password: " + param.get("password"));
    }

    @DataProvider
    public Object[][] dataMethod() {
        return base.getDataProviderValues("negativeLoginData.json");
    }

    @AfterClass
    public void endSuit() {
        System.out.println("\n@AfterClass");
    }
}
