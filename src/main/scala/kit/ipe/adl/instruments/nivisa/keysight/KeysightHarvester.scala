package kit.ipe.adl.instruments.nivisa.keysight

import edu.kit.ipe.adl.indesign.core.harvest.Harvester
import kit.ipe.adl.instruments.nivisa.VISADevice
import kit.ipe.adl.instruments.osci.OSCIUI

object KeysightHarvester extends Harvester {

 

  this.onDeliverFor[VISADevice] {
    case r =>
      println(s"Keysight H delivered device")
      r.getDeviceId match {
        case id if (id.startsWith("AGILENT") && id.contains("DSO-X 2024A")) =>
          gather(new KeysightOsci(r).deriveFrom(r))
          true
        case id if (id.startsWith("AGILENT")) =>
          println(s"Unknown Keysight Device")
          //gather(new KeysightDevice(r))
          false
        case o =>

          println(s"Tnot a Keysight device: " + o)
          false
      }

  }


}