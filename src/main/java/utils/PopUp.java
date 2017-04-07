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


public class PopUp{

//================================ Loading ===========================================

    @AndroidFindBy(id = "com.ajaxsystems:id/error_x")
    public WebElement errorPic;

    @AndroidFindBy(id = "com.ajaxsystems:id/content_text")
    public WebElement contentText;

    @AndroidFindBy(id = "com.ajaxsystems:id/timerText")
    private WebElement timerText;

    @AndroidFindBy(id = "com.ajaxsystems:id/message")
    private WebElement message;

    @AndroidFindBy(id = "com.ajaxsystems:id/loading")
    public WebElement loadingWindow;

    @AndroidFindBy(id = "com.ajaxsystems:id/progress")
    private WebElement progressImage;

    @AndroidFindBy(id = "com.ajaxsystems:id/shadow")
    private WebElement shadow;

//================================ dialogMessage =====================================

    @AndroidFindBy(id = "com.ajaxsystems:id/dialogMessage")
    private WebElement dialogMessage;

//================================ QR ================================================
    @AndroidFindBy(id = "com.ajaxsystems:id/zxing_viewfinder_view")
    private WebElement qrFinderView;

    @AndroidFindBy(id = "com.ajaxsystems:id/zxing_status_view")
    private WebElement qrStatusView;

//================================= Toast & Snack ====================================

    @AndroidFindBy(id = "com.ajaxsystems:id/snackbar_text")
    private WebElement snackBar;

    @AndroidFindBy(id = "com.ajaxsystems:id/message")
    private WebElement toast;

//================================ desc_container ====================================

    @AndroidFindBy(id = "com.android.packageinstaller:id/desc_container")
    private WebElement descContainer;

    @AndroidFindBy(id = "com.android.packageinstaller:id/permission_deny_button")
    private WebElement denyButton;

    @AndroidFindBy(id = "com.android.packageinstaller:id/permission_allow_button")
    private WebElement allowButton;

//================================ User_Agreement_Dialog ==============================

    @AndroidFindBy(id = "com.ajaxsystems:id/web")
    private WebElement userAgreement;

    @AndroidFindBy(id = "com.ajaxsystems:id/agreement")
    private WebElement userAgreementCheckbox;

//----------------------------------------------------------------------------------------------------------------------
    private final Base $;
    private final AppiumDriver driver;
    private boolean result;
    private long start, finish;


    public PopUp(Base base) {
        $ = base;
        this.driver = $.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
//----------------------------------------------------------------------------------------------------------------------

    public void waitLoadingPopUp(int flag){
        Base.log("Method is started");
        WebDriverWait iWait = new WebDriverWait (driver, 10);
        try {
            // assert is the element displayed on the page
            Base.log(2, "waiting 10 seconds for loading PopUp");
            iWait.until(ExpectedConditions.visibilityOf(loadingWindow));
            switch (flag) {
                case 1:
                    Base.log("loading PopUp is shown, so click Confirm Button");
                    $.nav.confirmIt();
                    break;
                default:
                    Base.log("loading PopUp is shown, so click Cancel Button");
                    $.nav.cancelIt();
                    break;
            }
        }
        catch (NoSuchElementException e) {
            Base.log(4, "NoSuchElementException, loading PopUp is not shown: \n\n\033[31;49m" + e + "\033[39;49m\n");
        }
        catch (TimeoutException e) {
            Base.log(4, "TimeoutException loading PopUp is not shown: \n\n\033[31;49m" + e + "\033[39;49m\n");
        }
    }


    public boolean waitLoaderPopUpWithText(String searchingText, int timer, boolean makeScreenShot) {
        Base.log("Method is started");

        try {
            Base.log(2, "waiting " + timer + " seconds for the element ");
            WebDriverWait iWait = new WebDriverWait(driver, timer);
            iWait.until(ExpectedConditions.textToBePresentInElement(contentText, searchingText));

            Base.log(2, "element is shown with text: \"" + contentText.getText() + "\"");
            result = true;
        } catch (NoSuchElementException e) {
            Base.log(4, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){$.getScreenShot();}
        } catch (TimeoutException e) {
            Base.log(4, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            result = false;
            if (makeScreenShot){$.getScreenShot();}
        }
        return result;
    }

    public String getContentText() {
        return contentText.getText();
    }

    public WebElement getSnackBar() {
        return snackBar;
    }
}
