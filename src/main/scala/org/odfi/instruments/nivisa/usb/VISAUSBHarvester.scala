package org.odfi.instruments.nivisa.usb

import org.odfi.indesign.core.harvest.Harvester
import org.odfi.instruments.nivisa.VISADevice

object VISAUSBHarvester extends Harvester{
  
  this.onDeliverFor[VISADevice] {
    case device if (device.isUSB) => 
      gather(new VISAUSBDevice(device))
      true
  }
  
}
class VISAUSBDevice(val d : VISADevice) extends VISADevice(d) {
  this.deriveFrom(d)
}