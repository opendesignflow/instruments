package org.odfi.instruments.tektronix

import org.odfi.instruments.nivisa.VISAHarvester
import org.odfi.instruments.nivisa.usb.VISAUSBHarvester
import org.odfi.instruments.nivisa.usb.VISAUSBDevice
import org.odfi.instruments.nivisa.tektronix.TektronixHarvester
import org.odfi.instruments.nivisa.tektronix.MDO3024106

object TryMDO3024 extends App {
  
  VISAHarvester.harvest
  
  VISAUSBHarvester.getResourcesOfType[VISAUSBDevice].foreach {
    r => 
      println("R: "+r.getId+"->"+r.getModelID)
  }
  
  TektronixHarvester.getResources.foreach {
    r => 
      println("R: "+r.getId)
  }
  
  val osci = TektronixHarvester.getResource[MDO3024106].get
  
  osci.selectChannel(1)
  osci.getWaveform
 
  
  
}