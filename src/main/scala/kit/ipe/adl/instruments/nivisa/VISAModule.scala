package kit.ipe.adl.instruments.nivisa

import edu.kit.ipe.adl.indesign.core.module.IndesignModule
import org.bridj.BridJ
import edu.kit.ipe.adl.indesign.core.harvest.Harvest

class VISAModule extends IndesignModule{
  
  
  def load = {
    Harvest.addHarvester(VISAHarvester)
  }
  
  this.onInit {
    BridJ.addLibraryPath("/usr/local/lib64")
  }
}