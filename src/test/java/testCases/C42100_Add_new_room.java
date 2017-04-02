package testCases;

import io.appium.java_client.AppiumDriver;
import pages.Base;

/**
 * Created by installer on 3/28/17.
 */
public class C42100_Add_new_room extends Base {

    private String login, pass, server, name, actual;

    public C42100_Add_new_room(AppiumDriver driver, String locale_) {
        super(driver, locale_);
        
        log("TEST IS STARTED");
        login = "ajax1@i.ua";
        pass = "qwe123";
        server = "Develop";

        log("start from IntroPage");
        introPage.goToAuthorization();
        loginPage.loginToTheServer(login, pass, server);

        log("waiting for Pincode PopUp and cancel it");
        check.waitElementWithoutPin(dashboardHeader.menuDrawer, 15);

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
