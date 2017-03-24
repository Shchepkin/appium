package utils;

import jssc.*;

public class Imitator {

    private Setup s = new Setup();
    private static SerialPort serialPort;
    private String port = "USB3";

    public Imitator() {
        SerialPortSetup();
    }

    private void SerialPortSetup() {
        s.log("method is started");

        try {
            serialPort = new SerialPort("/dev/ttyUSB3");

            s.log("print all available ports");
            for (String portName: SerialPortList.getPortNames()) {
                System.out.println(portName);
            }
            s.log("open port ");
            serialPort.openPort();

            s.log("set port params: BAUDRATE, DATABITS, STOPBITS,PARITY)");
            serialPort.setParams(57600, 8, 1, 0);

            s.log("switch on hardware flow control");
//            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
//                    SerialPort.FLOWCONTROL_RTSCTS_OUT);

            s.log("serialPort Mask to reaction on data in buffer");
            serialPort.setEventsMask(SerialPort.MASK_RXCHAR);
            serialPort.addEventListener(new PortReader());

        } catch (Exception ex) {
            s.log(3, "Exception \n" + ex + "\n");
        }
        s.log("method is finished");
    }

    public void sendCommand(String command){
        s.log("send command");
        try {
            serialPort.writeString(command +"\n");
        }catch (Exception e) {
            s.log(3, "Exception \n" + e + "\n");
        }
    }

    public void addDevice(int dev_id, int dev_numb, int dev_type){
        String command = String.format("add %d %d %d\n", dev_id, dev_numb, dev_type);
        try {
            serialPort.writeString(command);
            Thread.sleep(2000);
        }catch (Exception e) {
            s.log(3, "Exception \n" + e + "\n");
        }
    }

    public void getDeviceList(){
        try {
            serialPort.writeString("lst\n");
            Thread.sleep(2000);
        }catch (Exception e) {
            s.log(3, "Exception \n" + e + "\n");
        }
    }

    public void clearMemory(){
        try {
            serialPort.writeString("cln\n");
            Thread.sleep(2000);
        }catch (Exception e) {
            s.log(3, "Exception \n" + e + "\n");
        }
    }

    public void registerDevice(int dev_id){
        try {
            serialPort.writeString("reg " + dev_id + "\n");
            Thread.sleep(2000);
        }catch (Exception e) {
            s.log(3, "Exception \n" + e + "\n");
        }
    }

    private class PortReader implements SerialPortEventListener {

        public void serialEvent(SerialPortEvent event) {
            if (event.isRXCHAR() && event.getEventValue() > 0) {
                try {
                    String data = serialPort.readString(event.getEventValue());
//                    byte[] buffer = serialPort.readBytes(16);
//                    String str = new String(buffer);
                    System.out.println(data);

                } catch (Exception ex) {
                    s.log(3, "Exception \n" + ex + "\n");
                }
            }
        }
    }

}