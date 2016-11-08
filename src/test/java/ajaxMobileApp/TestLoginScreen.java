package ajaxMobileApp;

import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

/**
 * Created by installer on 11/7/16.
 */
public class TestLoginScreen {

    public static AndroidDriver driver;
    AppiumSetup appiumSetup = new AppiumSetup(driver);

    @BeforeTest
    public void setup() throws MalformedURLException {
        appiumSetup.runAppiumServer();
    }

    @Test
    public void All_Servers_Exist() {
        // Create objects of pages
        IntroScreen introScreen = new IntroScreen(driver);
        AuthorizationScreen authorizationScreen = new AuthorizationScreen(driver);

        // Go to the authorization page
        introScreen.goToAuthorization();
        authorizationScreen.longTapLoginButton();
        Assert.assertTrue(authorizationScreen.serverDebug.isDisplayed());
        Assert.assertTrue(authorizationScreen.serverDevelop.isDisplayed());
        Assert.assertTrue(authorizationScreen.serverProduction.isDisplayed());
        Assert.assertTrue(authorizationScreen.serverNewProduction.isDisplayed());
        Assert.assertTrue(authorizationScreen.serverEden.isDisplayed());
    }

    /*
        // Find and tap authorization button at start window
        driver.findElementById("com.ajaxsystems:id/login").click();
        System.out.println("Find and tap authorization button at start window");

        // Find and fill login field with login text
        driver.findElementById("com.ajaxsystems:id/login").sendKeys("ajx.00@yandex.ru");
        System.out.println("Find and fill login field with login text");

        // Find and fill password field with password text
        driver.findElementById("com.ajaxsystems:id/password").sendKeys("1");
        System.out.println("Find and fill password field with password text");

        // Find and long tap authorization button for opening servers' menu
        WebElement button = driver.findElementById("com.ajaxsystems:id/next");
        driver.tap(1, button,3000);
        System.out.println("Find and long tap authorization button for opening servers' menu");

        // Choice a server
        driver.findElementById("com.ajaxsystems:id/s1").click();
        System.out.println("Server is chosen");

        // Tap authorization button
        driver.findElementById("com.ajaxsystems:id/next").click();
        System.out.println("Authorization button is tapped");

        Boolean expected;
        expected = driver.findElementById("com.ajaxsystems:id/menuDrawer").isDisplayed();
        Assert.assertTrue(expected);
*/
    @AfterTest
    public void endSuit() {
        driver.quit();
    }
}
