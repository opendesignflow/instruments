package org.odfi.instruments.nivisa.tektronix

import org.odfi.instruments.data.XWaveform
import org.odfi.instruments.nivisa.VISADevice
import org.odfi.instruments.ieee.IEEE4882BinaryBlock
import org.odfi.instruments.osci.OSCIDevice

abstract class TekTronixOsci(baseDevice: VISADevice) extends TekTronixDevice(baseDevice) with OSCIDevice {

  
  // Channels
  //------------------
  
  def selectChannel(channel:Int) = {
    this.write(s":DATa:SOUrce CH${channel}")
  }
  
  // Acquire
  //---------------
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
  
  def acquireOff = {
    this.write("ACQuire:STATE OFF")
  }
  
  def acquireRun = {
    this.write("ACQuire:STATE RUN")
  }
  
  
  
  def getPNGScreen = {

    //-- Save image
    write("SAVe:IMAge:FILEFormat PNG")
    write("SAVe:IMAge \"E:/scr.png\"")

    //-- Get Image
    readBytes("FILESystem:READFile \"E:/scr.png\"")

    

  }




}