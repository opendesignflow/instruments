package org.odfi.instruments.nivisa.keysight.waveform

import java.io.{DataOutputStream, File, FileOutputStream}

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Tristran on 01.02.2017.
  */
class Waveform(val preamble: Preamble) {

  var values: Option[Array[Double]] = None

  def fromBytes(bytes: Array[Byte]) = {

    preamble.format match {
      case Preamble.FORMAT_WORD =>

        values = Some(bytes.grouped(2).map {
          bytes => (bytes(0) << 8 | bytes(0)).toDouble
        }.toArray)

      case Preamble.FORMAT_BYTE =>
      case Preamble.FORMAT_ASCII =>
    }

  }

  def toBinaryFile(f: File) = {

    if (f.exists()) {
      f.delete()
    }

    // Open Binary Stream
    var os = new DataOutputStream(new FileOutputStream(f))

    // Write FIrst line with preamble
    os.writeChars(preamble.prString)
    os.writeChar('\n')

    // Write next line with bytes
    this.values.get.foreach(os.writeDouble(_))


    os.flush
    os.close
  }

}
