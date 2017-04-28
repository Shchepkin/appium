package tmp;

import org.testng.annotations.*;
import pages.Base;

import java.util.Iterator;
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

    @DataProvider
    public Object[][] dataProviderObjects() {
        return base.getDataProviderObjects("negativeLoginData.json");
    }


    @Test(dataProvider = "dataProviderObjects")
    public void negativeLoginObjects (Map param) {
        System.out.println("Start test");
        System.out.println("comment: " + param.get("comment"));
        System.out.println("login: " + param.get("login"));
        System.out.println("password: " + param.get("password"));
    }

    @DataProvider
    public Iterator<Object[]> dataProviderIterator() {
        return base.getDataProviderIterator("negativeLoginData.json");
    }


    @Test(dataProvider = "dataProviderIterator")
    public void negativeLoginIterator (Map param) {
        System.out.println("Start test");
        System.out.println("comment: " + param.get("comment"));
        System.out.println("login: " + param.get("login"));
        System.out.println("password: " + param.get("password"));
    }

    @AfterClass
    public void endSuit() {
        System.out.println("\n@AfterClass");
    }
}
