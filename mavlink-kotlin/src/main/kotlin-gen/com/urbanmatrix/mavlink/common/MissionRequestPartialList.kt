package com.urbanmatrix.mavlink.common

import com.urbanmatrix.mavlink.api.MavDeserializationException
import com.urbanmatrix.mavlink.api.MavDeserializer
import com.urbanmatrix.mavlink.api.MavEnumValue
import com.urbanmatrix.mavlink.api.MavMessage
import com.urbanmatrix.mavlink.serialization.decodeEnumValue
import com.urbanmatrix.mavlink.serialization.decodeInt16
import com.urbanmatrix.mavlink.serialization.decodeUint8
import com.urbanmatrix.mavlink.serialization.encodeEnumValue
import com.urbanmatrix.mavlink.serialization.encodeInt16
import com.urbanmatrix.mavlink.serialization.encodeUint8
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.ByteArray
import kotlin.Int

/**
 * Request a partial list of mission items from the system/component.
 * https://mavlink.io/en/services/mission.html. If start and end index are the same, just send one
 * waypoint.
 */
public data class MissionRequestPartialList(
  /**
   * System ID
   */
  public val targetSystem: Int = 0,
  /**
   * Component ID
   */
  public val targetComponent: Int = 0,
  /**
   * Start index
   */
  public val startIndex: Int = 0,
  /**
   * End index, -1 by default (-1: send list to end). Else a valid index of the list
   */
  public val endIndex: Int = 0,
  /**
   * Mission type.
   */
  public val missionType: MavEnumValue<MavMissionType> = MavEnumValue.fromValue(0),
) : MavMessage<MissionRequestPartialList> {
  public override val instanceMetadata: MavMessage.Metadata<MissionRequestPartialList> = METADATA

  public override fun serialize(): ByteArray {
    val outputBuffer = ByteBuffer.allocate(SIZE).order(ByteOrder.LITTLE_ENDIAN)
    outputBuffer.encodeInt16(startIndex)
    outputBuffer.encodeInt16(endIndex)
    outputBuffer.encodeUint8(targetSystem)
    outputBuffer.encodeUint8(targetComponent)
    outputBuffer.encodeEnumValue(missionType.value, 1)
    return outputBuffer.array()
  }

  public companion object {
    private const val ID: Int = 37

    private const val CRC: Int = 212

    private const val SIZE: Int = 7

    private val DESERIALIZER: MavDeserializer<MissionRequestPartialList> = MavDeserializer {
        bytes ->
      if (bytes.size != SIZE) {
        throw MavDeserializationException(
          """Invalid ByteArray size for MissionRequestPartialList: Expected=$SIZE Actual=${bytes.size}"""
        )
      }

      val inputBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
      val startIndex = inputBuffer.decodeInt16()
      val endIndex = inputBuffer.decodeInt16()
      val targetSystem = inputBuffer.decodeUint8()
      val targetComponent = inputBuffer.decodeUint8()
      val missionType = inputBuffer.decodeEnumValue(1).let { value ->
        val entry = MavMissionType.getEntryFromValueOrNull(value)
        if (entry != null) MavEnumValue.of(entry) else MavEnumValue.fromValue(value)
      }

      MissionRequestPartialList(
        targetSystem = targetSystem,
        targetComponent = targetComponent,
        startIndex = startIndex,
        endIndex = endIndex,
        missionType = missionType,
      )
    }


    private val METADATA: MavMessage.Metadata<MissionRequestPartialList> = MavMessage.Metadata(ID,
        CRC, DESERIALIZER)

    public val classMetadata: MavMessage.Metadata<MissionRequestPartialList> = METADATA
  }
}
