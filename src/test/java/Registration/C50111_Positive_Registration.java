package Registration;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.Base;

/**
 * PRECONDITION
 */

public class C50111_Positive_Registration {

    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());
    }

    @Test()
    public void Full_registration_with_validation () {
        Base.log(1, "START TEST");
        Assert.assertTrue(base.user.registration.fullProcess(), "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"");
        Base.log(1, "test finished successfully, dashboard is shown", true);
        base.getDriver().resetApp();
    }

    @Test()
    public void With_Mistake_In_Email () {
        Base.log(1, "START TEST");
        Assert.assertTrue(base.user.registration.withMistakeInEmail(), "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"");
        Base.log(1, "test finished successfully, dashboard is shown", true);
        base.getDriver().resetApp();
    }

    @Test()
    public void With_Mistake_In_Phone () {
        Base.log(1, "START TEST");
        Assert.assertTrue(base.user.registration.withMistakeInPhone(), "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"");
        Base.log(1, "test finished successfully, dashboard is shown", true);
        base.getDriver().resetApp();
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
