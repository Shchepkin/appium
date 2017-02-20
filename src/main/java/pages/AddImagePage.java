package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


public class AddImagePage {
    public final AppiumDriver driver;

    @AndroidFindBy(id = "com.ajaxsystems:id/tv_title")
    public WebElement addImagePageTitle;

    @AndroidFindBy(id = "com.ajaxsystems:id/iv_thumbnail")
    public List<WebElement> thumbnail;

    @AndroidFindBy(id = "com.ajaxsystems:id/rotateCounterClockwise")
    public WebElement rotateCounterClockwise;

    @AndroidFindBy(id = "com.ajaxsystems:id/rotateClockwise")
    public WebElement rotateClockwise;

    @AndroidFindBy(id = "com.ajaxsystems:id/cropImage")
    public WebElement cropImage;

    @AndroidFindBy(id = "com.ajaxsystems:id/back")
    public WebElement backBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/next")
    public WebElement saveBtn;

    public AddImagePage(AppiumDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
}
