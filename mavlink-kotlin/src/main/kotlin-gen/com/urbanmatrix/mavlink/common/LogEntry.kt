package com.urbanmatrix.mavlink.common

import com.urbanmatrix.mavlink.api.MavDeserializationException
import com.urbanmatrix.mavlink.api.MavDeserializer
import com.urbanmatrix.mavlink.api.MavMessage
import com.urbanmatrix.mavlink.serialization.decodeUint16
import com.urbanmatrix.mavlink.serialization.decodeUint32
import com.urbanmatrix.mavlink.serialization.encodeUint16
import com.urbanmatrix.mavlink.serialization.encodeUint32
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.ByteArray
import kotlin.Int
import kotlin.Long

/**
 * Reply to LOG_REQUEST_LIST
 */
public data class LogEntry(
  /**
   * Log id
   */
  public val id: Int = 0,
  /**
   * Total number of logs
   */
  public val numLogs: Int = 0,
  /**
   * High log number
   */
  public val lastLogNum: Int = 0,
  /**
   * UTC timestamp of log since 1970, or 0 if not available
   */
  public val timeUtc: Long = 0L,
  /**
   * Size of the log (may be approximate)
   */
  public val size: Long = 0L,
) : MavMessage<LogEntry> {
  public override val instanceMetadata: MavMessage.Metadata<LogEntry> = METADATA

  public override fun serialize(): ByteArray {
    val outputBuffer = ByteBuffer.allocate(SIZE).order(ByteOrder.LITTLE_ENDIAN)
    outputBuffer.encodeUint32(timeUtc)
    outputBuffer.encodeUint32(size)
    outputBuffer.encodeUint16(id)
    outputBuffer.encodeUint16(numLogs)
    outputBuffer.encodeUint16(lastLogNum)
    return outputBuffer.array()
  }

  public companion object {
    private const val ID: Int = 118

    private const val CRC: Int = 56

    private const val SIZE: Int = 14

    private val DESERIALIZER: MavDeserializer<LogEntry> = MavDeserializer { bytes ->
      if (bytes.size != SIZE) {
        throw MavDeserializationException(
          """Invalid ByteArray size for LogEntry: Expected=$SIZE Actual=${bytes.size}"""
        )
      }

      val inputBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
      val timeUtc = inputBuffer.decodeUint32()
      val size = inputBuffer.decodeUint32()
      val id = inputBuffer.decodeUint16()
      val numLogs = inputBuffer.decodeUint16()
      val lastLogNum = inputBuffer.decodeUint16()

      LogEntry(
        id = id,
        numLogs = numLogs,
        lastLogNum = lastLogNum,
        timeUtc = timeUtc,
        size = size,
      )
    }


    private val METADATA: MavMessage.Metadata<LogEntry> = MavMessage.Metadata(ID, CRC, DESERIALIZER)

    public val classMetadata: MavMessage.Metadata<LogEntry> = METADATA
  }
}
