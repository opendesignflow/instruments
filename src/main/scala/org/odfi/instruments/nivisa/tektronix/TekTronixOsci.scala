package org.odfi.instruments.nivisa.tektronix

import org.odfi.instruments.nivisa.VISADevice
import org.odfi.instruments.ieee.IEEE4882BinaryBlock
import org.odfi.instruments.osci.OSCIDevice

class TekTronixOsci( baseDevice : VISADevice) extends TekTronixDevice(baseDevice) with OSCIDevice {
  
  
  def getCurve = {
    
    println(s"Getting Curve...")
    
    //- Set Output parameters
    
    
    this.write(":DATa:SOUrce CH1")
    this.write(":DATa:STARt 1")
    this.write(":DATa:STOP 10000")
    this.write("DATA:ENCDG RIBINARY")
    this.write("WFMOutpre:BYT_Nr 1") // 1 byte per point
    this.write(":HEADer 0")
    
    //--- get the parameters to transform to time and voltage ---
    var timeScale = this.readDouble("WFMOutpre:XINcr?")
    var yoffset = this.readDouble("WFMOutpre:YOFF?")
    var ymult = this.readDouble("WFMOutpre:YMULT?")
    
    
    
    //-- Get curve
    var curve = this.readBytes("CURVE?")
    
    //-- First char must be #
    var dataBlock = new IEEE4882BinaryBlock(Some(curve))
    
    curve
    
  }
}