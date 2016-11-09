package ajaxMobileApp;

import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public  class TestLoginScreen{

    private AndroidDriver driver;

    @BeforeTest
    public void setup() throws MalformedURLException {
        driver = (new AppiumSetup()).getDriver();
    }

    @Test
    public void All_Servers_Exist() {
        // Create objects of pages
        IntroScreen introScreen = new IntroScreen(driver);
        AuthorizationScreen authorizationScreen = new AuthorizationScreen(driver);

        // Go to the authorization page
        introScreen.goToAuthorization();
        authorizationScreen.longTapLoginButton();

        // Check whether all the elements in place
        Assert.assertTrue(authorizationScreen.serverDebug.isDisplayed());
        Assert.assertTrue(authorizationScreen.serverDevelop.isDisplayed());
        Assert.assertTrue(authorizationScreen.serverProduction.isDisplayed());
        Assert.assertTrue(authorizationScreen.serverNewProduction.isDisplayed());
        Assert.assertTrue(authorizationScreen.serverEden.isDisplayed());
    }

    @AfterTest
    public void endSuit() {
        driver.quit();
    }
}
