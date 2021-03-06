package utils;

import jssc.*;
import org.openqa.selenium.By;
import pageObjects.Base;

public class Imitator{

//----------------------------------------------------------------------------------------------------------------------
    private Base base;
    private static SerialPort serialPort;

    public String getDeviceNameNew() {
        return deviceNameNew;
    }

    private String deviceNameNew = "";

    public Imitator(Base base) {
        this.base = base;
        SerialPortSetup();
        Base.log(4, "clear imitator memory");
        clearMemory();
    }

//----------------------------------------------------------------------------------------------------------------------

    private void SerialPortSetup() {
        try {
            serialPort = new SerialPort("/dev/ttyUSB3");

            Base.log(4, "print all available ports");
            for (String portName: SerialPortList.getPortNames()) {
                Base.log(4, portName);
            }

            Base.log(4, "open port ");

            if (!serialPort.isOpened())serialPort.openPort();

            Base.log(4, "set port params: BAUDRATE, DATABITS, STOPBITS,PARITY)");
            serialPort.setParams(SerialPort.BAUDRATE_57600,
                                 SerialPort.DATABITS_8,
                                 SerialPort.STOPBITS_1,
                                 SerialPort.PARITY_NONE);

            Base.log(4, "switch on hardware flow control");
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);

            Base.log(4, "serialPort Mask to reaction on data in buffer");
            serialPort.setEventsMask(SerialPort.MASK_RXCHAR);
            serialPort.addEventListener(new PortReader());

        } catch (Exception ex) {
            Base.log(3, "Exception: \n" + ex + "\n");
        }
    }

    public void sendCommand(String command){
        Base.log(1, "send command");
        try {
            serialPort.writeString(command +"\n");
        }catch (Exception ex) {
            Base.log(3, "Exception: \n" + ex + "\n");
        }
    }

    private void addDeviceToImitator (int devId, int devNumber, int devType){
        String command = String.format("add %d %d %d\n", devId, devNumber, devType);
        try {
            serialPort.writeString(command);
            Thread.sleep(2000);
        }catch (Exception e) {
            Base.log(3, "Exception: \n" + e + "\n");
        }
    }

    private boolean registerDeviceProcess (String devName, int devId, int roomNumber){
        base.devicesPage.fillFieldsWith(devName, String.valueOf(devId));
        base.hideKeyboard();

        if (roomNumber > 0) {
            base.devicesPage.setRoom(roomNumber);
        }
        Base.log(1, "tap Add devices button", true);
        base.nav.confirmIt();

        if (base.check.isPresent.snackBar(5)){return  false;}

        Base.log(1, "device turn on", true);
        registerDevice(devId);
        registerDevice(devId);

        base.wait.invisibilityOfElement(By.id(base.device.getTimerTextId()), 30);

        Base.log(1, "check is PIN popUp displayed");
        base.wait.pinPopUp(2, false);
        return true;
    }

    public boolean addDevice(int devId, int devNumber, int devType, String devName, int roomNumber){
        Base.log(1, "add devices to imitator");
        addDeviceToImitator (devId, devNumber, devType);

        Base.log(1, "add device \"" + devName + "\" to Hub");
        base.nav.scroll.toElementWith.id(base.devicesPage.getAddDeviceButtonId(), true);

        Base.log(1, "wait for popUp for adding new device", true);
        if (!base.wait.element(base.popUp.getAddNewDevicePopUp(), 5, true)) {return false;}

        return registerDeviceProcess(devName, devId, roomNumber);
    }

    public boolean addDevice(int devId, int devNumber, int devType, String devName){
        Base.log(1, "add device \"" + devName + "\" to Hub");

        Base.log(1, "tap first room in the list");
        base.roomsPage.getRoomNameField().click();

        Base.log(1, "tap Add New Device button");
        try {
            base.nav.tapButton.add();
            Base.log(1, "add device \"" + devName + "\" from Empty rooms Page", true);
            deviceNameNew = devName;

        }catch (Exception e){
            base.nav.tapButton.add();
            devId++;
            devNumber++;
            deviceNameNew = devName + "_1";
            Base.log(1, "add device \"" + deviceNameNew + "\" from not Empty rooms Page", true);
        }

        Base.log(1, "wait for popUp for adding new device", true);
        if (!base.wait.element(base.popUp.getAddNewDevicePopUp(), 5, true)) {return false;}

        Base.log(1, "add devices to imitator");
        addDeviceToImitator (devId, devNumber, devType);

        return registerDeviceProcess(deviceNameNew, devId, 0);
    }

    public void getDeviceList(){
        try {
            serialPort.writeString("lst\n");
            Thread.sleep(2000);
        }catch (Exception e) {
            Base.log(3, "Exception: \n" + e + "\n");
        }
    }

    public void clearMemory(){
        try {
            serialPort.writeString("cln\n");
            Thread.sleep(2000);
        }catch (Exception e) {
            Base.log(3, "Exception: \n" + e + "\n");
        }
    }

    public void registerDevice(int dev_id){
        try {
            serialPort.writeString("reg " + dev_id + "\n");
            Thread.sleep(3000);
        }catch (Exception e) {
            Base.log(3, "Exception: \n" + e + "\n");
        }
    }

    private class PortReader implements SerialPortEventListener {

        public void serialEvent(SerialPortEvent event) {
            if (event.isRXCHAR() && event.getEventValue() > 0) {
                try {
                    String data = serialPort.readString(event.getEventValue());
                    Base.log(4, data);

                } catch (Exception ex) {
                    Base.log(3, "Exception \n" + ex + "\n");
                }
            }
        }
    }

}