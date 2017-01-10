package kit.ipe.adl.instruments.nivisa

import kit.ipe.adl.instruments.osci.OSCIDevice

class VISAOsciDevice(val baseDevice: VISADevice) extends VISADevice(baseDevice) with OSCIDevice { 
  deriveFrom(baseDevice)
  
  
  
  
}