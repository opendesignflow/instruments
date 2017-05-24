package org.odfi.instruments.nivisa

import org.odfi.instruments.osci.OSCIDevice

abstract class VISAOsciDevice(val baseDevice: VISADevice) extends VISADevice(baseDevice) with OSCIDevice {
  deriveFrom(baseDevice)
  
  
  
  
}