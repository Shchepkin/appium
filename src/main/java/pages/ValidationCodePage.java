package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.Setup;
import utils.Sql;

import java.util.Map;
import java.util.regex.Pattern;

public class ValidationCodePage extends Base{
    public final AppiumDriver driver;
    public boolean autoLoadResult;

    private Map tokenMap;
    private Sql sql = new Sql();

    @AndroidFindBy(id = "com.ajaxsystems:id/smsCode")
    public WebElement smsCode;

    @AndroidFindBy(id = "com.ajaxsystems:id/emailCode")
    public WebElement emailCode;

    @AndroidFindBy(id = "com.ajaxsystems:id/codeResend")
    public WebElement codeResend;

    @AndroidFindBy(id = "com.ajaxsystems:id/cancel")
    public WebElement cancelBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/ok")
    public WebElement okBtn;


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

    public void getAndFillValidationCodes(String row, String value){
        log("Method is started");

        tokenMap = sql.getTokenMap(row, value);
        log(2, "SMS token: " + tokenMap.get("smsToken"));
        log(2, "Email token: " + tokenMap.get("emailToken"));

        log("fill tokens to the fields");
        smsCode.sendKeys(tokenMap.get("smsToken").toString());
        emailCode.sendKeys(tokenMap.get("emailToken").toString());
        log("Method is finished");

    }

    public void getCodeFromEmail(){
        System.out.println("Done");
    }

    public ValidationCodePage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}
