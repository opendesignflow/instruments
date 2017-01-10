package kit.ipe.adl.instruments.scpi

import edu.kit.ipe.adl.indesign.core.harvest.Harvester
import kit.ipe.adl.instruments.scpi.serial.SCPIVCPHarvester
import kit.ipe.adl.instruments.scpi.rhodeschwarz.RhodeSchwarzVCPHarvester

object SCPIHarvester extends Harvester {

  this.addChildHarvester(SCPIVCPHarvester)
  
}
