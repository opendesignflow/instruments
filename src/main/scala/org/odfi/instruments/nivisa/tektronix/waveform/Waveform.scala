package org.odfi.instruments.nivisa.tektronix.waveform

import java.io.{ DataOutputStream, File, FileOutputStream }

import scala.collection.mutable.ArrayBuffer
import java.io.DataInputStream
import org.odfi.tea.io.TeaIOUtils
import java.io.FileInputStream
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream
import java.util.zip.ZipFile
import java.util.zip.ZipOutputStream
import java.util.zip.GZIPOutputStream
import org.odfi.instruments.compress.CompressModule


class Waveform(val preamble: Preamble) {

  var values: Option[Array[Double]] = None

  def fromBytes(bytes: Array[Byte]) = {

    preamble.ENCDG match {
      
      case Preamble.BIN =>

        values = Some(bytes.map {
          byte => byte.toDouble
        }.toArray)

      case Preamble.ASC =>
    }

  }

  def getValues = values match {
    case Some(data) => data
    case None => throw new RuntimeException("Waveform has not been initialised with data")
  }
  def setValues(arr: Array[Double]) = {
    this.values = Some(arr)
  }

  def getPointsCount = {
    this.getValues.size
  }

  def toBinaryFile(f: File) = {

    //-- Check compression format
    var fileOutStream = CompressModule.getFileCompressOutputStream(f)

    if (f.exists()) {
      f.delete()
    }

    // Open Binary Stream
    var os = new DataOutputStream(fileOutStream)

    // Write FIrst line with preamble
    os.writeChars(preamble.prString)
    os.writeChar('\n')

    // Write next line with bytes
    this.values.get.foreach(os.writeDouble(_))

    os.flush
    os.close

  }

}

object Waveform {

  def fromBinaryFile(f: File) = {

    //-- Open
    
    var is = new DataInputStream(CompressModule.getFileCompressInputStream(f))

    //-- Take first line
    //-- First Line is preamble
    var preamble = new String
    var c = is.readChar()
    while (c != '\n') {

      preamble += c

      c = is.readChar()
    }

    //var preamble = is.readLine().trim()

    println("Read PR: " + preamble)

    //-- Prepare
    var waveform = new Waveform(new Preamble(preamble))

    //-- Get Data
    println("Size: " + waveform.preamble.NR_Pt.toInt)
    var data = (0 until waveform.preamble.NR_Pt.toInt).map(i => is.readDouble()).toArray
    waveform.setValues(data)

    is.close()
    waveform

  }

}
