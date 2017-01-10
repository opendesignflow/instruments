package kit.ipe.adl.instruments.scpi.rhodeschwarz

import kit.ipe.adl.instruments.scpi.serial.SCPIVCPDevice
import edu.kit.ipe.adl.indesign.core.harvest.HarvestedResource

class RhodeSchwarzVCPDevice(val baseDevice : SCPIVCPDevice,val serial:String) extends HarvestedResource {
  
  this.deriveFrom(baseDevice)
  
  def getId = baseDevice.getId
}