package utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.Base;

public class PopUp{

//================================ Loading ===========================================

    @AndroidFindBy(id = "com.ajaxsystems:id/error_x")
    public WebElement errorPic;

    @AndroidFindBy(id = "com.ajaxsystems:id/content_text")
    private WebElement contentTextElement;

    @AndroidFindBy(id = "com.ajaxsystems:id/timerText")
    private WebElement timerText;

    @AndroidFindBy(id = "com.ajaxsystems:id/message")
    private WebElement message;

    @AndroidFindBy(id = "com.ajaxsystems:id/loading")
    private WebElement loadingWindow;

    @AndroidFindBy(id = "com.ajaxsystems:id/loaderLogo")
    private WebElement loaderLogo;

    @AndroidFindBy(id = "com.ajaxsystems:id/progress")
    private WebElement progressImage;

    @AndroidFindBy(id = "com.ajaxsystems:id/gravity")
    private WebElement addNewDevicePopUp;

    @AndroidFindBy(id = "com.ajaxsystems:id/action_bar_root")
    private WebElement actionBarRoot;

    public WebElement getAddNewDevicePopUp() {
        return addNewDevicePopUp;
    }

    public WebElement getLoadingWindow() {
        return loadingWindow;
    }

//================================ dialogMessage =====================================

    @AndroidFindBy(id = "com.ajaxsystems:id/dialogMessage")
    private WebElement dialogMessage;

    @AndroidFindBy(id = "com.ajaxsystems:id/codeLayout")
    private WebElement countryCodeLayout;

    @AndroidFindBy(id = "com.ajaxsystems:id/code")
    private WebElement countryCode;

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
    private Base base;
    private AndroidDriver driver;
    private long start, finish;


    public PopUp(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
//----------------------------------------------------------------------------------------------------------------------
    public String getContentText() {
    return contentTextElement.getText();
}
    public WebElement getLoaderLogo() {
        return loaderLogo;
    }
    public WebElement getContentTextElement() {
        return contentTextElement;
    }
    public WebElement getSnackBarElement() {
        return snackBar;
    }
    public String  getSnackBarText() {
        return snackBar.getText();
    }

    public void waitLoadingPopUp(int flag){
        Base.log(4, "Method is started");
        WebDriverWait iWait = new WebDriverWait (driver, 10);
        try {
            // assert is the element displayed on the page
            Base.log(4, "waiting 10 seconds for loading PopUp");
            iWait.until(ExpectedConditions.visibilityOf(loadingWindow));
            switch (flag) {
                case 1:
                    Base.log(1, "loading PopUp is shown, so click Confirm Button");
                    base.nav.confirmIt();
                    break;
                default:
                    Base.log(1, "loading PopUp is shown, so click Cancel Button");
                    base.nav.cancelIt();
                    break;
            }
        }
        catch (NoSuchElementException e) {
            Base.log(2, "NoSuchElementException, loading PopUp is not shown: \n\n\033[31;49m" + e + "\033[39;49m\n");
        }
        catch (TimeoutException e) {
            Base.log(2, "TimeoutException loading PopUp is not shown: \n\n\033[31;49m" + e + "\033[39;49m\n");
        }
        Base.log(4, "Method is finished");
    }

    public boolean waitLoaderPopUpWithText(String searchingText, int timer, boolean makeScreenShot) {
        try {
            Base.log(4, "waiting " + timer + " seconds for the element ");
            WebDriverWait iWait = new WebDriverWait(driver, timer);
            iWait.until(ExpectedConditions.textToBePresentInElement(contentTextElement, searchingText));

            Base.log(4, "element is shown with text: \"" + contentTextElement.getText() + "\"");
            return true;

        } catch (NoSuchElementException e) {
            Base.log(2, "No Such Element Exception, element is not shown:\n\n" + e + "\n");
            if (makeScreenShot){base.getScreenShot();}
            return false;

        } catch (TimeoutException e) {
            Base.log(2, "Timeout Exception, element is not shown:\n\n" + e + "\n");
            if (makeScreenShot){base.getScreenShot();}
            return false;
        }
    }

    public void setPhoneCountryCode(String country) {
        countryCodeLayout.click();
        base.nav.scroll.toElementWith.text(country, true);
        Base.log(1, "set country \"" + country + "\", code value is \"" + countryCode.getText() + "\"", true);
    }

}
