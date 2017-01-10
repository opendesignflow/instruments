package kit.ipe.adl.instruments.scpi.serial

import kit.ipe.adl.instruments.scpi.SCPIDevice

class SCPIVCPDevice(val ttyName : String,val devicePath : String) extends SCPIDevice {
   
  def getId = devicePath
  
  // Configuration
  //var vaus
  
  // Opening
  
  
}