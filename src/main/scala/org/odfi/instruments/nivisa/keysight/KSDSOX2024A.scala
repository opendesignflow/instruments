package org.odfi.instruments.nivisa.keysight

import java.io.{ByteArrayInputStream, File}
import javax.imageio.ImageIO

import com.idyria.osi.tea.io.TeaIOUtils
import org.odfi.instruments.data.XWaveform
import org.odfi.instruments.nivisa.VISADevice
import org.odfi.instruments.nivisa.keysight.waveform.{Preamble, Waveform}

/**
  * Created by Tristran on 31.01.2017.
  */
class KSDSOX2024A(baseDevice: VISADevice) extends KeysightOsci(baseDevice) {

  

  def onForceTriggered(cl: KSDSOX2024A => Boolean ): Unit = {


    // Clear status
    this.write("*CLS")

    var continue = true
    while(continue) {

      // Start in single mode again
      this.write(":SINGLE")

      // Force Trigger
      forceTrigger
      //isTriggered

      // Call handling closure
      continue = cl(this)

      if (!continue) {
        // Start in single mode again
        this.write(":SINGLE")
      }



    }


  }



  //var preamble : Option[Preamble] =  None

  def prepareAcquire(channel:Int) = {

    //-- Set Format
    this.write(s":ACQuire:TYPE NORMAL")
    this.write(s":WAVeform:SOURce CHANnel${channel}")
    this.write(s":WAVeform:FORMat BYTE")
    this.write(s":WAVeform:POINts:MODE NORMAL")
    this.write(s":WAVeform:UNSigned OFF")



  }

  

  def getScreenPNG = {

    var data = this.readIEEE4882Bytes(":DISPlay:DATA? PNG, COLOR")

    /*data.getData.foreach {
      v =>
        println("IMG data: "+v+ " -> "+v.toInt.toHexString)

    }

    TeaIOUtils.writeToFile(new File("osci.png"),new ByteArrayInputStream(data.getData))*/

    ImageIO.read(new ByteArrayInputStream(data.getData))

  }

}
