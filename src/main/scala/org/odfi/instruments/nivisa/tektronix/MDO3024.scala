package org.odfi.instruments.nivisa.tektronix

import org.odfi.instruments.nivisa.usb.VISAUSBDevice
import org.odfi.instruments.ieee.IEEE4882BinaryBlock
import org.odfi.instruments.data.XWaveform
import java.io.File

class MDO3024106 (baseDevice: VISAUSBDevice) extends TekTronixOsci(baseDevice) {
  
  
   
  
  
  def getJPEGHardcopy = {
    this.write("HARDCopy:FORMat JPEG")
    this.write("HARDCopy:LAYout LANdscape")
    this.write("HARDCopy:PORT USB")
    var image = this.readBytes("HARDCopy:START")
    var out = new File("target/test-out")
    out.mkdirs


  }
}