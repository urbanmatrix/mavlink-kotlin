package com.urbanmatrix.mavlink.matrixpilot

import com.urbanmatrix.mavlink.api.MavDeserializationException
import com.urbanmatrix.mavlink.api.MavDeserializer
import com.urbanmatrix.mavlink.api.MavMessage
import com.urbanmatrix.mavlink.serialization.decodeInt16
import com.urbanmatrix.mavlink.serialization.decodeInt32
import com.urbanmatrix.mavlink.serialization.decodeUint32
import com.urbanmatrix.mavlink.serialization.encodeInt16
import com.urbanmatrix.mavlink.serialization.encodeInt32
import com.urbanmatrix.mavlink.serialization.encodeUint32
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.ByteArray
import kotlin.Int
import kotlin.Long

/**
 * Backwards compatible version of SERIAL_UDB_EXTRA - F2: Part B
 */
public data class SerialUdbExtraF2B(
  /**
   * Serial UDB Extra Time
   */
  public val sueTime: Long = 0L,
  /**
   * Serial UDB Extra PWM Input Channel 1
   */
  public val suePwmInput1: Int = 0,
  /**
   * Serial UDB Extra PWM Input Channel 2
   */
  public val suePwmInput2: Int = 0,
  /**
   * Serial UDB Extra PWM Input Channel 3
   */
  public val suePwmInput3: Int = 0,
  /**
   * Serial UDB Extra PWM Input Channel 4
   */
  public val suePwmInput4: Int = 0,
  /**
   * Serial UDB Extra PWM Input Channel 5
   */
  public val suePwmInput5: Int = 0,
  /**
   * Serial UDB Extra PWM Input Channel 6
   */
  public val suePwmInput6: Int = 0,
  /**
   * Serial UDB Extra PWM Input Channel 7
   */
  public val suePwmInput7: Int = 0,
  /**
   * Serial UDB Extra PWM Input Channel 8
   */
  public val suePwmInput8: Int = 0,
  /**
   * Serial UDB Extra PWM Input Channel 9
   */
  public val suePwmInput9: Int = 0,
  /**
   * Serial UDB Extra PWM Input Channel 10
   */
  public val suePwmInput10: Int = 0,
  /**
   * Serial UDB Extra PWM Input Channel 11
   */
  public val suePwmInput11: Int = 0,
  /**
   * Serial UDB Extra PWM Input Channel 12
   */
  public val suePwmInput12: Int = 0,
  /**
   * Serial UDB Extra PWM Output Channel 1
   */
  public val suePwmOutput1: Int = 0,
  /**
   * Serial UDB Extra PWM Output Channel 2
   */
  public val suePwmOutput2: Int = 0,
  /**
   * Serial UDB Extra PWM Output Channel 3
   */
  public val suePwmOutput3: Int = 0,
  /**
   * Serial UDB Extra PWM Output Channel 4
   */
  public val suePwmOutput4: Int = 0,
  /**
   * Serial UDB Extra PWM Output Channel 5
   */
  public val suePwmOutput5: Int = 0,
  /**
   * Serial UDB Extra PWM Output Channel 6
   */
  public val suePwmOutput6: Int = 0,
  /**
   * Serial UDB Extra PWM Output Channel 7
   */
  public val suePwmOutput7: Int = 0,
  /**
   * Serial UDB Extra PWM Output Channel 8
   */
  public val suePwmOutput8: Int = 0,
  /**
   * Serial UDB Extra PWM Output Channel 9
   */
  public val suePwmOutput9: Int = 0,
  /**
   * Serial UDB Extra PWM Output Channel 10
   */
  public val suePwmOutput10: Int = 0,
  /**
   * Serial UDB Extra PWM Output Channel 11
   */
  public val suePwmOutput11: Int = 0,
  /**
   * Serial UDB Extra PWM Output Channel 12
   */
  public val suePwmOutput12: Int = 0,
  /**
   * Serial UDB Extra IMU Location X
   */
  public val sueImuLocationX: Int = 0,
  /**
   * Serial UDB Extra IMU Location Y
   */
  public val sueImuLocationY: Int = 0,
  /**
   * Serial UDB Extra IMU Location Z
   */
  public val sueImuLocationZ: Int = 0,
  /**
   * Serial UDB Location Error Earth X
   */
  public val sueLocationErrorEarthX: Int = 0,
  /**
   * Serial UDB Location Error Earth Y
   */
  public val sueLocationErrorEarthY: Int = 0,
  /**
   * Serial UDB Location Error Earth Z
   */
  public val sueLocationErrorEarthZ: Int = 0,
  /**
   * Serial UDB Extra Status Flags
   */
  public val sueFlags: Long = 0L,
  /**
   * Serial UDB Extra Oscillator Failure Count
   */
  public val sueOscFails: Int = 0,
  /**
   * Serial UDB Extra IMU Velocity X
   */
  public val sueImuVelocityX: Int = 0,
  /**
   * Serial UDB Extra IMU Velocity Y
   */
  public val sueImuVelocityY: Int = 0,
  /**
   * Serial UDB Extra IMU Velocity Z
   */
  public val sueImuVelocityZ: Int = 0,
  /**
   * Serial UDB Extra Current Waypoint Goal X
   */
  public val sueWaypointGoalX: Int = 0,
  /**
   * Serial UDB Extra Current Waypoint Goal Y
   */
  public val sueWaypointGoalY: Int = 0,
  /**
   * Serial UDB Extra Current Waypoint Goal Z
   */
  public val sueWaypointGoalZ: Int = 0,
  /**
   * Aeroforce in UDB X Axis
   */
  public val sueAeroX: Int = 0,
  /**
   * Aeroforce in UDB Y Axis
   */
  public val sueAeroY: Int = 0,
  /**
   * Aeroforce in UDB Z axis
   */
  public val sueAeroZ: Int = 0,
  /**
   * SUE barometer temperature
   */
  public val sueBaromTemp: Int = 0,
  /**
   * SUE barometer pressure
   */
  public val sueBaromPress: Int = 0,
  /**
   * SUE barometer altitude
   */
  public val sueBaromAlt: Int = 0,
  /**
   * SUE battery voltage
   */
  public val sueBatVolt: Int = 0,
  /**
   * SUE battery current
   */
  public val sueBatAmp: Int = 0,
  /**
   * SUE battery milli amp hours used
   */
  public val sueBatAmpHours: Int = 0,
  /**
   * Sue autopilot desired height
   */
  public val sueDesiredHeight: Int = 0,
  /**
   * Serial UDB Extra Stack Memory Free
   */
  public val sueMemoryStackFree: Int = 0,
) : MavMessage<SerialUdbExtraF2B> {
  public override val instanceMetadata: MavMessage.Metadata<SerialUdbExtraF2B> = METADATA

  public override fun serialize(): ByteArray {
    val outputBuffer = ByteBuffer.allocate(SIZE).order(ByteOrder.LITTLE_ENDIAN)
    outputBuffer.encodeUint32(sueTime)
    outputBuffer.encodeUint32(sueFlags)
    outputBuffer.encodeInt32(sueBaromPress)
    outputBuffer.encodeInt32(sueBaromAlt)
    outputBuffer.encodeInt16(suePwmInput1)
    outputBuffer.encodeInt16(suePwmInput2)
    outputBuffer.encodeInt16(suePwmInput3)
    outputBuffer.encodeInt16(suePwmInput4)
    outputBuffer.encodeInt16(suePwmInput5)
    outputBuffer.encodeInt16(suePwmInput6)
    outputBuffer.encodeInt16(suePwmInput7)
    outputBuffer.encodeInt16(suePwmInput8)
    outputBuffer.encodeInt16(suePwmInput9)
    outputBuffer.encodeInt16(suePwmInput10)
    outputBuffer.encodeInt16(suePwmInput11)
    outputBuffer.encodeInt16(suePwmInput12)
    outputBuffer.encodeInt16(suePwmOutput1)
    outputBuffer.encodeInt16(suePwmOutput2)
    outputBuffer.encodeInt16(suePwmOutput3)
    outputBuffer.encodeInt16(suePwmOutput4)
    outputBuffer.encodeInt16(suePwmOutput5)
    outputBuffer.encodeInt16(suePwmOutput6)
    outputBuffer.encodeInt16(suePwmOutput7)
    outputBuffer.encodeInt16(suePwmOutput8)
    outputBuffer.encodeInt16(suePwmOutput9)
    outputBuffer.encodeInt16(suePwmOutput10)
    outputBuffer.encodeInt16(suePwmOutput11)
    outputBuffer.encodeInt16(suePwmOutput12)
    outputBuffer.encodeInt16(sueImuLocationX)
    outputBuffer.encodeInt16(sueImuLocationY)
    outputBuffer.encodeInt16(sueImuLocationZ)
    outputBuffer.encodeInt16(sueLocationErrorEarthX)
    outputBuffer.encodeInt16(sueLocationErrorEarthY)
    outputBuffer.encodeInt16(sueLocationErrorEarthZ)
    outputBuffer.encodeInt16(sueOscFails)
    outputBuffer.encodeInt16(sueImuVelocityX)
    outputBuffer.encodeInt16(sueImuVelocityY)
    outputBuffer.encodeInt16(sueImuVelocityZ)
    outputBuffer.encodeInt16(sueWaypointGoalX)
    outputBuffer.encodeInt16(sueWaypointGoalY)
    outputBuffer.encodeInt16(sueWaypointGoalZ)
    outputBuffer.encodeInt16(sueAeroX)
    outputBuffer.encodeInt16(sueAeroY)
    outputBuffer.encodeInt16(sueAeroZ)
    outputBuffer.encodeInt16(sueBaromTemp)
    outputBuffer.encodeInt16(sueBatVolt)
    outputBuffer.encodeInt16(sueBatAmp)
    outputBuffer.encodeInt16(sueBatAmpHours)
    outputBuffer.encodeInt16(sueDesiredHeight)
    outputBuffer.encodeInt16(sueMemoryStackFree)
    return outputBuffer.array()
  }

  public companion object {
    private const val ID: Int = 171

    private const val CRC: Int = 245

    private const val SIZE: Int = 108

    private val DESERIALIZER: MavDeserializer<SerialUdbExtraF2B> = MavDeserializer { bytes ->
      if (bytes.size != SIZE) {
        throw MavDeserializationException(
          """Invalid ByteArray size for SerialUdbExtraF2B: Expected=$SIZE Actual=${bytes.size}"""
        )
      }

      val inputBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
      val sueTime = inputBuffer.decodeUint32()
      val sueFlags = inputBuffer.decodeUint32()
      val sueBaromPress = inputBuffer.decodeInt32()
      val sueBaromAlt = inputBuffer.decodeInt32()
      val suePwmInput1 = inputBuffer.decodeInt16()
      val suePwmInput2 = inputBuffer.decodeInt16()
      val suePwmInput3 = inputBuffer.decodeInt16()
      val suePwmInput4 = inputBuffer.decodeInt16()
      val suePwmInput5 = inputBuffer.decodeInt16()
      val suePwmInput6 = inputBuffer.decodeInt16()
      val suePwmInput7 = inputBuffer.decodeInt16()
      val suePwmInput8 = inputBuffer.decodeInt16()
      val suePwmInput9 = inputBuffer.decodeInt16()
      val suePwmInput10 = inputBuffer.decodeInt16()
      val suePwmInput11 = inputBuffer.decodeInt16()
      val suePwmInput12 = inputBuffer.decodeInt16()
      val suePwmOutput1 = inputBuffer.decodeInt16()
      val suePwmOutput2 = inputBuffer.decodeInt16()
      val suePwmOutput3 = inputBuffer.decodeInt16()
      val suePwmOutput4 = inputBuffer.decodeInt16()
      val suePwmOutput5 = inputBuffer.decodeInt16()
      val suePwmOutput6 = inputBuffer.decodeInt16()
      val suePwmOutput7 = inputBuffer.decodeInt16()
      val suePwmOutput8 = inputBuffer.decodeInt16()
      val suePwmOutput9 = inputBuffer.decodeInt16()
      val suePwmOutput10 = inputBuffer.decodeInt16()
      val suePwmOutput11 = inputBuffer.decodeInt16()
      val suePwmOutput12 = inputBuffer.decodeInt16()
      val sueImuLocationX = inputBuffer.decodeInt16()
      val sueImuLocationY = inputBuffer.decodeInt16()
      val sueImuLocationZ = inputBuffer.decodeInt16()
      val sueLocationErrorEarthX = inputBuffer.decodeInt16()
      val sueLocationErrorEarthY = inputBuffer.decodeInt16()
      val sueLocationErrorEarthZ = inputBuffer.decodeInt16()
      val sueOscFails = inputBuffer.decodeInt16()
      val sueImuVelocityX = inputBuffer.decodeInt16()
      val sueImuVelocityY = inputBuffer.decodeInt16()
      val sueImuVelocityZ = inputBuffer.decodeInt16()
      val sueWaypointGoalX = inputBuffer.decodeInt16()
      val sueWaypointGoalY = inputBuffer.decodeInt16()
      val sueWaypointGoalZ = inputBuffer.decodeInt16()
      val sueAeroX = inputBuffer.decodeInt16()
      val sueAeroY = inputBuffer.decodeInt16()
      val sueAeroZ = inputBuffer.decodeInt16()
      val sueBaromTemp = inputBuffer.decodeInt16()
      val sueBatVolt = inputBuffer.decodeInt16()
      val sueBatAmp = inputBuffer.decodeInt16()
      val sueBatAmpHours = inputBuffer.decodeInt16()
      val sueDesiredHeight = inputBuffer.decodeInt16()
      val sueMemoryStackFree = inputBuffer.decodeInt16()

      SerialUdbExtraF2B(
        sueTime = sueTime,
        suePwmInput1 = suePwmInput1,
        suePwmInput2 = suePwmInput2,
        suePwmInput3 = suePwmInput3,
        suePwmInput4 = suePwmInput4,
        suePwmInput5 = suePwmInput5,
        suePwmInput6 = suePwmInput6,
        suePwmInput7 = suePwmInput7,
        suePwmInput8 = suePwmInput8,
        suePwmInput9 = suePwmInput9,
        suePwmInput10 = suePwmInput10,
        suePwmInput11 = suePwmInput11,
        suePwmInput12 = suePwmInput12,
        suePwmOutput1 = suePwmOutput1,
        suePwmOutput2 = suePwmOutput2,
        suePwmOutput3 = suePwmOutput3,
        suePwmOutput4 = suePwmOutput4,
        suePwmOutput5 = suePwmOutput5,
        suePwmOutput6 = suePwmOutput6,
        suePwmOutput7 = suePwmOutput7,
        suePwmOutput8 = suePwmOutput8,
        suePwmOutput9 = suePwmOutput9,
        suePwmOutput10 = suePwmOutput10,
        suePwmOutput11 = suePwmOutput11,
        suePwmOutput12 = suePwmOutput12,
        sueImuLocationX = sueImuLocationX,
        sueImuLocationY = sueImuLocationY,
        sueImuLocationZ = sueImuLocationZ,
        sueLocationErrorEarthX = sueLocationErrorEarthX,
        sueLocationErrorEarthY = sueLocationErrorEarthY,
        sueLocationErrorEarthZ = sueLocationErrorEarthZ,
        sueFlags = sueFlags,
        sueOscFails = sueOscFails,
        sueImuVelocityX = sueImuVelocityX,
        sueImuVelocityY = sueImuVelocityY,
        sueImuVelocityZ = sueImuVelocityZ,
        sueWaypointGoalX = sueWaypointGoalX,
        sueWaypointGoalY = sueWaypointGoalY,
        sueWaypointGoalZ = sueWaypointGoalZ,
        sueAeroX = sueAeroX,
        sueAeroY = sueAeroY,
        sueAeroZ = sueAeroZ,
        sueBaromTemp = sueBaromTemp,
        sueBaromPress = sueBaromPress,
        sueBaromAlt = sueBaromAlt,
        sueBatVolt = sueBatVolt,
        sueBatAmp = sueBatAmp,
        sueBatAmpHours = sueBatAmpHours,
        sueDesiredHeight = sueDesiredHeight,
        sueMemoryStackFree = sueMemoryStackFree,
      )
    }


    private val METADATA: MavMessage.Metadata<SerialUdbExtraF2B> = MavMessage.Metadata(ID, CRC,
        DESERIALIZER)

    public val classMetadata: MavMessage.Metadata<SerialUdbExtraF2B> = METADATA
  }
}
