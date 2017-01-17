package org.odfi.instruments.osci

import org.odfi.indesign.core.module.ui.www.IndesignUIView
import org.odfi.instruments.nivisa.tektronix.TektronixHarvester
import org.odfi.instruments.nivisa.tektronix.TekTronixDevice
import org.odfi.instruments.nivisa.tektronix.TekTronixOsci

class OSCIUI extends IndesignUIView {
  
   
  this.viewContent {
    
    div {
      h2("Osci Generic Interface 2") {
        
      }
      p {
        textContent("Use this page to manage an OSCI")
      }
      
      // Device Selection
      //---------------------
      "ui table" :: table {
        
        thead {
          tr {
            th("Id") {
              
            }
            th("VISA ID") {
              
            }
            th("Name") {
              
            }
          }
        }
        
         tbody {
         
          TektronixHarvester.onResources[TekTronixOsci] {
            tekDevice => 
              tr {
                td(s"${tekDevice.hashCode()}") {
                  
                }
                td(s"${tekDevice.getId}") {
                  
                }
                td(s"${tekDevice.getDeviceId}") {
                  
                }
              }
          }
          
        }
      }
      
      
      // WaveForm
      //--------------------
      "osci-waveform graph" :: div {
        
        input {
          +@("type" -> "button")
          textContent("Get WaveForm")
        }
      }
      
      // Screen Shot
      "osci-screen" ::div {
        input {
          +@("type" -> "button")
          textContent("Get Screen")
        }
      }
      
    }
    
  }
  
  
}