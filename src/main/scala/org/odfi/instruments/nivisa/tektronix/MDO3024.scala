package org.odfi.instruments.nivisa.tektronix

import org.odfi.instruments.nivisa.usb.VISAUSBDevice
import org.odfi.instruments.ieee.IEEE4882BinaryBlock
import org.odfi.instruments.data.XWaveform
import java.io.File

class MDO3024106 (baseDevice: VISAUSBDevice) extends TekTronixOsci(baseDevice) {
  
  /**
   * Makes acquisition OFF then rerun it
   */
  def withStopAndRestartAcquire[T](cl: => T) : T = {
    
    this.write("ACQuire:STATE OFF")
    try {
      cl
    } finally {
      this.write("ACQuire:STATE RUN")
    }
  }
  
  def getWaveform : XWaveform = {


     var waveform =  new XWaveform()
     
     this.write(":DATa:STARt 1")
     this.write(":DATa:STOP 50000")
     
     this.write("DATa:ENCdg RIBINARY")
     this.write("WFMpre:BYT_Nr 1") // 1 byte per point
     this.write(":HEADer 0")


     var points = this.readDouble("WFMpre:NR_Pt?")
     var timeScale = this.readDouble("WFMpre:XINcr?")
     var yoffset = this.readDouble("WFMpre:YOFF?")
     var ymult = this.readDouble("WFMpre:YMUlt?")
     var xunit = this.readString("WFMPre:XUNit?")
     var yunit = this.readString("WFMPre:YUNit?")
     
     //-- Get curve
     var curve = this.readBytes("CURVE?")

     //-- First char must be #
     var dataBlock = new IEEE4882BinaryBlock(Some(curve))

     //-- Convert to int (one byte in one int)
     var dataInt = dataBlock.getData.map { b => b.toInt }

     //-- Save to waveform

     waveform.data = dataInt
     waveform.points = points.toLong
     waveform.xIncrement= timeScale
     waveform.xUnit= xunit
     waveform.yIncrement=ymult
     waveform.yUnit= yunit
     
     //-- Return
     waveform

   }
  
  
  def getJPEGHardcopy = {
    this.write("HARDCopy:FORMat JPEG")
    this.write("HARDCopy:LAYout LANdscape")
    this.write("HARDCopy:PORT USB")
    var image = this.readBytes("HARDCopy:START")
    var out = new File("target/test-out")
    out.mkdirs


  }
}