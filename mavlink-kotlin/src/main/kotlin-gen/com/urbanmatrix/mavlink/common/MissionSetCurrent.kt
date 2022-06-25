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
import kotlin.Int

/**
 * Set the mission item with sequence number seq as current item. This means that the MAV will
 * continue to this mission item on the shortest path (not following the mission items in-between).
 */
public data class MissionSetCurrent(
  /**
   * System ID
   */
  public val targetSystem: Int = 0,
  /**
   * Component ID
   */
  public val targetComponent: Int = 0,
  /**
   * Sequence
   */
  public val seq: Int = 0,
) : MavMessage<MissionSetCurrent> {
  public override val instanceMetadata: MavMessage.Metadata<MissionSetCurrent> = METADATA

  public override fun serialize(): ByteArray {
    val outputBuffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN)
    outputBuffer.encodeUint8(targetSystem)
    outputBuffer.encodeUint8(targetComponent)
    outputBuffer.encodeUint16(seq)
    return outputBuffer.array()
  }

  public companion object {
    private const val ID: Int = 41

    private const val CRC: Int = 38

    private val DESERIALIZER: MavDeserializer<MissionSetCurrent> = MavDeserializer { bytes ->
      val inputBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
      val targetSystem = inputBuffer.decodeUint8()
      val targetComponent = inputBuffer.decodeUint8()
      val seq = inputBuffer.decodeUint16()
      MissionSetCurrent(
        targetSystem = targetSystem,
        targetComponent = targetComponent,
        seq = seq,
      )
    }


    private val METADATA: MavMessage.Metadata<MissionSetCurrent> = MavMessage.Metadata(ID, CRC,
        DESERIALIZER)

    public val classMetadata: MavMessage.Metadata<MissionSetCurrent> = METADATA
  }
}
