package tmp;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.Setup;
import utils.Sql;

import java.util.Map;


public class testSQL {
    private Setup s = new Setup();
    private Sql sql = new Sql();
    private boolean result = false;
    private Map tokenMap;

    @Parameters({"deviceName_", "UDID_", "platformVersion_", "URL_", "appPath_", "locale_"})
    @BeforeClass
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_, String appPath_, String locale_) {
        s.log("Method is started");
    }

    @Test(priority = 1, enabled = true)
    public void get_SELECT() {
        s.log("TEST IS STARTED");
        s.log(3, "print ValidationTokens");
        sql.getDelete("Phone", "%683669947%");
        tokenMap = sql.getTokenMap("Phone", "683669947");
        System.out.println("SMS: " + tokenMap.get("smsToken"));
        System.out.println("Email: " + tokenMap.get("emailToken"));
        s.log("TEST IS FINISHED");
    }


    @Test(priority = 2, enabled = false)
    public void get_DELETE() {
        s.log("Method is started");
        sql.getDelete("Phone", "%1216815329%");
    }




    @AfterClass
    public void endSuit() {
        s.log("Method is finished");
    }
}
