package org.odfi.instruments.nivisa

import org.odfi.instruments.scpi.SCPIHarvester
import org.odfi.indesign.core.harvest.Harvest

object TrySCPI extends App {
  
  Harvest.addHarvester(SCPIHarvester)

  
  
  Harvest.run
  
  Harvest.printHarvesters
}