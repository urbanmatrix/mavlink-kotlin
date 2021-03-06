package com.urbanmatrix.mavlink.common

import com.urbanmatrix.mavlink.api.MavDeserializationException
import com.urbanmatrix.mavlink.api.MavDeserializer
import com.urbanmatrix.mavlink.api.MavEnumValue
import com.urbanmatrix.mavlink.api.MavMessage
import com.urbanmatrix.mavlink.serialization.decodeEnumValue
import com.urbanmatrix.mavlink.serialization.decodeFloat
import com.urbanmatrix.mavlink.serialization.decodeFloatArray
import com.urbanmatrix.mavlink.serialization.decodeUint64
import com.urbanmatrix.mavlink.serialization.decodeUint8
import com.urbanmatrix.mavlink.serialization.encodeEnumValue
import com.urbanmatrix.mavlink.serialization.encodeFloat
import com.urbanmatrix.mavlink.serialization.encodeFloatArray
import com.urbanmatrix.mavlink.serialization.encodeUint64
import com.urbanmatrix.mavlink.serialization.encodeUint8
import java.math.BigInteger
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.ByteArray
import kotlin.Float
import kotlin.Int
import kotlin.collections.List

/**
 * Odometry message to communicate odometry information with an external interface. Fits ROS REP 147
 * standard for aerial vehicles (http://www.ros.org/reps/rep-0147.html).
 */
public data class Odometry(
  /**
   * Timestamp (UNIX Epoch time or time since system boot). The receiving end can infer timestamp
   * format (since 1.1.1970 or since system boot) by checking for the magnitude of the number.
   */
  public val timeUsec: BigInteger = BigInteger.ZERO,
  /**
   * Coordinate frame of reference for the pose data.
   */
  public val frameId: MavEnumValue<MavFrame> = MavEnumValue.fromValue(0),
  /**
   * Coordinate frame of reference for the velocity in free space (twist) data.
   */
  public val childFrameId: MavEnumValue<MavFrame> = MavEnumValue.fromValue(0),
  /**
   * X Position
   */
  public val x: Float = 0F,
  /**
   * Y Position
   */
  public val y: Float = 0F,
  /**
   * Z Position
   */
  public val z: Float = 0F,
  /**
   * Quaternion components, w, x, y, z (1 0 0 0 is the null-rotation)
   */
  public val q: List<Float> = emptyList(),
  /**
   * X linear speed
   */
  public val vx: Float = 0F,
  /**
   * Y linear speed
   */
  public val vy: Float = 0F,
  /**
   * Z linear speed
   */
  public val vz: Float = 0F,
  /**
   * Roll angular speed
   */
  public val rollspeed: Float = 0F,
  /**
   * Pitch angular speed
   */
  public val pitchspeed: Float = 0F,
  /**
   * Yaw angular speed
   */
  public val yawspeed: Float = 0F,
  /**
   * Row-major representation of a 6x6 pose cross-covariance matrix upper right triangle (states: x,
   * y, z, roll, pitch, yaw; first six entries are the first ROW, next five entries are the second ROW,
   * etc.). If unknown, assign NaN value to first element in the array.
   */
  public val poseCovariance: List<Float> = emptyList(),
  /**
   * Row-major representation of a 6x6 velocity cross-covariance matrix upper right triangle
   * (states: vx, vy, vz, rollspeed, pitchspeed, yawspeed; first six entries are the first ROW, next
   * five entries are the second ROW, etc.). If unknown, assign NaN value to first element in the
   * array.
   */
  public val velocityCovariance: List<Float> = emptyList(),
  /**
   * Estimate reset counter. This should be incremented when the estimate resets in any of the
   * dimensions (position, velocity, attitude, angular speed). This is designed to be used when e.g an
   * external SLAM system detects a loop-closure and the estimate jumps.
   */
  public val resetCounter: Int = 0,
  /**
   * Type of estimator that is providing the odometry.
   */
  public val estimatorType: MavEnumValue<MavEstimatorType> = MavEnumValue.fromValue(0),
) : MavMessage<Odometry> {
  public override val instanceMetadata: MavMessage.Metadata<Odometry> = METADATA

  public override fun serialize(): ByteArray {
    val outputBuffer = ByteBuffer.allocate(SIZE).order(ByteOrder.LITTLE_ENDIAN)
    outputBuffer.encodeUint64(timeUsec)
    outputBuffer.encodeFloat(x)
    outputBuffer.encodeFloat(y)
    outputBuffer.encodeFloat(z)
    outputBuffer.encodeFloatArray(q, 16)
    outputBuffer.encodeFloat(vx)
    outputBuffer.encodeFloat(vy)
    outputBuffer.encodeFloat(vz)
    outputBuffer.encodeFloat(rollspeed)
    outputBuffer.encodeFloat(pitchspeed)
    outputBuffer.encodeFloat(yawspeed)
    outputBuffer.encodeFloatArray(poseCovariance, 84)
    outputBuffer.encodeFloatArray(velocityCovariance, 84)
    outputBuffer.encodeEnumValue(frameId.value, 1)
    outputBuffer.encodeEnumValue(childFrameId.value, 1)
    outputBuffer.encodeUint8(resetCounter)
    outputBuffer.encodeEnumValue(estimatorType.value, 1)
    return outputBuffer.array()
  }

  public companion object {
    private const val ID: Int = 331

    private const val CRC: Int = 129

    private const val SIZE: Int = 232

    private val DESERIALIZER: MavDeserializer<Odometry> = MavDeserializer { bytes ->
      if (bytes.size != SIZE) {
        throw MavDeserializationException(
          """Invalid ByteArray size for Odometry: Expected=$SIZE Actual=${bytes.size}"""
        )
      }

      val inputBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
      val timeUsec = inputBuffer.decodeUint64()
      val x = inputBuffer.decodeFloat()
      val y = inputBuffer.decodeFloat()
      val z = inputBuffer.decodeFloat()
      val q = inputBuffer.decodeFloatArray(16)
      val vx = inputBuffer.decodeFloat()
      val vy = inputBuffer.decodeFloat()
      val vz = inputBuffer.decodeFloat()
      val rollspeed = inputBuffer.decodeFloat()
      val pitchspeed = inputBuffer.decodeFloat()
      val yawspeed = inputBuffer.decodeFloat()
      val poseCovariance = inputBuffer.decodeFloatArray(84)
      val velocityCovariance = inputBuffer.decodeFloatArray(84)
      val frameId = inputBuffer.decodeEnumValue(1).let { value ->
        val entry = MavFrame.getEntryFromValueOrNull(value)
        if (entry != null) MavEnumValue.of(entry) else MavEnumValue.fromValue(value)
      }
      val childFrameId = inputBuffer.decodeEnumValue(1).let { value ->
        val entry = MavFrame.getEntryFromValueOrNull(value)
        if (entry != null) MavEnumValue.of(entry) else MavEnumValue.fromValue(value)
      }
      val resetCounter = inputBuffer.decodeUint8()
      val estimatorType = inputBuffer.decodeEnumValue(1).let { value ->
        val entry = MavEstimatorType.getEntryFromValueOrNull(value)
        if (entry != null) MavEnumValue.of(entry) else MavEnumValue.fromValue(value)
      }

      Odometry(
        timeUsec = timeUsec,
        frameId = frameId,
        childFrameId = childFrameId,
        x = x,
        y = y,
        z = z,
        q = q,
        vx = vx,
        vy = vy,
        vz = vz,
        rollspeed = rollspeed,
        pitchspeed = pitchspeed,
        yawspeed = yawspeed,
        poseCovariance = poseCovariance,
        velocityCovariance = velocityCovariance,
        resetCounter = resetCounter,
        estimatorType = estimatorType,
      )
    }


    private val METADATA: MavMessage.Metadata<Odometry> = MavMessage.Metadata(ID, CRC, DESERIALIZER)

    public val classMetadata: MavMessage.Metadata<Odometry> = METADATA
  }
}
