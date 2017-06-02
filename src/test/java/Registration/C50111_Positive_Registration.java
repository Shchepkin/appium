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

    @Test
    public void Full_registration_with_validation () {
        Base.log(1, "START TEST");
        base.getDriver().resetApp();
        Assert.assertTrue(base.user.registration.fullProcess(false), "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"");
        Base.log(1, "test finished successfully, dashboard is shown", true);
    }

    @Test
    public void Email_contains_64_symbols () {
        Base.log(1, "START TEST");
        base.getDriver().resetApp();
        Assert.assertTrue(base.user.registration.withSpecificEmail("emailwithmistakeqweqweqweq12345678901hgfhghfhfdhjhg23456789@i.ua", ""), "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"");
        Base.log(1, "test finished successfully, dashboard is shown", true);
    }

    @Test
    public void Repeat_After_Canceling_Validation () {
        Base.log(1, "START TEST");
        base.getDriver().resetApp();
        Assert.assertTrue(base.user.registration.fullProcess(true), "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"");
        Base.log(1, "test finished successfully, dashboard is shown", true);
    }

    @Test
    public void With_validation_from_LoginPage_after_canceling () {
        Base.log(1, "START TEST");
        base.getDriver().resetApp();
        Assert.assertTrue(base.user.registration.validateWithExistingCodes(), "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"");
        Base.log(1, "test finished successfully, dashboard is shown", true);
    }

    @Test
    public void With_Mistake_In_Email () {
        Base.log(1, "START TEST");
        base.getDriver().resetApp();
        Assert.assertTrue(base.user.registration.withFake.email(false, true), "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"");
        Base.log(1, "test finished successfully, dashboard is shown", true);
    }

    @Test
    public void Resend_validation_code_from_login_with_email_changing () {
        Base.log(1, "START TEST");
        base.getDriver().resetApp();
        Assert.assertTrue(base.user.registration.withFake.email(true, false), "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"");
        Base.log(1, "test finished successfully, dashboard is shown", true);
    }

    @Test
    public void With_Mistake_In_Phone () {
        Base.log(1, "START TEST");
        base.getDriver().resetApp();
        Assert.assertTrue(base.user.registration.withFake.phone(false, true), "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"");
        Base.log(1, "test finished successfully, dashboard is shown", true);
    }

    @Test
    public void Resend_validation_code_from_login_with_phone_changing () {
        Base.log(1, "START TEST");
        base.getDriver().resetApp();
        Assert.assertTrue(base.user.registration.withFake.phone(true, true), "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"");
        Base.log(1, "test finished successfully, dashboard is shown", true);
    }


    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
