package org.odfi.instruments.nivisa.keysight

import org.odfi.indesign.core.harvest.Harvester
import org.odfi.instruments.nivisa.VISADevice
import org.odfi.instruments.osci.OSCIUI

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