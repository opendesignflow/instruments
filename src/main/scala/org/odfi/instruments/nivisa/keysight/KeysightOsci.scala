package org.odfi.instruments.nivisa.keysight

import org.odfi.instruments.data.XWaveform
import org.odfi.instruments.nivisa.VISADevice
import org.odfi.instruments.nivisa.tektronix.TekTronixDevice
import org.odfi.instruments.osci.OSCIDevice
import org.odfi.instruments.nivisa.VISAOsciDevice

class KeysightOsci(baseDevice: VISADevice) extends VISAOsciDevice(baseDevice) with KeysightDevice {

  /**
   * Returns a PNG format screenshot
   */
  def saveScreen(name:String) : Unit = {

   
    val fn = """bla.png"""
    //-- Save image
    this.write("SAVe:IMAGe:Format PNG")
    this.write(s"""SAVe:IMAGe "${name}"""")

   // Thread.sleep(3000)
    
    //-- Get Image
   // var bytesPNG = this.readBytes(s""":RECall:FILename "${name}"""")

    //bytesPNG
    //Array[Byte]()
  }

  def getWaveform : XWaveform = {
    XWaveform()
  }

}