
Visa Serial Device Usage
=========================================

VISA can list Serial devices and send/receive lines very easily

To use a Serial Device you can use the `VISASerialHarvester` which is set on the main VISAHarvester detector

.. code-block:: scala
    :linenos:
    
    import org.odfi.instruments.nivisa.serial.VISASerialHarvester
    import org.odfi.instruments.nivisa.serial.VISASerialDevice 
    
    object SerialDeviceExample extends App {
        
        // Use the VISA Harvester to list devices
        VISAHarvester.harvest
        
        // Use Serial Harvester to list serial devices
        VISASerialHarvester.getResources.foreach {
            resource => 
                println("Found resource on VISA Serial")
        }
        
        // Use Serial Harvester and look for specific VISASerial Resource
        // This returns only the first listed Serial port
        VISASerialHarvester.getResource[VISASerialDevice] match {
            case Some(firstSerial) =>
                
                 // This will send "test" to the port and wait for a response line
                 var received = firstSerial.readString("test")
                
            case None => 
                println("No Serial Listed in VISA")     
            
        }
    
    
    }