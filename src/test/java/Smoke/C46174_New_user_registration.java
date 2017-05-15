package Smoke;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.*;

/**
 * PRECONDITION
 * - gallery on device must have at least 3 pics
 */

public class C46174_New_user_registration {
    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());
    }

    @Test()
    public void Positive() {
        Base.log(1, "START TEST");
        Assert.assertTrue(base.user.registration.fullProcess(), "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"");
        Base.log(1, "test finished successfully, dashboard is shown", true);
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
