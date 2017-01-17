package org.odfi.instruments.nivisa.tektronix

import org.odfi.instruments.nivisa.VISADevice

class TekTronixDevice(val baseDevice : VISADevice) extends VISADevice(baseDevice) {
  deriveFrom(baseDevice)
  
}