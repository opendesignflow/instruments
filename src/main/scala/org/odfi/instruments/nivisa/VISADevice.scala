package org.odfi.instruments.nivisa

import org.odfi.indesign.module.measurement.MeasurementDevice
import org.odfi.instruments.nivisa.VisaLibrary;

import org.bridj.Pointer
import java.io.ByteArrayOutputStream

class VISADevice(val deviceString: String) extends MeasurementDevice {

  def this(d: VISADevice) = this(d.deviceString)

  var deviceSession: Option[Long] = None

  // ID And descriptions
  def getId = deviceString

  /**
   * This method will open the interface if not openend and close it again
   * If the interface is openend, it won't close it
   */
  def getInterfaceDescription = {

    var desc = isOpen match {
      case true =>
        var description = Pointer.allocateChars(256)
        VisaLibrary.viGetAttribute(deviceSession.get, VisaLibrary.VI_ATTR_INTF_INST_NAME, description)
        description.getCString
      case false =>
        try {
          requireOpen
          var description = Pointer.allocateChars(256)
          VisaLibrary.viGetAttribute(deviceSession.get, VisaLibrary.VI_ATTR_INTF_INST_NAME, description)
          description.getCString
        } catch {
          case e: Throwable => "Error while reading description: " + e.getLocalizedMessage
        }
    }
    desc

  }

  def requireOpen = deviceSession match {
    case Some(session) =>
    case None => open
  }

  def isOpen = this.deviceSession.isDefined

  def open = {

    //-- Prepare Session or isntrument
    var instrumentSession = Pointer.allocateCLong()
    VisaLibrary.viOpen(VISA.getResourceManagerHandler, Pointer.pointerToCString(deviceString), VisaLibrary.VI_NULL, VisaLibrary.VI_NULL, instrumentSession) match {

      case 0 =>
        println(s"Opened Device: ${deviceString}")
        sys.addShutdownHook(this.close)
      case other =>
        throw new RuntimeException(s"An Error Occured while opening device ${deviceString} code=$other, message=${VISA.getStatusDesc(other)}")

    }
    this.deviceSession = Some(instrumentSession.getInt)

  }

  def close = deviceSession match {
    case Some(session) =>
      this.deviceSession = None
      VisaLibrary.viClose(session)
    case None =>
  }

  // Type
  //--------------
  def isUSB = {
    this.getId.startsWith("USB")
  }
  def isSerial = {
    this.getId.startsWith("ASRL")
  }

  // USB
  //-----------
  def getVendorID = {
    require(isUSB)
    this.getId.split("::")(1)
  }

  def getProductID = {
    require(isUSB)
    this.getId.split("::")(2)
  }

  def getModelID = {
    require(isUSB)
    this.getId.split("::")(3)
  }

  // I/O
  //-----------------

  def readString(command: String): String = {
    this.write(command)

    // Read bytes as string 
    new String(this.readBytes)

  }

  def readDouble(command: String): Double = {
    this.write(command)
    new String(this.readBytes).toDouble
  }

  def readBytes(command: String): Array[Byte] = {
    this.write(command)
    this.readBytes
  }

  def readBytes: Array[Byte] = {

    //-- Read
    var resBytes = new ByteArrayOutputStream
    var readBuffer = Pointer.allocateBytes(4096)
    var readCount = Pointer.allocateCLong()
    var continue = true
    while (continue) {
      VisaLibrary.viRead(deviceSession.get, readBuffer, 4096, readCount) match {
        case 0 =>
          println(s"Read: ${readCount.getInt}")
          resBytes.write(readBuffer.getBytes(readCount.getInt))
          continue = false
        //Some(new String(readBuffer.getBytes(readCount.getInt).dropRight(1)))

        // Maybe more available
        case 1073676294 =>

          // Write and continue
          resBytes.write(readBuffer.getBytes(readCount.getInt))
          continue = true

        case other =>
          throw new RuntimeException(s"Error While Reading data to device $deviceString, code=$other, message=${VISA.getStatusDesc(other)}")
      }
    }

    resBytes.toByteArray()

  }

  /**
   * \n is added to the end of string if required
   */
  def write(command: String): Unit = {
    requireOpen

    var finalCommand = command.last match {
      case '\n' => command
      case _ => command + "\n"
    }

    //-- Write
    var totalWritten = 0
    while (totalWritten < finalCommand.length()) {
      var written = Pointer.allocateCLong()
      VisaLibrary.viWrite(deviceSession.get, Pointer.pointerToCString(finalCommand), finalCommand.length().toLong, written) match {
        case 0 =>
          totalWritten += written.getInt
        case other =>
          throw new RuntimeException(s"Error While Writting data to device $deviceString, code=$other ")
      }
    }

  }

  // Utils
  //--------------

  def getDeviceId = {
    this.readString("*IDN?")

  }

}