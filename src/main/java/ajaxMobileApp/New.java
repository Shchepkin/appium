package ajaxMobileApp;

public class New {
      /*

-------------------------------------------------------------------------------------------------------

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="Default Suite">
  <test name="appium">
    <classes>
      <class name="ajaxMobileApp.TestLoginScreen"/>
    </classes>
  </test> <!-- appium -->
</suite> <!-- Default Suite -->


-------------------------------------------------------------------------------------------------------
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "Android");
        capabilities.setCapability("deviceName", "Prestigio");
        capabilities.setCapability("platformVersion", "4.4.2");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("appPackage", "com.ajaxsystems");
        capabilities.setCapability("appActivity", "com.ajaxsystems.activity.DashboardActivity");
------------------------------------------------------------------------------------------------------
        // Create AndroidDriver object and connect to ajaxMobileApp server
        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);

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
}
