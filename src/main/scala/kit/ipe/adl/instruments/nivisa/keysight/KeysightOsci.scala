package kit.ipe.adl.instruments.nivisa.keysight

import kit.ipe.adl.instruments.nivisa.VISADevice
import kit.ipe.adl.instruments.nivisa.tektronix.TekTronixDevice
import kit.ipe.adl.instruments.osci.OSCIDevice
import kit.ipe.adl.instruments.nivisa.VISAOsciDevice

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

}