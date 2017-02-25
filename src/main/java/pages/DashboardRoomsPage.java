package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindAll;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AndroidFindBys;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.Check;
import utils.Navigation;
import utils.ScreenShot;
import utils.Setup;

import java.util.List;

/**
 * Created by installer on 1/21/17.
 */
public class DashboardRoomsPage {
    public final AndroidDriver driver;
    private IntroPage introPage;
    private AuthorizationPage authorizationPage;
    private Navigation nav;
    private AddImagePage addImagePage;
    private Setup s = new Setup();
    private Dashboard dashboard;
    private Check check;
    private WebElement[] elements;
    private boolean result;

    @AndroidFindBy(id = "com.ajaxsystems:id/add")
    private WebElement addRoomBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/add")
    private WebElement addRoomImageBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/name")
    private WebElement roomName;

    @FindAll({@FindBy(id = "com.ajaxsystems:id/name")})
    private List<MobileElement> roomNameList;

    @AndroidFindBy(id = "com.ajaxsystems:id/save")
    private WebElement saveBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/footerImage")
    private WebElement addRoomPlusBtn;




    public DashboardRoomsPage(AppiumDriver driver) {
        this.driver = (AndroidDriver)driver;
        nav = new Navigation(driver);
        check = new Check(driver);
        introPage = new IntroPage(driver);
        dashboard = new Dashboard(driver);
        addImagePage = new AddImagePage(driver);
        authorizationPage = new AuthorizationPage(driver);

        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }


    public void addRoom(String name, int numOfImage) {
        s.log("Method is started");

        elements = new WebElement[]{addRoomBtn, addRoomPlusBtn};
        s.log("check for existing Add Room button");
        switch (check.waitElements(elements, 3)){
            case 1: addRoomBtn.click(); break;
            case 2: addRoomPlusBtn.click(); break;
            // make here scroll to element with text if there are a lot of rooms
            default: s.log(3, "Something was wrong!"); break;
        }

        s.log("fill Room name field with: \"" + name + "\"");
        roomName.sendKeys(name);

        if (numOfImage > 0){
            s.log("add room image");
            addRoomImageBtn.click();
            addImagePage.thumbnail.get(numOfImage + 2).click();
            addImagePage.nextBtn.click();
        }else {
            s.log("add room without image");
            driver.hideKeyboard();
        }

        s.log("save room");
        result = false;
        
        if(check.clickElementAndWaitingPopup(saveBtn, 3, 2, false)){
            check.waitElement(roomName,3,true);
            s.log("check list of rooms for existing new room");
            for (WebElement roomNameElement: roomNameList) {
                System.out.println(roomNameElement.getText());
                if(roomNameElement.getText().equals(name)) {result = true;}
            }
            Assert.assertTrue(result);
            s.log("room with name \"" + name + "\" successfully added!");
        }
        s.log("Method is finished");
    }
}
