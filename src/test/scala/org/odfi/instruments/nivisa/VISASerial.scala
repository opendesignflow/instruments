package org.odfi.instruments.nivisa

import org.odfi.instruments.nivisa.serial.VISASerialHarvester
import org.odfi.instruments.nivisa.serial.VISASerialDevice

object VISASerial extends App {
  
    var h = VISAHarvester
  h.harvest
  
  VISASerialHarvester.getResource[VISASerialDevice] match {
      case Some(firstSerial) =>
        println("FOund serial: "+firstSerial.getId)
        
        firstSerial.open
        
        //firstSerial.write("test")
        
        var received = firstSerial.readString("test")
        
        println("REceived: "+received)
        
        firstSerial.close
      case None => 
    }
  
  
}