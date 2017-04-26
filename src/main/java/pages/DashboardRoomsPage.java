package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

    @AndroidFindBy(id = "com.ajaxsystems:id/delete")
    private AndroidElement deleteButton;

//----------------------------------------------------------------------------------------------------------------------
    private final Base base;
    private final AndroidDriver driver;
    private boolean result;
    private WebElement[] elements;

    public DashboardRoomsPage(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.TIMEOUT, TimeUnit.SECONDS), this);
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
        Base.log(4, "Method is started");

        clickAddRoomButton();

        Base.log(1, "fill Room name field with: \"" + roomName + "\"");
        roomNameField.sendKeys(roomName);

        switch (type){
            case 1: Base.log(1, "add image from camera");
                addRoomImageBtn.click();
                base.addImagePage.setImageFromCamera();
                break;
            case 2: Base.log(1, "add image from gallery");
                addRoomImageBtn.click();
                base.addImagePage.setImageFromGallery(imageNumber);
                break;
            default: Base.log(1, "add room without image");
                driver.hideKeyboard();
                break;
        }

        Base.log(1, "tap Save button");
        saveButton.click();

        Base.log(4, "Method is finished");
    }

    public void addRoom(String roomName, int roomType) {
        Base.log(4, "Method is started");

        clickAddRoomButton();

        Base.log(1, "fill Room name field with: \"" + roomName + "\"");
        roomNameField.sendKeys(roomName);

        switch (roomType){
            case 1: Base.log(1, "add image from camera");
                addRoomImageBtn.click();
                base.addImagePage.setImageFromCamera();
                break;
            case 2: Base.log(1, "add image from gallery");
                addRoomImageBtn.click();
                base.addImagePage.setImageFromGallery(1);
                break;
            default: Base.log(1, "add room without image");
                driver.hideKeyboard();
                break;
        }
        Base.log(1, "tap Save button");
        saveButton.click();

        Base.log(4, "Method is finished");
    }

    private void clickAddRoomButton (){
        Base.log(4, "Method is started");
        elements = new WebElement[]{addRoomBtn, addRoomPlusBtn};

        Base.log(1, "choice the Add Room button");
        base.nav.scrollBottom();
        switch (base.check.waitElements(elements, 2)){
            case 1: addRoomBtn.click(); break;
            case 2: addRoomPlusBtn.click(); break;
            default: Base.log(3, "Something was wrong!"); break;
        }
        Base.log(4, "Method is finished");
    }

    public boolean isRoomPresens(String roomName) {
        base.nav.scrollTop();
        if (base.nav.scrollToElementWith.name(roomName, false)){
            Base.log(1, "room with name \"" + roomName + "\" is displayed in rooms list ");
            return true;
        }else {
            return false;
        }
    }

    public String getFirstRoomName(){
        return roomNameField.getText();
    }

    public void goToRoomSettingsPage(){
        Base.log(1, "tap Settings Button");
        base.nav.goToSettings();
    }

    public void deleteButtonClick(){
        Base.log(1, "tap delete button");
        deleteButton.click();
    }

    public boolean deleteAllRooms() {
        String successText = base.getLocalizeTextForKey("Deleting_success1");
        int counter = 0;
        try {
            while (true) {
                if (base.wait.element(base.dashboardHeader.getMenuDrawer(), 5, true)){
                    String roomName = getFirstRoomName();
                    goToRoomSettingsPage();
                    base.nav.scrollBottom();
                    deleteButtonClick();
                    base.nav.confirmIt();
                    if (base.wait.elementWithText(successText, 10, true)){
                        base.wait.element(base.dashboardHeader.getMenuDrawer(), 5, true);
                        base.check.isDeletedBy("name", roomName);
                        Base.log(1, "room with name \"" + roomName + "\" is deleted successfully and SUCCESS text is shown");
                        counter++;
                    }else {
                        Base.log(3, "SUCCESS text is not shown");
                        return false;
                    }

                }else if (base.nav.getCancelButton().isDisplayed()){
                    base.nav.cancelIt();
                }else {
                    break;
                }
            }
        }catch (NoSuchElementException e){
            Base.log(1, "NoSuchElementException: \n\n" + e + "\n");
        }
        if (counter > 0) {
            Base.log(1, "element are not found, number of deleted elements: " + counter);
            result = true;
        }else {
            Base.log(2, "Test impossible, because precondition isn't valid - no one element found");
            result = false;
        }
        return result;
    }

}
