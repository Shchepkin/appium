package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.List;

public class DashboardRoomsPage{

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

//----------------------------------------------------------------------------------------------------------------------
    private final Base base;
    private final AppiumDriver driver;
    private boolean result;
    private WebElement[] elements;

    public DashboardRoomsPage(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
//----------------------------------------------------------------------------------------------------------------------

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
        Base.log("Method is started");

        clickAddRoomButton();

        Base.log("fill Room name field with: \"" + roomName + "\"");
        roomNameField.sendKeys(roomName);

        switch (type){
            case 1: Base.log("add image from camera");
                addRoomImageBtn.click();
                base.addImagePage.setImageFromCamera();
                break;
            case 2: Base.log("add image from gallery");
                addRoomImageBtn.click();
                base.addImagePage.setImageFromGallery(imageNumber);
                break;
            default: Base.log("add room without image");
                driver.hideKeyboard();
                break;
        }

        Base.log("tap Save button");
        saveButton.click();

        Base.log("Method is finished");
    }

    public void addRoom(String roomName, int roomType) {
        Base.log("Method is started");

        clickAddRoomButton();

        Base.log("fill Room name field with: \"" + roomName + "\"");
        roomNameField.sendKeys(roomName);

        switch (roomType){
            case 1: Base.log("add image from camera");
                addRoomImageBtn.click();
                base.addImagePage.setImageFromCamera();
                break;
            case 2: Base.log("add image from gallery");
                addRoomImageBtn.click();
                base.addImagePage.setImageFromGallery(1);
                break;
            default: Base.log("add room without image");
                driver.hideKeyboard();
                break;
        }
        Base.log("tap Save button");
        saveButton.click();

        Base.log("Method is finished");
    }

    private void clickAddRoomButton (){
        Base.log("Method is started");
        elements = new WebElement[]{addRoomBtn, addRoomPlusBtn};

        Base.log("choice the Add Room button");
        base.nav.scrollBottom();
        switch (base.check.waitElements(elements, 2)){
            case 1: addRoomBtn.click(); break;
            case 2: addRoomPlusBtn.click(); break;
            default: Base.log(3, "Something was wrong!"); break;
        }
        Base.log("Method is finished");
    }

    public boolean isRoomPresens(String roomName) {
        Base.log("Method is started");
        result = false;
        for (WebElement roomNameElement : roomNameList) {
            if (roomNameElement.getText().equals(roomName)) {
                result = true;
                Base.log("room with name \"" + roomName + "\" successfully added!");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        Base.log("Method is finished");
        return result;
    }
}
