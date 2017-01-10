package kit.ipe.adl.instruments.ieee

class IEEE4882BinaryBlock(var array : Option[Array[Byte]]) {
  
  //-- Check validity
  array match {
    case Some(data) =>
      
      if(data.length==0 || data(0).toChar != '#') {
        throw new RuntimeException("Cannot init IEEE4882BinaryBlock from data, must not be empty and start with # ")
      }
      
   
    case None => 
  }
  
  def getArray = array match {
    case Some(data) => data
    case None => throw new RuntimeException("Cannot work on non defined array")
  }
  
  /**
   * First char is #
   */
  def getDataLength = {
    
    var lengthDefinitionCharCount = this.array.get.drop(1)(0).toInt
    var length = this.getArray.drop(2).take(lengthDefinitionCharCount).mkString.toInt
    length
  }
  
  def getData = {
    this.getArray.takeRight(this.getDataLength)
  }
  
  
}