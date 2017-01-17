package org.odfi.instruments.scpi.rhodeschwarz

import org.odfi.instruments.scpi.serial.SCPIVCPDevice
import org.odfi.indesign.core.harvest.HarvestedResource

class RhodeSchwarzVCPDevice(val baseDevice : SCPIVCPDevice,val serial:String) extends HarvestedResource {
  
  this.deriveFrom(baseDevice)
  
  def getId = baseDevice.getId
}