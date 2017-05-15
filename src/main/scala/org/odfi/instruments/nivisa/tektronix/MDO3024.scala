package org.odfi.instruments.nivisa.tektronix

import org.odfi.instruments.nivisa.usb.VISAUSBDevice
import org.odfi.instruments.ieee.IEEE4882BinaryBlock
import org.odfi.instruments.data.XWaveform
import java.io.File

class MDO3024106 (baseDevice: VISAUSBDevice) extends TekTronixOsci(baseDevice) {
  
  
  /**
   * Warning, this method does not stop the oscilloscope for acquire
   * If you want to force stop, you should use acquireOff/Run or withAcquireStopAnRestart
   * yourself
   * 
   * WARNING: the Y offset is removed from the data here and Y origin set to 0
   * This is because the Y Origin in XWaveform is supposed to be a real value not a digitizing level value
   * And the osci gives 0 back for this value all the time, but offset, which is in digitizing value is correct.
   */
  def getWaveform : XWaveform = {


     var waveform =  new XWaveform()
     
     this.write(":DATa:STARt 1")
     this.write(":DATa:STOP 50000")
     
     this.write("DATa:ENCdg RIBINARY")
     this.write("WFMpre:BYT_Nr 1") // 1 byte per point
     this.write(":HEADer 0")


     var points = this.readDouble("WFMOutpre:NR_Pt?")
     var timeScale = this.readDouble("WFMOutpre:XINcr?")
     var yoffset = this.readDouble("WFMOutpre:YOFF?")
     var ymult = this.readDouble("WFMOutpre:YMUlt?")
     var xunit = this.readString("WFMOutpre:XUNit?")
     var yunit = this.readString("WFMOutpre:YUNit?")
     var yorigin = this.readDouble("WFMOutpre:YZero?")
     
       //println("Origin is: "+yorigin, "offset: "+yoffset)
     //-- Get curve
     var curve = this.readBytes("CURVE?")

     //-- First char must be #
     var dataBlock = new IEEE4882BinaryBlock(Some(curve))

     //-- Convert to int (one byte in one int)
     var dataInt = dataBlock.getData.map { b => (b - yoffset).toInt }

     //-- Save to waveform

     waveform.data = dataInt
     waveform.points = points.toLong
     waveform.xIncrement= timeScale
     waveform.xUnit= xunit
     waveform.yIncrement=ymult
     waveform.yUnit= yunit
     waveform.yOrigin = yorigin
     
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