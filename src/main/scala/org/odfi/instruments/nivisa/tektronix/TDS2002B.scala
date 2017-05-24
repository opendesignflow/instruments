package org.odfi.instruments.nivisa.tektronix
import java.io.{ByteArrayInputStream, File}
import javax.imageio.ImageIO
import java.io.File
import com.idyria.osi.tea.io.TeaIOUtils
import org.odfi.instruments.data.XWaveform
import org.odfi.instruments.ieee.IEEE4882BinaryBlock
import org.odfi.instruments.nivisa.VISADevice
import org.odfi.instruments.nivisa.tektronix.waveform.{Preamble, Waveform}
import org.odfi.instruments.nivisa.usb.VISAUSBDevice

/**
  *
  * @param baseDevice
  */
class TDS2002B(baseDevice: VISAUSBDevice) extends TekTronixOsci(baseDevice) {

   /*def getWaveform : XWaveform = {

     println(s"Getting Curve...")

     var waveform =  new XWaveform()

     //- Set Output parameters

     this.write(":DATa:SOUrce CH1")
     this.write(":DATa:STARt 1")
     this.write(":DATa:STOP 2500")
     this.write("DATa:ENCdg RIBINARY")
     this.write("WFMpre:BYT_Nr 1") // 1 byte per point
     this.write(":HEADer 0")

     //--- get the parameters to transform to time and voltage ---
     /*var timeScale = this.readDouble("WFMOutpre:XINcr?")
     var yoffset = this.readDouble("WFMOutpre:YOFF?")
     var ymult = this.readDouble("WFMOutpre:YMULT?")
*/

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
     //-- Save to File
     waveform

   }*/
  def getJPEGHardcopy = {
    this.write("HARDCopy:FORMat JPEG")
    this.write("HARDCopy:LAYout LANdscape")
    this.write("HARDCopy:PORT USB")
    var image = this.readBytes("HARDCopy:START")
    var out = new File("target/test-out")
    out.mkdirs


  }

}