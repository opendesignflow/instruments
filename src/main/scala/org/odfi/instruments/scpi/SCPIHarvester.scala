package org.odfi.instruments.scpi

import org.odfi.indesign.core.harvest.Harvester
import org.odfi.instruments.scpi.serial.SCPIVCPHarvester
import org.odfi.instruments.scpi.rhodeschwarz.RhodeSchwarzVCPHarvester

object SCPIHarvester extends Harvester {

  this.addChildHarvester(SCPIVCPHarvester)
  
}
