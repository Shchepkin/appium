package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AddImagePage{

    @AndroidFindBy(id = "com.ajaxsystems:id/tv_title")
    private WebElement addImagePageTitle;

    @AndroidFindBy(id = "com.ajaxsystems:id/iv_thumbnail")
    private List<WebElement> thumbnail;

    @AndroidFindBy(id = "com.ajaxsystems:id/rotateCounterClockwise")
    private WebElement rotateCounterClockwise;

    @AndroidFindBy(id = "com.ajaxsystems:id/rotateClockwise")
    private WebElement rotateClockwise;

    @AndroidFindBy(id = "com.ajaxsystems:id/cropImage")
    private WebElement cropImage;

    @AndroidFindBy(id = "com.ajaxsystems:id/back")
    private WebElement backBtn;

    @AndroidFindBy(id = "com.ajaxsystems:id/next")
    private WebElement nextBtn;


// ===================== Camera v.4-5 =================================
    @AndroidFindBy(id = "com.android.gallery3d:id/shutter_button_photo")
    private WebElement shutterButtonPhoto;

    @AndroidFindBy(id = "com.android.gallery3d:id/btn_done")
    private WebElement doneButton;

    @AndroidFindBy(id = "com.android.gallery3d:id/btn_cancel")
    private WebElement cancelButton;

    @AndroidFindBy(id = "com.android.gallery3d:id/btn_retake")
    private WebElement retakeButton;

// ===================== Camera v.6  =================================
    @AndroidFindBy(id = "com.android.camera:id/v6_shutter_button_internal")
    private WebElement shutterButtonInternal6;

    @AndroidFindBy(id = "com.android.camera:id/v6_btn_done")
    private WebElement doneButton6;

    @AndroidFindBy(id = "com.android.camera:id/v6_btn_cancel")
    private WebElement cancelButton6;

// ==================================================================

    private Base base;
    private AndroidDriver driver;

    public AddImagePage(Base base) {
        this.base = base;
        this.driver = base.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Base.TIMEOUT, TimeUnit.SECONDS), this);
    }

    public void setImageFromCamera(){
        Base.log(1, "method is started");
        thumbnail.get(0).click();
        shutterButtonPhoto.click();
        doneButton.click();
        nextBtn.click();
        Base.log(1, "method is finished");
    }


    public void setImageFromGallery(int imageNumber){
        if (imageNumber > 3 || imageNumber < 1) {imageNumber = 1;}
        thumbnail.get(imageNumber + 2).click();
        nextBtn.click();
    }

}
