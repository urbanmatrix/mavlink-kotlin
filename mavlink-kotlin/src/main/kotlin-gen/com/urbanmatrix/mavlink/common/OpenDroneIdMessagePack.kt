package com.urbanmatrix.mavlink.common

import com.urbanmatrix.mavlink.api.MavDeserializationException
import com.urbanmatrix.mavlink.api.MavDeserializer
import com.urbanmatrix.mavlink.api.MavMessage
import com.urbanmatrix.mavlink.serialization.decodeUint8
import com.urbanmatrix.mavlink.serialization.decodeUint8Array
import com.urbanmatrix.mavlink.serialization.encodeUint8
import com.urbanmatrix.mavlink.serialization.encodeUint8Array
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.ByteArray
import kotlin.Int
import kotlin.collections.List

/**
 * An OpenDroneID message pack is a container for multiple encoded OpenDroneID messages (i.e. not in
 * the format given for the above message descriptions but after encoding into the compressed
 * OpenDroneID byte format). Used e.g. when transmitting on Bluetooth 5.0 Long Range/Extended
 * Advertising or on WiFi Neighbor Aware Networking or on WiFi Beacon.
 */
public data class OpenDroneIdMessagePack(
  /**
   * System ID (0 for broadcast).
   */
  public val targetSystem: Int = 0,
  /**
   * Component ID (0 for broadcast).
   */
  public val targetComponent: Int = 0,
  /**
   * Only used for drone ID data received from other UAs. See detailed description at
   * https://mavlink.io/en/services/opendroneid.html. 
   */
  public val idOrMac: List<Int> = emptyList(),
  /**
   * This field must currently always be equal to 25 (bytes), since all encoded OpenDroneID messages
   * are specificed to have this length.
   */
  public val singleMessageSize: Int = 0,
  /**
   * Number of encoded messages in the pack (not the number of bytes). Allowed range is 1 - 9.
   */
  public val msgPackSize: Int = 0,
  /**
   * Concatenation of encoded OpenDroneID messages. Shall be filled with nulls in the unused portion
   * of the field.
   */
  public val messages: List<Int> = emptyList(),
) : MavMessage<OpenDroneIdMessagePack> {
  public override val instanceMetadata: MavMessage.Metadata<OpenDroneIdMessagePack> = METADATA

  public override fun serialize(): ByteArray {
    val outputBuffer = ByteBuffer.allocate(SIZE).order(ByteOrder.LITTLE_ENDIAN)
    outputBuffer.encodeUint8(targetSystem)
    outputBuffer.encodeUint8(targetComponent)
    outputBuffer.encodeUint8Array(idOrMac, 20)
    outputBuffer.encodeUint8(singleMessageSize)
    outputBuffer.encodeUint8(msgPackSize)
    outputBuffer.encodeUint8Array(messages, 225)
    return outputBuffer.array()
  }

  public companion object {
    private const val ID: Int = 12915

    private const val CRC: Int = 184

    private const val SIZE: Int = 249

    private val DESERIALIZER: MavDeserializer<OpenDroneIdMessagePack> = MavDeserializer { bytes ->
      if (bytes.size != SIZE) {
        throw MavDeserializationException(
          """Invalid ByteArray size for OpenDroneIdMessagePack: Expected=$SIZE Actual=${bytes.size}"""
        )
      }

      val inputBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
      val targetSystem = inputBuffer.decodeUint8()
      val targetComponent = inputBuffer.decodeUint8()
      val idOrMac = inputBuffer.decodeUint8Array(20)
      val singleMessageSize = inputBuffer.decodeUint8()
      val msgPackSize = inputBuffer.decodeUint8()
      val messages = inputBuffer.decodeUint8Array(225)

      OpenDroneIdMessagePack(
        targetSystem = targetSystem,
        targetComponent = targetComponent,
        idOrMac = idOrMac,
        singleMessageSize = singleMessageSize,
        msgPackSize = msgPackSize,
        messages = messages,
      )
    }


    private val METADATA: MavMessage.Metadata<OpenDroneIdMessagePack> = MavMessage.Metadata(ID, CRC,
        DESERIALIZER)

    public val classMetadata: MavMessage.Metadata<OpenDroneIdMessagePack> = METADATA
  }
}
