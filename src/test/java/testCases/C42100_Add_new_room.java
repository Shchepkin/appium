package testCases;

import io.appium.java_client.AppiumDriver;
import org.testng.annotations.Test;
import pages.Base;

/**
 * Created by installer on 3/28/17.
 */
public class C42100_Add_new_room extends Base {

    private String login, pass, server, name, actual;

    public C42100_Add_new_room(AppiumDriver driver, String locale_) {
        super(driver, locale_);
        
        log("TEST IS STARTED");
        pass = "qwe123";
        name = "room_number_";
        login = "ajax1@i.ua";
        server = "Production";

        log("start from IntroPage");
        introPage.goToAuthorization();
        authorizationPage.loginToTheServer(login, pass, server);

        log("waiting for Pincode PopUp");
        check.waitElementWithoutPin(dashboardHeader.menuDrawer, 3);

        log("tap the Room Page button in the footer");
        dashboard.footerRooms.click();

        log("add Room without image");
        roomsPage.addRoom("Without image", 0);

        log("add Room with image from camera");
        roomsPage.addRoom("Camera image", 1);

        log("add Room with image from popup gallery");
        roomsPage.addRoom("Gallery image", 2, 2);

        log("TEST IS FINISHED");
    }

}
