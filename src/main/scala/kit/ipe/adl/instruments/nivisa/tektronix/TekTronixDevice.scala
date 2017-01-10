package kit.ipe.adl.instruments.nivisa.tektronix

import kit.ipe.adl.instruments.nivisa.VISADevice

class TekTronixDevice(val baseDevice : VISADevice) extends VISADevice(baseDevice) {
  deriveFrom(baseDevice)
  
}