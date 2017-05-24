package org.odfi.instruments.keysight

import org.odfi.instruments.nivisa.VISAHarvester
import org.odfi.instruments.nivisa.usb.VISAUSBDevice
import org.odfi.instruments.nivisa.usb.VISAUSBHarvester
import org.odfi.instruments.nivisa.keysight.KeysightHarvester
import org.odfi.indesign.core.harvest.Harvest
import org.odfi.instruments.nivisa.keysight.wavegen.KSTrueForm33200B

object TryKSFuncgen extends App {
  
  //Harvest.addHarvester(VISAHarvester)
  //Harvest.run
  VISAHarvester.harvest
  VISAHarvester.getResources.foreach {
    r => 
      println("R: "+r)
  }
  
  VISAUSBHarvester.getResources.foreach {
    r => 
      println("R: "+r.getId)
  }
  
  
  KeysightHarvester.getResources.foreach {
    r => 
      println("KS R:" +r.getId)
  }
  
  var wgen = KeysightHarvester.getResource[KSTrueForm33200B].get
  
  wgen.outputSin(1000000,0,2,90)
  
  
  
}