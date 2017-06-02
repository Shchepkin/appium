package Fields.Negative;

import org.testng.Assert;
import org.testng.annotations.*;
import pageObjects.Base;

import java.util.Iterator;
import java.util.Map;

public class C52261_All_fields_are_empty {
    private Base base;
    private String expectedText;

    @Parameters({ "deviceName_" })
    @BeforeClass
    public void init(String deviceName_){
        base = new Base(deviceName_);
        base.initPageObjects(base.getDriver());
        expectedText = base.getLocalizeTextForKey("please_check_in_all_of_the_required_fields");
    }

    @Test
    public void Agreement_checkbox_is_confirmed () {
        Base.log(1, "START TEST");
        base.getDriver().resetApp();
        boolean actualResult = base.user.registration.withData("", "", "", "", "", "", "", "", expectedText, false, true);
        Assert.assertEquals(false, actualResult, "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"\n");
    }

    @Test
    public void Agreement_checkbox_is_not_confirmed () {
        Base.log(1, "START TEST");
        base.getDriver().resetApp();
        boolean actualResult = base.user.registration.withData("", "", "", "", "", "", "", "", expectedText, false, false);
        Assert.assertEquals(false, actualResult, "Test failed, more info you can find in logFile: \"" + Base.getLogFile() + "\"\n");
    }

    @AfterClass
    public void endSuit() {
        base.getDriver().quit();
    }
}
