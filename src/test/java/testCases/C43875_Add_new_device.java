package testCases;

import io.appium.java_client.AppiumDriver;
import pages.Base;
import utils.Imitator;

/**
 * Created by installer on 3/28/17.
 */
public class C43875_Add_new_device extends Base {

    private String login, pass, server, name, actual;
    public String locale;
    Imitator imitator = new Imitator();

    public C43875_Add_new_device(AppiumDriver driver, String locale) {
        super(driver, locale);
        log("TEST IS STARTED");
        login = "ajax1@i.ua";
        pass = "qwe123";
        server = "Develop";

        log("start from IntroPage");
        introPage.goToAuthorization();
        loginPage.loginToTheServer(login, pass, server);

        log("waiting for Pincode PopUp and cancel it");
        check.waitElementWithoutPin(dashboardHeader.menuDrawer, 15);

        log("add devices to imitator");
        int devID = 203060;
        imitator.clearMemory();
        imitator.addDevice(devID + 1, 1, 1);
        imitator.addDevice(devID + 2, 2, 2);
        imitator.addDevice(devID + 3, 3, 4);
        imitator.getDeviceList();

        for (int i = 1; i <= 3; i++) {
            log("add devices #" + i);
            nav.scrollBottom();
            device.addDeviceButtonClick();
            device.fillFieldsWith("device" + i, "20306" + i);
            hideKeyboard();
            device.setRoom(i);
            hideKeyboard();
            nav.confirmIt();
            imitator.registerDevice(devID + i);
        }

        log("TEST IS FINISHED");
    }

}
