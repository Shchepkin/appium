package Registration;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.Base;

/**
 * PRECONDITION
 * - account exist and validated
 * -
 */

public class C50112_Negative_Registration {

    private Base base;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());
    }

    @Test
    public void With_Email_From_Existing_User () {
        Base.log(1, "START TEST");
        base.getDriver().resetApp();
        Assert.assertFalse(base.user.registration.withExisting.email(false), "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"\n");
    }

    @Test
    public void With_Phone_From_Existing_User () {
        Base.log(1, "START TEST");
        base.getDriver().resetApp();
        Assert.assertFalse(base.user.registration.withExisting.phone(false), "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"\n");
    }

    @Test
    public void With_both_data_from_existing_user () {
        Base.log(1, "START TEST");
        base.getDriver().resetApp();
        Assert.assertFalse(base.user.registration.withExisting.both(false), "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"\n");
    }

    @Test
    public void With_Mistake_In_Phone_And_Email () {
        Base.log(1, "START TEST");
        base.getDriver().resetApp();
        Assert.assertFalse(base.user.registration.withFake.both(false, false), "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"\n");
    }

    @Test
    public void Resend_validation_code_from_loginPage_with_email_and_phone_changing () {
        Base.log(1, "START TEST");
        base.getDriver().resetApp();
        Assert.assertFalse(base.user.registration.withFake.both(true, false), "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"");
    }

    @Test
    public void Resend_validation_code_from_Login_page_with_existing_phone () {
        Base.log(1, "START TEST");
        base.getDriver().resetApp();
        Assert.assertFalse(base.user.registration.withExisting.phone(true), "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"\n");
    }

    @Test
    public void Resend_validation_code_from_Login_page_with_existing_email () {
        Base.log(1, "START TEST");
        base.getDriver().resetApp();
        Assert.assertFalse(base.user.registration.withExisting.email(true), "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"\n");
    }

    @Test
    public void Resend_validation_code_from_Login_page_with_existing_email_and_phone () {
        Base.log(1, "START TEST");
        base.getDriver().resetApp();
        Assert.assertFalse(base.user.registration.withExisting.both(true), "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"\n");
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
