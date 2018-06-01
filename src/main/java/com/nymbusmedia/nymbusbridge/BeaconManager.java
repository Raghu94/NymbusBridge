package com.nymbusmedia.nymbusbridge;

import com.fazecast.jSerialComm.SerialPort;
import com.nymbusmedia.nymbusbridge.exception.AirCableNotFoundException;

public class BeaconManager {
    public void setAirBeaconPort(SerialPort airBeaconPort) {
        this.airBeaconPort = airBeaconPort;
    }

    private SerialPort airBeaconPort;

    public SerialPort getAirBeaconPort() {
        return airBeaconPort;
    }

    public void scan() throws AirCableNotFoundException {
        SerialPort[] comPorts = SerialPort.getCommPorts();
        System.out.println("List of available ports: ");
        int portNumber = 0;
        for(SerialPort serialPort : comPorts) {
            System.out.println( serialPort.getDescriptivePortName() + ":" + serialPort.getSystemPortName());
            if(Constants.AIR_CABLE_SYSTEM_PORT_NAME.equals(serialPort.getSystemPortName())){
                setAirBeaconPort(serialPort);
                return;
            }
            portNumber++;
        }
        throw new AirCableNotFoundException("AirCable is not present in the list of open COM ports");
    }

    public void setup() {
        SerialPort airBeacon = getAirBeaconPort();
        airBeacon.setComPortParameters(115200, 8, 1, SerialPort.NO_PARITY);
    }



    public boolean isOpen() {
        return getAirBeaconPort().isOpen();
    }

    public void execute(String command, OutputMessageListener outputMessageListener) {
        SerialPort airBeacon = getAirBeaconPort();
        airBeacon.addDataListener(new BeaconOutputManager(outputMessageListener, command));
        airBeacon.openPort();
        try {
            if(airBeacon.bytesAvailable() >= 0) {
                byte[] writeBuffer = command.getBytes(); // @b command
                int length = writeBuffer.length;
                airBeacon.writeBytes(writeBuffer, length);
                Thread.sleep(2000);

            }
        } catch(Exception e) {e.printStackTrace();}
        airBeacon.closePort();
        airBeacon.removeDataListener();
    }

}
