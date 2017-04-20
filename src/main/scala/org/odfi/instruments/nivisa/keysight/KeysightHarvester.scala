package org.odfi.instruments.nivisa.keysight

import org.odfi.indesign.core.harvest.Harvester
import org.odfi.instruments.nivisa.VISADevice
import org.odfi.instruments.nivisa.usb.VISAUSBDevice
import org.odfi.instruments.osci.OSCIUI

object KeysightHarvester extends Harvester {

  this.onDeliverFor[VISAUSBDevice] {
    case r if (r.getVendorID == "0x0957") =>
      //println(s"Keysight H delivered device")

      //-- Device Map
      r.getProductID match {
        case "0x1796" =>
          gather(new KSDSOX2024A(r))
          true
        case "0x179B" =>
          gather(new KSDSOX2002A(r))
          true
        case other =>
          false
      }

    case r if (r.getVendorID == "0x2A8D") =>

      //-- Device Map
      r.getProductID match {
        case "0x1772" =>
          gather(new KMSOX3054T(r))
          true
        case other =>
          false
      }

    case other =>
      println("KS got another usb device")
      false

  }

}