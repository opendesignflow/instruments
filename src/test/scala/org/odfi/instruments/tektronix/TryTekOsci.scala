package org.odfi.instruments.tektronix

import org.odfi.instruments.nivisa.tektronix.TektronixHarvester
import org.odfi.instruments.nivisa.VISAHarvester

object TryTekOsci extends App {
  
  //-- Use tektronix harvester
  VISAHarvester --> TektronixHarvester
  VISAHarvester.harvest
  
  println("Detected VISA devices: "+VISAHarvester.getResources.size)
  println("Detected tek devices: "+TektronixHarvester.getResources.size)
  
}