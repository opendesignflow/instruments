package org.odfi.instruments.tektronix

import java.io.File

import org.odfi.indesign.core.harvest.Harvest
import org.odfi.instruments.data.XWaveform
import org.odfi.instruments.nivisa.tektronix.{TDS2002B, TekTronixDevice, TekTronixOsci, TektronixHarvester}
import org.odfi.instruments.nivisa.{VISADevice, VISAHarvester}
object TryTekOsci extends App {

  //-- Gather devices
  Harvest.addHarvester(VISAHarvester)
  Harvest.run
var waveform1= new XWaveform
  println("Detected VISA devices: "+VISAHarvester.getResources.size)
  println("Detected tek devices: "+TektronixHarvester.getResources.size)


 /* TektronixHarvester.getResources.foreach {
    p =>
      println("OSCI: "+p)
  }
*/
 var osci = TektronixHarvester.getResource[TDS2002B].get

waveform1=osci.getWaveform
  //-- Save to File
  var out = new File("target/test-out")
  out.mkdirs

  waveform1.toFile(new File(out, "waveform-2500-simple.xml"))
   /*h.onResources[TekTronixOsci]{
     case r=>   println("ok")
  true
  }*/


}