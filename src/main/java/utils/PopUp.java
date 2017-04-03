package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.Base;


public class PopUp extends Base{
    private AppiumDriver driver;
    private long start, finish;

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
    public WebElement loadingWindow;

    @AndroidFindBy(id = "com.ajaxsystems:id/progress")
    public WebElement progressImage;

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

    @AndroidFindBy(id = "com.ajaxsystems:id/agreement")
    public WebElement userAgreementCheckbox;

//=====================================================================================

    public PopUp() {}

    public PopUp(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void waitLoadingPopUp(int flag){
        log("Method is started");
        WebDriverWait iWait = new WebDriverWait (driver, 10);
        try {
            // assert is the element displayed on the page
            log(2, "waiting 10 seconds for loading PopUp");
            iWait.until(ExpectedConditions.visibilityOf(loadingWindow));
            switch (flag) {
                case 1:
                    log("loading PopUp is shown, so click Confirm Button");
                    confirmButton.click();
                    break;
                default:
                    log("loading PopUp is shown, so click Cancel Button");
                    cancelButton.click();
                    break;
            }
        }
        catch (NoSuchElementException e) {
            log(4, "NoSuchElementException, loading PopUp is not shown: \n\n\033[31;49m" + e + "\033[39;49m\n");
        }
        catch (TimeoutException e) {
            log(4, "TimeoutException loading PopUp is not shown: \n\n\033[31;49m" + e + "\033[39;49m\n");
        }
    }


    public boolean waitLoaderPopUpWithText(String searchingText, int timer, boolean makeScreenShot) {
        log("Method is started");

        try {
            log(2, "waiting " + timer + " seconds for the element ");
            WebDriverWait iWait = new WebDriverWait(driver, timer);
            iWait.until(ExpectedConditions.textToBePresentInElement(contentText, searchingText));

            log(2, "element is shown with text: \"" + contentText.getText() + "\"");
            result = true;
        } catch (NoSuchElementException e) {
            log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){getScreenShot(driver);}
        } catch (TimeoutException e) {
            log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){getScreenShot(driver);}
        }
        return result;
    }

    public String getContentText() {
        return contentText.getText();
    }
}
