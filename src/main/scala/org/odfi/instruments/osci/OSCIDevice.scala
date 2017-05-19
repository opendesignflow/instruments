package org.odfi.instruments.osci

import org.odfi.indesign.module.measurement.MeasurementDevice
import org.odfi.instruments.data.XWaveform
import org.odfi.indesign.core.harvest.Harvester

object OSCIDeviceHarvester extends Harvester {

  this.onDeliverFor[OSCIDevice] {
    case d =>
      gather(d)
      true
  }

}

trait OSCIDevice extends MeasurementDevice {

  var osciTriggerPollWaitMs = 1

  // Acquisitation control
  //------------
  def selectChannel(channel: Int) 
  
  def enableSingle: Unit
  def enableRun : Unit

  // Trigger
  //-------------------
  def isTriggered: Boolean

  /**
   * Enable single mode then wait for triggers
   */
  def onTriggered(cl: OSCIDevice => Boolean): Unit = {

    this.enableSingle
    var continue = true
    while (continue) {

      

      isTriggered match {
        case true =>

          // Call handling closure
          continue = cl(this)

          // Start in single mode again
          this.enableSingle

        case false =>
          Thread.sleep(osciTriggerPollWaitMs)
        
      }

    }
    this.enableRun
  }

  def getWaveform: XWaveform

}