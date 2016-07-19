package main.java.ladar;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.server.ExportException;
import java.util.Enumeration;
import gnu.io.*;

public class ComReader implements SerialPortEventListener{

    static CommPortIdentifier portId;
    static Enumeration portList;

    InputStream inputStream;
    SerialPort serialPort;


    public void start() {
        try {
            portList = CommPortIdentifier.getPortIdentifiers();

            while (portList.hasMoreElements()) {
                portId = (CommPortIdentifier) portList.nextElement();
                if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL && portId.getName().equals("COM1")) {
                    serialPort = (SerialPort) portId.open("Ladar", 2000);
                    serialPort.addEventListener(this);
                    serialPort.notifyOnDataAvailable(true);
                    inputStream = serialPort.getInputStream();
                    serialPort.setSerialPortParams(9600,
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);
                }
            }
        } catch (Exception e) {System.out.println(e);}
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {

        if (serialPortEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE){
            byte[] readBuffer = new byte[20];

            try {
                while (inputStream.available() > 0) {
                    int numBytes = inputStream.read(readBuffer);
                }
                System.out.print(new String(readBuffer));
            } catch (IOException e) {System.out.println(e);}
        }

    }
}
