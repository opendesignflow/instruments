package kit.ipe.adl.instruments.nivisa

import kit.ipe.adl.instruments.scpi.SCPIHarvester
import edu.kit.ipe.adl.indesign.core.harvest.Harvest

object TrySCPI extends App {
  
  Harvest.addHarvester(SCPIHarvester)

  
  
  Harvest.run
  
  Harvest.printHarvesters
}