package kit.ipe.adl.instruments.scpi.serial

import edu.kit.ipe.adl.indesign.core.harvest.Harvester

import scala.sys.process._
import kit.ipe.adl.instruments.scpi.rhodeschwarz.RhodeSchwarzVCPHarvester
import java.io.File

object SCPIVCPHarvester extends Harvester {

  this.addChildHarvester(RhodeSchwarzVCPHarvester)

  /**
   * Look for VCP Usb devices
   */
  override def doHarvest = {

    // List TTY in usb-serial
    var p = Process(Seq("ls", "/sys/bus/usb-serial/devices/"))
    var ttys = p.lineStream_!.mkString.split(" ").toList
    println("TTY: " + ttys)

    // Map them to devices, and gather

    var devices = ttys.map { tty =>
      
      
      //-- Get Device folder (not the USB interface, one aboce which is the device itself)
      val folder = new File(s"/sys/bus/usb-serial/devices/$tty/../../")
      
      var scpiDevice = new SCPIVCPDevice(tty,folder.getAbsolutePath)
      scpiDevice
    }
    println(s"D: $devices")
    devices.foreach {d => 
      gather(d)
      //deliver(d)
      
    }

  }

}