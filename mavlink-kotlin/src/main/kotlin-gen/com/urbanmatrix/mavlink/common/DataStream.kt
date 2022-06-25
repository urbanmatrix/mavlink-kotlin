package com.urbanmatrix.mavlink.common

import com.urbanmatrix.mavlink.api.MavDeserializer
import com.urbanmatrix.mavlink.api.MavMessage
import com.urbanmatrix.mavlink.serialization.decodeUint16
import com.urbanmatrix.mavlink.serialization.decodeUint8
import com.urbanmatrix.mavlink.serialization.encodeUint16
import com.urbanmatrix.mavlink.serialization.encodeUint8
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.ByteArray
import kotlin.Deprecated
import kotlin.Int

/**
 * Data stream status information.
 */
@Deprecated(message = "")
public data class DataStream(
  /**
   * The message rate
   */
  public val messageRate: Int = 0,
  /**
   * 1 stream is enabled, 0 stream is stopped.
   */
  public val onOff: Int = 0,
  /**
   * The ID of the requested data stream
   */
  public val streamId: Int = 0,
) : MavMessage<DataStream> {
  public override val instanceMetadata: MavMessage.Metadata<DataStream> = METADATA

  public override fun serialize(): ByteArray {
    val outputBuffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN)
    outputBuffer.encodeUint16(messageRate)
    outputBuffer.encodeUint8(onOff)
    outputBuffer.encodeUint8(streamId)
    return outputBuffer.array()
  }

  public companion object {
    private const val ID: Int = 67

    private const val CRC: Int = 193

    private val DESERIALIZER: MavDeserializer<DataStream> = MavDeserializer { bytes ->
      val inputBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
      val messageRate = inputBuffer.decodeUint16()
      val onOff = inputBuffer.decodeUint8()
      val streamId = inputBuffer.decodeUint8()
      DataStream(
        messageRate = messageRate,
        onOff = onOff,
        streamId = streamId,
      )
    }


    private val METADATA: MavMessage.Metadata<DataStream> = MavMessage.Metadata(ID, CRC,
        DESERIALIZER)

    public val classMetadata: MavMessage.Metadata<DataStream> = METADATA
  }
}