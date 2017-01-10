package kit.ipe.adl.instruments.nivisa

import edu.kit.ipe.adl.indesign.module.measurement.MeasurementDevice
import org.bridj.Pointer
import java.io.ByteArrayOutputStream

class VISADevice(val deviceString: String) extends MeasurementDevice {

  def this(d : VISADevice) = this(d.deviceString)
  
  var deviceSession: Option[Long] = None

  def getId = deviceString

  def requireOpen = deviceSession match {
    case Some(session) => 
    case None => open
  }
  
  def open = {

    //-- Prepare Session or isntrument
    var instrumentSession = Pointer.allocateCLong()
    VisaLibrary.viOpen(VISA.getResourceManagerHandler, Pointer.pointerToCString(deviceString), VisaLibrary.VI_NULL, VisaLibrary.VI_NULL, instrumentSession) match {

      case 0 =>
        println(s"Opened Device: ${deviceString}")
      case other =>
        throw new RuntimeException(s"An Error Occured while opening device ${deviceString} code=$other, message=${VISA.getStatusDesc(other)}")

    }
    this.deviceSession = Some(instrumentSession.getInt)

  }

  def close = {

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