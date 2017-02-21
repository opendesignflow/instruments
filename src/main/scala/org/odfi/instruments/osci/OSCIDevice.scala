package org.odfi.instruments.osci

import org.odfi.indesign.module.measurement.MeasurementDevice
import org.odfi.instruments.data.XWaveform

trait OSCIDevice extends MeasurementDevice {

  def getWaveform : XWaveform

}