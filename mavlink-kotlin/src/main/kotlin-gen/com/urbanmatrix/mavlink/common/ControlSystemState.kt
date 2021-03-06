package com.urbanmatrix.mavlink.common

import com.urbanmatrix.mavlink.api.MavDeserializationException
import com.urbanmatrix.mavlink.api.MavDeserializer
import com.urbanmatrix.mavlink.api.MavMessage
import com.urbanmatrix.mavlink.serialization.decodeFloat
import com.urbanmatrix.mavlink.serialization.decodeFloatArray
import com.urbanmatrix.mavlink.serialization.decodeUint64
import com.urbanmatrix.mavlink.serialization.encodeFloat
import com.urbanmatrix.mavlink.serialization.encodeFloatArray
import com.urbanmatrix.mavlink.serialization.encodeUint64
import java.math.BigInteger
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.ByteArray
import kotlin.Float
import kotlin.Int
import kotlin.collections.List

/**
 * The smoothed, monotonic system state used to feed the control loops of the system.
 */
public data class ControlSystemState(
  /**
   * Timestamp (UNIX Epoch time or time since system boot). The receiving end can infer timestamp
   * format (since 1.1.1970 or since system boot) by checking for the magnitude of the number.
   */
  public val timeUsec: BigInteger = BigInteger.ZERO,
  /**
   * X acceleration in body frame
   */
  public val xAcc: Float = 0F,
  /**
   * Y acceleration in body frame
   */
  public val yAcc: Float = 0F,
  /**
   * Z acceleration in body frame
   */
  public val zAcc: Float = 0F,
  /**
   * X velocity in body frame
   */
  public val xVel: Float = 0F,
  /**
   * Y velocity in body frame
   */
  public val yVel: Float = 0F,
  /**
   * Z velocity in body frame
   */
  public val zVel: Float = 0F,
  /**
   * X position in local frame
   */
  public val xPos: Float = 0F,
  /**
   * Y position in local frame
   */
  public val yPos: Float = 0F,
  /**
   * Z position in local frame
   */
  public val zPos: Float = 0F,
  /**
   * Airspeed, set to -1 if unknown
   */
  public val airspeed: Float = 0F,
  /**
   * Variance of body velocity estimate
   */
  public val velVariance: List<Float> = emptyList(),
  /**
   * Variance in local position
   */
  public val posVariance: List<Float> = emptyList(),
  /**
   * The attitude, represented as Quaternion
   */
  public val q: List<Float> = emptyList(),
  /**
   * Angular rate in roll axis
   */
  public val rollRate: Float = 0F,
  /**
   * Angular rate in pitch axis
   */
  public val pitchRate: Float = 0F,
  /**
   * Angular rate in yaw axis
   */
  public val yawRate: Float = 0F,
) : MavMessage<ControlSystemState> {
  public override val instanceMetadata: MavMessage.Metadata<ControlSystemState> = METADATA

  public override fun serialize(): ByteArray {
    val outputBuffer = ByteBuffer.allocate(SIZE).order(ByteOrder.LITTLE_ENDIAN)
    outputBuffer.encodeUint64(timeUsec)
    outputBuffer.encodeFloat(xAcc)
    outputBuffer.encodeFloat(yAcc)
    outputBuffer.encodeFloat(zAcc)
    outputBuffer.encodeFloat(xVel)
    outputBuffer.encodeFloat(yVel)
    outputBuffer.encodeFloat(zVel)
    outputBuffer.encodeFloat(xPos)
    outputBuffer.encodeFloat(yPos)
    outputBuffer.encodeFloat(zPos)
    outputBuffer.encodeFloat(airspeed)
    outputBuffer.encodeFloatArray(velVariance, 12)
    outputBuffer.encodeFloatArray(posVariance, 12)
    outputBuffer.encodeFloatArray(q, 16)
    outputBuffer.encodeFloat(rollRate)
    outputBuffer.encodeFloat(pitchRate)
    outputBuffer.encodeFloat(yawRate)
    return outputBuffer.array()
  }

  public companion object {
    private const val ID: Int = 146

    private const val CRC: Int = 187

    private const val SIZE: Int = 100

    private val DESERIALIZER: MavDeserializer<ControlSystemState> = MavDeserializer { bytes ->
      if (bytes.size != SIZE) {
        throw MavDeserializationException(
          """Invalid ByteArray size for ControlSystemState: Expected=$SIZE Actual=${bytes.size}"""
        )
      }

      val inputBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
      val timeUsec = inputBuffer.decodeUint64()
      val xAcc = inputBuffer.decodeFloat()
      val yAcc = inputBuffer.decodeFloat()
      val zAcc = inputBuffer.decodeFloat()
      val xVel = inputBuffer.decodeFloat()
      val yVel = inputBuffer.decodeFloat()
      val zVel = inputBuffer.decodeFloat()
      val xPos = inputBuffer.decodeFloat()
      val yPos = inputBuffer.decodeFloat()
      val zPos = inputBuffer.decodeFloat()
      val airspeed = inputBuffer.decodeFloat()
      val velVariance = inputBuffer.decodeFloatArray(12)
      val posVariance = inputBuffer.decodeFloatArray(12)
      val q = inputBuffer.decodeFloatArray(16)
      val rollRate = inputBuffer.decodeFloat()
      val pitchRate = inputBuffer.decodeFloat()
      val yawRate = inputBuffer.decodeFloat()

      ControlSystemState(
        timeUsec = timeUsec,
        xAcc = xAcc,
        yAcc = yAcc,
        zAcc = zAcc,
        xVel = xVel,
        yVel = yVel,
        zVel = zVel,
        xPos = xPos,
        yPos = yPos,
        zPos = zPos,
        airspeed = airspeed,
        velVariance = velVariance,
        posVariance = posVariance,
        q = q,
        rollRate = rollRate,
        pitchRate = pitchRate,
        yawRate = yawRate,
      )
    }


    private val METADATA: MavMessage.Metadata<ControlSystemState> = MavMessage.Metadata(ID, CRC,
        DESERIALIZER)

    public val classMetadata: MavMessage.Metadata<ControlSystemState> = METADATA
  }
}
