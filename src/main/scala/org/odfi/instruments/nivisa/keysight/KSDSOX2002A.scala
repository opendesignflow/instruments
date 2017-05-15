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
class KSDSOX2002A(baseDevice: VISADevice) extends KeysightOsci(baseDevice) {

  

  def onTriggered(cl: KSDSOX2002A => Boolean ): Unit = {

    var continue = true
    while(continue) {

      isTriggered match {
        case true =>



              // Call handling closure
            continue = cl(this)

            // Start in single mode again
          this.write(":SINGLE")


        case false =>
          Thread.sleep(10)
      }

    }
  }

  def onForceTriggered(cl: KSDSOX2002A => Boolean ): Unit = {


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
   /* this.write(s":ACQuire:TYPE NORMAL")
    this.write(s":WAVeform:SOURce CHANnel${channel}")
    this.write(s":WAVeform:FORMat BYTE")
    this.write(s":WAVeform:POINts:MODE NORMAL")
    this.write(s":WAVeform:UNSigned OFF")*/



  }

  def getWaveform(channel:Int,prepareAcquire : Boolean = true) = {
    require (channel >=1 && channel <=4)

    if (prepareAcquire==true) {
      //println(s"Setting acquire")
      this.prepareAcquire(channel)
      //println(s"Set Acquire")
    }


    //println(s"Getting waveform")
    //Thread.sleep(100)

    //-- Set Format
    this.write(s":ACQuire:TYPE NORMAL")
    this.write(s":WAVeform:SOURce CHANnel${channel}")
    this.write(s":WAVeform:FORMat BYTE")
    this.write(s":WAVeform:POINts:MODE NORMAL")
    this.write(s":WAVeform:UNSigned OFF")

    //-- Get Number of points
    var pointsCount = this.readString(":WAVeform:COUNt?")
    //println("POints count: "+pointsCount)

    //-- Get preamble
    var pr = this.readString(":WAVeform:PREamble?")
    //println("Preamble: "+pr)
    var preamble =  new Preamble(pr)

   // println("PR format: "+preamble.format)
   // println("PR points: "+preamble.points)


    //-- Read WF
    //var waveform = new Waveform(preamble)
    var data = this.readIEEE4882Bytes(":WAVeform:DATA?")

    //waveform.fromBytes(data.getData)

    var xwaveform = new  XWaveform()
    xwaveform.data =  data.getData.map { b => b.toInt }
    xwaveform.points = preamble.points
    xwaveform.xIncrement= preamble.dblXIncrement
    xwaveform.xReference = preamble.lngXReference
    xwaveform.xOrigin= preamble.dblXOrigin
    xwaveform.yIncrement=preamble.sngYIncrement
    xwaveform.yOrigin= preamble.sngYOrigin
    xwaveform.yReference= preamble.lngYReference

    //println(s"Done waveform")

    xwaveform



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