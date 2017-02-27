package org.odfi.instruments.data


import com.idyria.osi.ooxoo.model.ModelBuilder
import com.idyria.osi.ooxoo.model.producer
import com.idyria.osi.ooxoo.model.producers
import com.idyria.osi.ooxoo.model.out.markdown.MDProducer
import com.idyria.osi.ooxoo.model.out.scala.ScalaProducer
import com.idyria.osi.ooxoo.core.buffers.structural.io.sax.STAXSyncTrait


@producers(Array(
  new producer(value = classOf[ScalaProducer]),
  new producer(value = classOf[MDProducer])))
object InstrumentsDataModel extends ModelBuilder {
  
  "XWaveform" is {
    
    withTrait("org.odfi.instruments.compress.XMLCompressOutput")
    
    attribute("name")
    
    
    
    // External
    attribute("externalFile") ofType("string")
  
    // POints  count
    "Points" ofType "long"
    "XReference"  ofType "double"
    "XIncrement" ofType "double"
    "XUnit" ofType "string"
    "YReference" ofType "double"
    "YIncrement" ofType "double"
    "YUnit" ofType "string"
    
    // If embedded data
    "Data" ofType("intbinary")
    
  }
  
}