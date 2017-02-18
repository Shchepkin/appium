package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.io.IOException;


public class PopUp {
    private Setup s = new Setup();
    private AndroidDriver driver;
    public boolean result = false;

//================================ Loading ===========================================

    @AndroidFindBy(id = "com.ajaxsystems:id/error_x")
    public WebElement errorPic;

    @AndroidFindBy(id = "com.ajaxsystems:id/content_text")
    public WebElement contentText;

    @AndroidFindBy(id = "com.ajaxsystems:id/timerText")
    public WebElement timerText;

    @AndroidFindBy(id = "com.ajaxsystems:id/message")
    public WebElement message;

    @AndroidFindBy(id = "com.ajaxsystems:id/loading")
    public WebElement loadingWin;

    @AndroidFindBy(id = "com.ajaxsystems:id/progress")
    public WebElement progressCircle;

    @AndroidFindBy(id = "com.ajaxsystems:id/shadow")
    public WebElement shadow;

    @AndroidFindBy(id = "com.ajaxsystems:id/cancel")
    public WebElement cancelBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/cancel_button")
    public WebElement cancelButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/ok")
    public WebElement okBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/confirm_button")
    public WebElement confirmButton;

//================================ dialogMessage =====================================

    @AndroidFindBy(id = "com.ajaxsystems:id/dialogMessage")
    public WebElement dialogMessage;

//================================ QR ================================================
    @AndroidFindBy(id = "com.ajaxsystems:id/zxing_viewfinder_view")
    public WebElement qrFinderView;

    @AndroidFindBy(id = "com.ajaxsystems:id/zxing_status_view")
    public WebElement qrStatusView;

//================================= Toast & Snack ====================================

    @AndroidFindBy(id = "com.ajaxsystems:id/snackbar_text")
    public WebElement snackBar;

    @AndroidFindBy(id = "com.ajaxsystems:id/message")
    public WebElement toast;

//================================ desc_container ====================================

    @AndroidFindBy(id = "com.android.packageinstaller:id/desc_container")
    public WebElement descContainer;

    @AndroidFindBy(id = "com.android.packageinstaller:id/permission_deny_button")
    public WebElement denyButton;

    @AndroidFindBy(id = "com.android.packageinstaller:id/permission_allow_button")
    public WebElement allowButton;

//================================ User_Agreement_Dialog ==============================

    @AndroidFindBy(id = "com.ajaxsystems:id/web")
    public WebElement userAgreement;

//=====================================================================================

    public PopUp() {

    }

    public PopUp(AndroidDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void waitLoadingPopUp(int flag){
        s.log("Method is started");
        WebDriverWait iWait = new WebDriverWait (driver, 10);
        try {
            // assert is the element displayed on the page
            s.log(2, "waiting 10 seconds for loading PopUp");
            iWait.until(ExpectedConditions.visibilityOf(loadingWin));
            switch (flag) {
                case 1:
                    s.log("loading PopUp is shown, so click Confirm Button");
                    confirmButton.click();
                    break;
                default:
                    s.log("loading PopUp is shown, so click Cancel Button");
                    cancelButton.click();
                    break;
            }
        }
        catch (NoSuchElementException e) {
            s.log(4, "NoSuchElementException, loading PopUp is not shown: \n\n\033[31;49m" + e + "\033[39;49m\n");
        }
        catch (TimeoutException e) {
            s.log(4, "TimeoutException loading PopUp is not shown: \n\n\033[31;49m" + e + "\033[39;49m\n");
        }
    }

    public void waitPopUps () {
        s.log("Method is started");
        boolean loading = false;
        boolean desc = false;
        WebDriverWait iWait = new WebDriverWait (driver, 1);
        for (int i = 0; i < 10; i++) {
            s.log("Wait loading PopUp");
            if (loading) {
//                Reporter.log("> loadingWin - already shown early", true);
            } else {
                try {
                    // assert is the element displayed on the page
                    iWait.until(ExpectedConditions.visibilityOf(loadingWin));
                    Reporter.log("> cancelButton.click()", true);
                    cancelButton.click();
                    Reporter.log("> loading = true", true);
                    loading = true;

                } catch (NoSuchElementException e) {
                    Reporter.log("> loadingWin - NoSuchElementException", true);
                }
                catch (TimeoutException e) {
                    Reporter.log("> descContainer - TimeoutException\n" + e, true);
                }
            }
            /*
            Reporter.log("== descContainer", true);
            if (desc) {
                Reporter.log("> descContainer - already shown early", true);
            }else {
                try {
                    // assert is the element displayed on the page
                    iWait.until(ExpectedConditions.visibilityOf(descContainer));
                    allowButton.click();
                    desc = true;

                } catch (NoSuchElementException e) {
                    Reporter.log("> descContainer - NoSuchElementException", true);
                } catch (TimeoutException e) {
                    Reporter.log("> descContainer - TimeoutException" + e, true);
                }

            }
            */
        }
    }
}
