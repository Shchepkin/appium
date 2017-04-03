package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Check;
import utils.Navigation;

import java.util.List;

public class DashboardRoomsPage extends Base{
    private AppiumDriver driver;
//    private Navigation nav;
//    private Check check;
    private WebElement[] elements;
    private boolean result;

    @AndroidFindBy(id = "com.ajaxsystems:id/add")
    private WebElement addRoomBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/add")
    private WebElement addRoomImageBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/name")
    private WebElement roomNameField;

    @FindAll({@FindBy(id = "com.ajaxsystems:id/name")})
    private List<MobileElement> roomNameList;

    @AndroidFindBy(id = "com.ajaxsystems:id/save")
    private WebElement saveButton;

    @AndroidFindBy(id = "com.ajaxsystems:id/footerImage")
    private WebElement addRoomPlusBtn;


    public DashboardRoomsPage(AppiumDriver driver) {

        this.driver = driver;
        nav = new Navigation(driver);
        check = new Check(driver);
//        introPage = new IntroPage(driver);
//        dashboard = new Dashboard(driver);
        addImagePage = new AddImagePage(driver);
//        loginPage = new AuthorizationPage(driver);

        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    /******************************************************************************************************************
     *
     * @param roomName        - Room name (24 byte)
     * @param type        - for default type set as 0 (room without image)
     *                          0. room without image
     *                          1. add image from camera
     *                          2. add image from gallery
     * @param imageNumber - if use room with type 2 y can set 1, 2 or 3 image from PopUp, other values set number to 1 for default
     */
    public void addRoom(String roomName, int type, int imageNumber) {
        log("Method is started");

        clickAddRoomButton();

        log("fill Room name field with: \"" + roomName + "\"");
        roomNameField.sendKeys(roomName);

        switch (type){
            case 1: log("add image from camera");
                addRoomImageBtn.click();
                addImagePage.setImageFromCamera();
                break;
            case 2: log("add image from gallery");
                addRoomImageBtn.click();
                addImagePage.setImageFromGallery(imageNumber);
                break;
            default: log("add room without image");
                driver.hideKeyboard();
                break;
        }

        log("tap Save button");
        saveButton.click();

        log("Method is finished");
    }

    public void addRoom(String roomName, int roomType) {
        log("Method is started");

        clickAddRoomButton();

        log("fill Room name field with: \"" + roomName + "\"");
        roomNameField.sendKeys(roomName);

        switch (roomType){
            case 1: log("add image from camera");
                addRoomImageBtn.click();
                addImagePage.setImageFromCamera();
                break;
            case 2: log("add image from gallery");
                addRoomImageBtn.click();
                addImagePage.setImageFromGallery(1);
                break;
            default: log("add room without image");
                driver.hideKeyboard();
                break;
        }
        log("tap Save button");
        saveButton.click();

        log("Method is finished");
    }

    private void clickAddRoomButton (){
        log("Method is started");

        elements = new WebElement[]{addRoomBtn, addRoomPlusBtn};
        log("choice the Add Room button");
        switch (check.waitElements(elements, 3)){
            case 1: System.out.println(addRoomBtn.getText());addRoomBtn.click(); break;
            case 2: addRoomPlusBtn.click(); break;
            // make here scroll to element with text if there are a lot of rooms
            default: log(3, "Something was wrong!"); break;
        }
        log("Method is finished");
    }

    public boolean isRoomPresens(String roomName) {
        log("Method is started");
        result = false;
        for (WebElement roomNameElement : roomNameList) {
            System.out.println(roomNameElement.getText());
            if (roomNameElement.getText().equals(roomName)) {
                result = true;
                log("room with name \"" + roomName + "\" successfully added!");
                break;
            }
        }
        log("Method is finished");
        return result;
    }
}
