package org.odfi.instruments.compress

import org.apache.commons.compress.archivers.ArchiveEntry

class MemoryArchiveEntry(val archiveEntry:ArchiveEntry,val bytes : Array[Byte]) {
  
  def getName = archiveEntry.getName
  
}