package org.odfi.instruments.nivisa.serial

import org.odfi.indesign.core.harvest.Harvester
import org.odfi.instruments.nivisa.usb.VISAUSBDevice
import org.odfi.instruments.nivisa.VISADevice

object VISASerialHarvester extends Harvester {
  
 this.onDeliverFor[VISADevice] {
    case device if (device.isSerial) => 
      gather(new VISASerialDevice(device))
      true
  }
  
}
class VISASerialDevice(val d : VISADevice) extends VISADevice(d) {
  this.deriveFrom(d)
}