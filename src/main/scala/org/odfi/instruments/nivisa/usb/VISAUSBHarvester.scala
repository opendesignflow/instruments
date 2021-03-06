package org.odfi.instruments.nivisa.usb

import org.odfi.indesign.core.harvest.Harvester
import org.odfi.instruments.nivisa.VISADevice
import org.odfi.instruments.nivisa.keysight.KeysightHarvester
import org.odfi.instruments.nivisa.tektronix.TektronixHarvester

object VISAUSBHarvester extends Harvester{

  this.addChildHarvester(TektronixHarvester)
  this.addChildHarvester(KeysightHarvester)

  this.onDeliverFor[VISADevice] {
    case device if (device.isUSB) =>
      //println("Found USB device: "+device.getVendorID)
      gather(new VISAUSBDevice(device))
      true
  }
  
}
class VISAUSBDevice(val d : VISADevice) extends VISADevice(d) {
  this.deriveFrom(d)
}