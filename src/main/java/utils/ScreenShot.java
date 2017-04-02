package utils;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import pages.Base;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenShot extends Base{

    private AppiumDriver driver;
    private String path_screenshot = "screenshot";
    private Date currentDate = new Date();
    private String pathOfScreenshot;

    public ScreenShot(AppiumDriver driver) {
        this.driver = driver;
    }

    public void getScreenShot(){
        try {
            log("Method is started");
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

            pathOfScreenshot = targetFile.getAbsolutePath();
            log("Path to screenshot: " + pathOfScreenshot);
            log("Method is finished");
        }
        catch (IOException e1) {
            log(4, "IOException:\n\n" + e1 + "\n");
        }
    }

}
