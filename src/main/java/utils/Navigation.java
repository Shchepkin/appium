package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.Map;


public class Navigation {
    public final AppiumDriver driver;
    Setup s = new Setup();

    @AndroidFindBy(id = "com.ajaxsystems:id/back")
    public WebElement backBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/next")
    public WebElement nextBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/cancel")
    public WebElement cancelBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/ok")
    public WebElement okBtnd;

//================================================================================================
    public void swipeUp() {
        s.log("Method is started");

        Dimension screenSize = driver.manage().window().getSize();
        s.log("Screen dimension is " + screenSize);

        int startX = (int)(screenSize.width / 2.00);
        int startY = (int)(screenSize.height / 3.00);
        int endX = startX;
        int endY = (int)(screenSize.height / 20.00);
        int duration = 1500;

        s.log("swipe(startX, startY, endX, endY, duration) [" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + duration + "]");
        driver.swipe(startX, startY, endX, endY, duration);

        s.log("Method is finished");
    }

    public void swipeDown() {
        s.log("Method is started");

        Dimension screenSize = driver.manage().window().getSize();
        s.log("Screen dimension is " + screenSize);

        int startX = (int)(screenSize.width / 2.00);
        int startY = (int)(screenSize.height / 3.00);
        int endX = startX;
        int endY = (int)(screenSize.height / 1.3);
        int duration = 1500;

        s.log("swipe(startX, startY, endX, endY, duration) [" + startX + ", " + startY + ", " + endX + ", " + endY + ", " + duration + "]");
        driver.swipe(startX, startY, endX, endY, duration);

        s.log("Method is finished");
    }

    public void scrollToElement() {
        s.log("Method is started");

        Dimension screenSize = driver.manage().window().getSize();
        s.log("Screen dimension is " + screenSize);


//        driver.switchTo(element);
//        driver.executeScript("mobile: scroll", new HashMap<String, Double>() {
//            {
//                put("direction", "down");
//            }
//        });

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("direction", "down");
        driver.executeScript("mobile: scroll", params);

        s.log("Method is finished");
    }

    public void longTapButton(WebElement element, int timer) {
        s.log("Method is started");
        s.log(2, "long tap for " + timer + " seconds");
        timer = timer * 1000;
        driver.tap(1, element, timer);
        s.log("Method is finished");
    }

    public Navigation(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}
