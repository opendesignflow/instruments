package org.odfi.instruments.nivisa.keysight

import org.odfi.instruments.data.XWaveform
import org.odfi.instruments.nivisa.VISADevice
import org.odfi.instruments.nivisa.tektronix.TekTronixDevice
import org.odfi.instruments.osci.OSCIDevice
import org.odfi.instruments.nivisa.VISAOsciDevice

class KeysightOsci(baseDevice: VISADevice) extends VISAOsciDevice(baseDevice) with KeysightDevice {

  def isTriggered = this.readString(":TER?").toInt match {case 1 => true ; case 0 => false}

  def forceTrigger = {

    this.write(":TRIGger:FORCe")


  }
  
  
  /**
   * Returns a PNG format screenshot
   */
  def saveScreenBytesPNG  = {


    
    this.readIEEE4882Bytes(":DISPlay:DATA? PNG, COLOR")
    
    
  }

  def getWaveform : XWaveform = {
    new XWaveform()
  }

}