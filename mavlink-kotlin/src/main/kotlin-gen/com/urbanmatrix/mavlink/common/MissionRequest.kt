package com.urbanmatrix.mavlink.common

import com.urbanmatrix.mavlink.api.MavDeserializationException
import com.urbanmatrix.mavlink.api.MavDeserializer
import com.urbanmatrix.mavlink.api.MavEnumValue
import com.urbanmatrix.mavlink.api.MavMessage
import com.urbanmatrix.mavlink.serialization.decodeEnumValue
import com.urbanmatrix.mavlink.serialization.decodeUint16
import com.urbanmatrix.mavlink.serialization.decodeUint8
import com.urbanmatrix.mavlink.serialization.encodeEnumValue
import com.urbanmatrix.mavlink.serialization.encodeUint16
import com.urbanmatrix.mavlink.serialization.encodeUint8
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.ByteArray
import kotlin.Deprecated
import kotlin.Int

/**
 * Request the information of the mission item with the sequence number seq. The response of the
 * system to this message should be a MISSION_ITEM message. https://mavlink.io/en/services/mission.html
 */
@Deprecated(message =
    "A system that gets this request should respond with MISSION_ITEM_INT (as though MISSION_REQUEST_INT was received).")
public data class MissionRequest(
  /**
   * Mission type.
   */
  public val missionType: MavEnumValue<MavMissionType> = MavEnumValue.fromValue(0),
  /**
   * Sequence
   */
  public val seq: Int = 0,
  /**
   * Component ID
   */
  public val targetComponent: Int = 0,
  /**
   * System ID
   */
  public val targetSystem: Int = 0,
) : MavMessage<MissionRequest> {
  public override val instanceMetadata: MavMessage.Metadata<MissionRequest> = METADATA

  public override fun serialize(): ByteArray {
    val outputBuffer = ByteBuffer.allocate(SIZE).order(ByteOrder.LITTLE_ENDIAN)
    outputBuffer.encodeEnumValue(missionType.value, 1)
    outputBuffer.encodeUint16(seq)
    outputBuffer.encodeUint8(targetComponent)
    outputBuffer.encodeUint8(targetSystem)
    return outputBuffer.array()
  }

  public companion object {
    private const val ID: Int = 40

    private const val CRC: Int = 63

    private const val SIZE: Int = 5

    private val DESERIALIZER: MavDeserializer<MissionRequest> = MavDeserializer { bytes ->
      if (bytes.size != SIZE) {
        throw MavDeserializationException(
          """Invalid ByteArray size for MissionRequest: Expected=$SIZE Actual=${bytes.size}"""
        )
      }

      val inputBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
      val missionType = inputBuffer.decodeEnumValue(1).let { value ->
        val entry = MavMissionType.getEntryFromValueOrNull(value)
        if (entry != null) MavEnumValue.of(entry) else MavEnumValue.fromValue(value)
      }
      val seq = inputBuffer.decodeUint16()
      val targetComponent = inputBuffer.decodeUint8()
      val targetSystem = inputBuffer.decodeUint8()

      MissionRequest(
        missionType = missionType,
        seq = seq,
        targetComponent = targetComponent,
        targetSystem = targetSystem,
      )
    }


    private val METADATA: MavMessage.Metadata<MissionRequest> = MavMessage.Metadata(ID, CRC,
        DESERIALIZER)

    public val classMetadata: MavMessage.Metadata<MissionRequest> = METADATA
  }
}
