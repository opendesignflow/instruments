package org.odfi.instruments.osci

import java.io.File

import org.odfi.indesign.core.module.ui.www.IndesignUIView
import org.odfi.instruments.data.XWaveform
import org.odfi.instruments.nivisa.tektronix.{TDS2002B, TekTronixDevice, TekTronixOsci, TektronixHarvester}

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
      TektronixHarvester.getResource[TDS2002B] match {
        case Some(osci) =>

          "osci-waveform graph" :: div {

            button("Get Waveform") {
              onClick {
                var waveform1= new XWaveform
                waveform1=osci.getWaveform
                var out = new File("target/test-out")
                out.mkdirs
                waveform1.toFile(new File(out, "waveform-2500-simple.xml"))

              }

            }
          }


        case None =>

          "ui message warning" :: "No OSCI is connected"
      }



    }

  }


}