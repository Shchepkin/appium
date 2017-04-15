package utils;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortList;
import pages.Base;

public class Imitator{

//----------------------------------------------------------------------------------------------------------------------
    private boolean result;
    private static SerialPort serialPort;

    public Imitator() {
        SerialPortSetup();
        Base.log(1, "clear imitator memory");
        clearMemory();
    }

//----------------------------------------------------------------------------------------------------------------------

    private void SerialPortSetup() {
        Base.log(1, "method is started");

        try {
            serialPort = new SerialPort("/dev/ttyS0");

            Base.log(1, "print all available ports");
            for (String portName: SerialPortList.getPortNames()) {
                System.out.println(portName);
            }

            Base.log(1, "open port ");
            serialPort.openPort();

            Base.log(1, "set port params: BAUDRATE, DATABITS, STOPBITS,PARITY)");
            serialPort.setParams(SerialPort.BAUDRATE_57600,
                                 SerialPort.DATABITS_8,
                                 SerialPort.STOPBITS_1,
                                 SerialPort.PARITY_NONE);

            Base.log(1, "switch on hardware flow control");
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);

            Base.log(1, "serialPort Mask to reaction on data in buffer");
            serialPort.setEventsMask(SerialPort.MASK_RXCHAR);
            serialPort.addEventListener(new PortReader());

        } catch (Exception ex) {
            Base.log(3, "Exception: \n" + ex + "\n");
        }
        Base.log(1, "method is finished");
    }

    public void sendCommand(String command){
        Base.log(1, "send command");
        try {
            serialPort.writeString(command +"\n");
        }catch (Exception ex) {
            Base.log(3, "Exception: \n" + ex + "\n");
        }
    }

    public void addDevice(int dev_id, int dev_numb, int dev_type){
        String command = String.format("add %d %d %d\n", dev_id, dev_numb, dev_type);
        try {
            Thread.sleep(2000);
            serialPort.writeString(command);
        }catch (Exception e) {
            Base.log(3, "Exception: \n" + e + "\n");
        }
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
                    System.out.println(data);

                } catch (Exception ex) {
                    Base.log(3, "Exception \n" + ex + "\n");
                }
            }
        }
    }

}