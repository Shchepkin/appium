package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.io.IOException;


public class PopUp {
    public final AppiumDriver driver;


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

    @AndroidFindBy(id = "com.ajaxsystems:id/ok")
    public WebElement okBtnd;

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

//=====================================================================================

    public PopUp(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void waitPopUps () {
        boolean loading = false;
        boolean desc = false;
        for (int i = 0; i < 5; i++) {
            WebDriverWait iWait = new WebDriverWait (driver, 1);

            Reporter.log("== Wait loadingWin", true);
            if (loading) {
                Reporter.log("> loadingWin - already shown early", true);
            } else {
                try {
                    // assert is the element displayed on the page
                    System.out.println(iWait.until(ExpectedConditions.visibilityOf(loadingWin)));
                    System.out.println("Element is displayed. " + loadingWin.getAttribute("id"));
                    cancelBtn.click();
                    loading = true;

                } catch (NoSuchElementException e) {
                    Reporter.log("> loadingWin - NoSuchElementException", true);
                }
            }


            Reporter.log("== descContainer", true);
            if (desc) {
                Reporter.log("> descContainer - already shown early", true);
            }else {
                try {
                    // assert is the element displayed on the page
                    System.out.println(iWait.until(ExpectedConditions.visibilityOf(descContainer)));
                    System.out.println("Element is displayed. " + descContainer.getAttribute("id"));
                    allowButton.click();
                    desc = true;

                } catch (NoSuchElementException e) {
                    Reporter.log("> descContainer - NoSuchElementException", true);
                }
            }
        }
    }
}
