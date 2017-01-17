package org.odfi.instruments.scpi.serial

import org.odfi.instruments.scpi.SCPIDevice

class SCPIVCPDevice(val ttyName : String,val devicePath : String) extends SCPIDevice {
   
  def getId = devicePath
  
  // Configuration
  //var vaus
  
  // Opening
  
  
}