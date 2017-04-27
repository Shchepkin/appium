package utils;

import jssc.*;
import pages.Base;

public class Imitator{

//----------------------------------------------------------------------------------------------------------------------
    private Base base;
    private static SerialPort serialPort;

    public Imitator(Base base) {
        this.base = base;
        SerialPortSetup();
        Base.log(4, "clear imitator memory");
        clearMemory();
    }

//----------------------------------------------------------------------------------------------------------------------

    private void SerialPortSetup() {
        Base.log(4, "Method is started");

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
        Base.log(4, "Method is finished");
    }

    public void sendCommand(String command){
        Base.log(1, "send command");
        try {
            serialPort.writeString(command +"\n");
        }catch (Exception ex) {
            Base.log(3, "Exception: \n" + ex + "\n");
        }
    }

    public void addDevice(int devId, int devNumber, int devType, String devName, int roomNumber){
        Base.log(1, "add devices to imitator");
        String command = String.format("add %d %d %d\n", devId, devNumber, devType);
        try {
            Thread.sleep(2000);
            serialPort.writeString(command);
        }catch (Exception e) {
            Base.log(3, "Exception: \n" + e + "\n");
        }

        Base.log(1, "add devices \"" + devName + "\" to Hub", true);
        Base.log(1, "scroll bottom");
        base.nav.scrollBottom();
        base.devicesPage.addDeviceButtonClick();
        base.devicesPage.fillFieldsWith(devName, String.valueOf(devId));
        base.hideKeyboard();
        base.devicesPage.setRoom(roomNumber);

        Base.log(1, "tap Add devices button");
        base.nav.confirmIt();

        Base.log(1, "device turn on");
        registerDevice(devId);

        Base.log(1, "check is PIN popUp displayed");
        base.wait.pinPopUp(2, false);
    }

    public void getDeviceList(){
        try {
            Thread.sleep(2000);
            serialPort.writeString("lst\n");
        }catch (Exception e) {
            Base.log(3, "Exception: \n" + e + "\n");
        }
    }

    public void clearMemory(){
        try {
            Thread.sleep(2000);
            serialPort.writeString("cln\n");
        }catch (Exception e) {
            Base.log(3, "Exception: \n" + e + "\n");
        }
    }

    public void registerDevice(int dev_id){
        try {
            Thread.sleep(3000);
            serialPort.writeString("reg " + dev_id + "\n");
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