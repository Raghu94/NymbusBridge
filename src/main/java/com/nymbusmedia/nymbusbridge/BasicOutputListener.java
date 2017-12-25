package com.nymbusmedia.nymbusbridge;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;

/**
 * Created by Raghu Mulukutla on 12/9/17.
 */
public class BasicOutputListener implements SerialPortDataListener, SerialPortPacketListener {
    @Override
    public int getPacketSize() {
        System.out.println("BasicOutputListener:Get packet size");
        return 1;
    }

    @Override
    public int getListeningEvents() {
        System.out.println("BasicOutputListener:getListeningEvents");
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        System.out.println("BasicOutputListener: Received: " + new String(serialPortEvent.getReceivedData()));
    }
}
