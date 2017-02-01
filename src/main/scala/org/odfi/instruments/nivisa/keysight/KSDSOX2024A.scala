package org.odfi.instruments.nivisa.keysight

import org.odfi.instruments.nivisa.VISADevice
import org.odfi.instruments.nivisa.keysight.waveform.{Preamble, Waveform}

/**
  * Created by Tristran on 31.01.2017.
  */
class KSDSOX2024A(baseDevice: VISADevice) extends KeysightOsci(baseDevice) {

  def hasAcquired = {



  }

  def getWaveform(channel:Int) = {
    require (channel >=1 && channel <=4)

    //-- Set Format
    this.write(s":WAVeform:SOURce CHANnel${channel}")
    this.write(s":WAVeform:FORMat WORD")
    this.write(s":WAVeform:POINts:MODE NORMAL")
    this.write(s":WAVeform:UNSigned OFF")

    //-- Get Number of points
    var pointsCount = this.readString(":WAVeform:COUNt?")
    println("POints count: "+pointsCount)

    //-- Get preamble
    var pr = this.readString(":WAVeform:PREamble?")
    println("Preamble: "+pr)
    var preamble =  new Preamble(pr)

    println("PR format: "+preamble.format)
    println("PR points: "+preamble.points)


    //-- Read WF
    var waveform = new Waveform(preamble)
    var data = this.readIEEE4882Bytes(":WAVeform:DATA?")
    waveform.fromBytes(data.getData)


    waveform



  }

}
