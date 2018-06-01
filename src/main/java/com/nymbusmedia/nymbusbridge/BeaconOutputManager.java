package com.nymbusmedia.nymbusbridge;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;

/**
 * Created by Raghu Mulukutla on 12/9/17.
 */
public class BeaconOutputManager implements SerialPortDataListener, SerialPortPacketListener {
    private OutputMessageListener listener;
    private String command;
    public BeaconOutputManager(OutputMessageListener outputMessageListener, String command) {
        this.listener = outputMessageListener;
        this.command = command;
    }
    @Override
    public int getPacketSize() {
//        System.out.println("BeaconOutputManager:Get packet size");
        return 1;
    }

    @Override
    public int getListeningEvents() {
//        System.out.println("BeaconOutputManager:getListeningEvents");
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }

    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        String output = new String(serialPortEvent.getReceivedData());
//        System.out.println("BeaconOutputManager: Received: " + output);
        listener.onOutputMessageReceived(output, this.command);
    }

}
