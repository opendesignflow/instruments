package org.odfi.instruments.nivisa.tektronix

import org.odfi.instruments.data.XWaveform
import org.odfi.instruments.nivisa.VISADevice
import org.odfi.instruments.ieee.IEEE4882BinaryBlock
import org.odfi.instruments.osci.OSCIDevice

abstract class TekTronixOsci(baseDevice: VISADevice) extends TekTronixDevice(baseDevice) with OSCIDevice {

  
  def selectChannel(channel:Int) = {
    this.write(s":DATa:SOUrce CH${channel}")
  }
  
  def getPNGScreen = {

    //-- Save image
    write("SAVe:IMAge:FILEFormat PNG")
    write("SAVe:IMAge \"E:/scr.png\"")

    //-- Get Image
    readBytes("FILESystem:READFile \"E:/scr.png\"")

    

  }




}