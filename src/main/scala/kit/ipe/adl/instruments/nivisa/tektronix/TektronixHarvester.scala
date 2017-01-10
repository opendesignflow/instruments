package kit.ipe.adl.instruments.nivisa.tektronix

import edu.kit.ipe.adl.indesign.core.harvest.Harvester
import kit.ipe.adl.instruments.nivisa.VISADevice
import kit.ipe.adl.instruments.osci.OSCIUI

object TektronixHarvester extends Harvester {


  this.onDeliverFor[VISADevice] {
    case r =>

      println(s"TEK H delivered device")
      r.getDeviceId match {
        case id if (id.startsWith("TEKTRONIX") && id.contains("MDO3024")) =>
          gather(new TekTronixOsci(r).deriveFrom(r))
          true
        case o =>

          println(s"TEK H delivered device, not a TEK device: " + o)
          false
      }
  }


}