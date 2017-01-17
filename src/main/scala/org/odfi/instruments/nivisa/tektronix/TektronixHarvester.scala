package org.odfi.instruments.nivisa.tektronix

import org.odfi.indesign.core.harvest.Harvester
import org.odfi.instruments.nivisa.VISADevice
import org.odfi.instruments.osci.OSCIUI

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