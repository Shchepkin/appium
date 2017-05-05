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

    private String userName, login, pass, phone, server;

    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        Base.log(3, "\nSTART TEST\n");
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());
    }

    @Test(priority = 1, enabled = true)
    public void With_Validation() {
        Assert.assertTrue(base.user.registration.fullProcess());
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
