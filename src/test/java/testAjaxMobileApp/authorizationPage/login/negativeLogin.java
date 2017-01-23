package testAjaxMobileApp.authorizationPage.login;

import ajaxMobileApp.*;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.net.MalformedURLException;

public class negativeLogin {
    private AndroidDriver driver;
    private IntroPage introPage;
    private AuthorizationPage authorizationPage;
    private Check assertion;
    private String expected;
    private String actual;

    @Parameters({ "deviceName_","UDID_","platformVersion_", "URL_" })
    @BeforeClass
    public void setup(String deviceName_, String UDID_, String platformVersion_, String URL_) throws MalformedURLException, InterruptedException {
        AppiumSetup appiumSetup = new AppiumSetup(deviceName_, UDID_, platformVersion_, URL_);
        driver = appiumSetup.getDriver();

        // Create objects of pages
        introPage = new IntroPage(driver);
        authorizationPage = new AuthorizationPage(driver);

        // Create assertion object
        assertion = new Check(driver);

        // Go to the authorization page
        introPage.goToAuthorization();
        authorizationPage.longTapLoginButton();
        authorizationPage.serverDevelop.click();
    }

    @Test()
    public void Email_and_Password_is_empty() {
        authorizationPage.loginField.sendKeys("");
        authorizationPage.passwordField.sendKeys("");
        authorizationPage.loginBtn.click();
        waitElement(authorizationPage.snackBar);
        expected = "Будь ласка, заповніть усі поля";
        actual = authorizationPage.snackBar.getText();
        Assert.assertEquals(expected, actual);
    }

    @Test()
    public void Email_is_empty() {
        authorizationPage.loginField.sendKeys("");
        authorizationPage.passwordField.sendKeys("qwe123");
        authorizationPage.loginBtn.click();
        waitElement(authorizationPage.snackBar);
        expected = "Будь ласка, заповніть усі поля";
        actual = authorizationPage.snackBar.getText();
        Assert.assertEquals(expected, actual);
    }

    @Test()
    public void Password_is_empty() {
        authorizationPage.loginField.sendKeys("ajax1@i.ua");
        authorizationPage.passwordField.sendKeys("");
        authorizationPage.loginBtn.click();
        waitElement(authorizationPage.snackBar);
        expected = "Будь ласка, заповніть усі поля";
        actual = authorizationPage.snackBar.getText();
        Assert.assertEquals(expected, actual);
    }

    @Test()
    public void Email_includes_spaces() {
        authorizationPage.loginField.sendKeys("aj ax1@i.u a");
        authorizationPage.passwordField.sendKeys("qwe123");
        authorizationPage.loginBtn.click();
        waitElement(authorizationPage.snackBar);
        expected = "Невірний логін або пароль";
        actual = authorizationPage.snackBar.getText();
        Assert.assertEquals(expected, actual);
    }

    @Test()
    public void Password_includes_spaces() {
        authorizationPage.loginField.sendKeys("ajax1@i.ua");
        authorizationPage.passwordField.sendKeys("q we1 23");
        authorizationPage.loginBtn.click();
        waitElement(authorizationPage.snackBar);
        expected = "Невірний логін або пароль";
        actual = authorizationPage.snackBar.getText();
        Assert.assertEquals(expected, actual);
    }

    @Test()
    public void Capital_letters_in_Password() {
        authorizationPage.loginField.sendKeys("ajax1@i.ua");
        authorizationPage.passwordField.sendKeys("QwE123");
        authorizationPage.loginBtn.click();
        waitElement(authorizationPage.snackBar);
        expected = "Невірний логін або пароль";
        actual = authorizationPage.snackBar.getText();
        Assert.assertEquals(expected, actual);
    }

    public void waitElement (WebElement element) {
            WebDriverWait iWait = new WebDriverWait (driver, 60);
            iWait.until(ExpectedConditions.visibilityOf(element));
    }


    @AfterClass
    public void endSuit() {
        driver.quit();
    }
}
