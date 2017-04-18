package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ValidationCodePage{

    @AndroidFindBy(id = "com.ajaxsystems:id/smsCodeField")
    private WebElement smsCodeField;
    public WebElement getSmsCodeField() {
        return smsCodeField;
    }

    @AndroidFindBy(id = "com.ajaxsystems:id/emailCodeField")
    private WebElement emailCodeField;

    @AndroidFindBy(id = "com.ajaxsystems:id/codeResend")
    private WebElement codeResend;

    @AndroidFindBy(id = "com.ajaxsystems:id/cancel")
    private WebElement cancelBtn;

//----------------------------------------------------------------------------------------------------------------------
    private final Base base;
    private final AndroidDriver driver;
    private boolean result;

    public ValidationCodePage(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.TIMEOUT, TimeUnit.SECONDS), this);
    }
//----------------------------------------------------------------------------------------------------------------------

    public void getAndFillValidationCodes(String row, String value){
        Base.log(4, "Method is started");

        Map tokenMap = base.sql.getTokenMap(row, value);
        Base.log(4, "SMS token: " + tokenMap.get("smsToken"));
        Base.log(4, "Email token: " + tokenMap.get("emailToken"));

        Base.log(1, "fill tokens to the fields");
        smsCodeField.sendKeys(tokenMap.get("smsToken").toString());
        emailCodeField.sendKeys(tokenMap.get("emailToken").toString());
        Base.log(4, "Method is finished");

    }


/*
    public void autoLoadCode(WebElement element, int timer){
        System.out.print("  - Create matcher for checking  ... ");
        String regularExpression = "[\\d]{6}";
        String fieldValue = element.getText();
        Pattern pattern = Pattern.compile(regularExpression);
        System.out.println("Done");

        System.out.print("  - Wait until loading validation code from SMS ...      ");
        while (!pattern.matcher(fieldValue).matches() & timer > 0) {

            fieldValue = element.getText();
            timer--;
//debug            System.out.println("" + timer + "  " + element.getText() + " --> " + pattern.matcher(fieldValue).matches());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        boolean autoLoadResult = timer > 0 ? true : false;
        Assert.assertTrue(autoLoadResult, "\nTimeout exception\nValue of SMS field is not valid: [" + fieldValue + "]\n");
        System.out.println("Done");
    }
*/

}
