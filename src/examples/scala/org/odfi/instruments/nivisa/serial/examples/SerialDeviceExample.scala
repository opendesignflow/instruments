package org.odfi.instruments.nivisa.serial.examples

import org.odfi.instruments.nivisa.serial.VISASerialHarvester
import org.odfi.instruments.nivisa.serial.VISASerialDevice
import org.odfi.instruments.nivisa.VISAHarvester

object SerialDeviceExample extends App {

    // Use the VISA Harvester to list devices
    VISAHarvester.harvest

    // Use Serial Harvester to list serial devices
    VISASerialHarvester.getResources.foreach {
        resource =>
            println("Found resource on VISA Serial")
    }

    // Use Serial Harvester and look for specific VISASerial Resource
    // This returns only the first listed Serial port
    VISASerialHarvester.getResource[VISASerialDevice] match {
        case Some(firstSerial) =>

             // This will send "test" to the port and wait for a response line
             var received = firstSerial.readString("test")

        case None =>
            println("No Serial Listed in VISA")

    }


}