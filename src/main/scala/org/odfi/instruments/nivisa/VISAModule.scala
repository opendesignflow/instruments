package org.odfi.instruments.nivisa

import org.odfi.indesign.core.module.IndesignModule
import org.bridj.BridJ
import org.odfi.indesign.core.harvest.Harvest

class VISAModule extends IndesignModule{
  
  
  def load = {
    Harvest.addHarvester(VISAHarvester)
  }
  
  this.onInit {
    BridJ.addLibraryPath("/usr/local/lib64")
  }
}