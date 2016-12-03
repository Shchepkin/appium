package ajaxMobileApp;

import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenShot {

    private AndroidDriver driver;
    private String path_screenshot = "screenshot";
    private Date currentDate = new Date();

    public ScreenShot(AndroidDriver driver) {
        this.driver = driver;
    }

    public void getScreenShot() throws IOException{
        // Create formatted date and time for folders and filenames
        SimpleDateFormat date = new SimpleDateFormat("yyyy.MM.dd");
        SimpleDateFormat time = new SimpleDateFormat("HHmmss");

        // Make screenshot
        File srcFile = driver.getScreenshotAs(OutputType.FILE);

        // Creating folder and filename for screenshot
        String folder = path_screenshot + "/" + date.format(currentDate);
        String filename = date.format(currentDate) + "_" + time.format(currentDate) + ".png";
        File targetFile = new File(folder + "/" + filename);

        // Move screenshot to the target folder
        FileUtils.copyFile(srcFile, targetFile);

        System.out.println("\nScreenshot filename [" + filename + "]" +
                            "\nScreenshot folder is [" + folder + "]\n\n");
    }
}
