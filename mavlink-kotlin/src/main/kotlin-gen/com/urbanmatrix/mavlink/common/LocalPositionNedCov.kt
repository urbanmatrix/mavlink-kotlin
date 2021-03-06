package com.urbanmatrix.mavlink.common

import com.urbanmatrix.mavlink.api.MavDeserializationException
import com.urbanmatrix.mavlink.api.MavDeserializer
import com.urbanmatrix.mavlink.api.MavEnumValue
import com.urbanmatrix.mavlink.api.MavMessage
import com.urbanmatrix.mavlink.serialization.decodeEnumValue
import com.urbanmatrix.mavlink.serialization.decodeFloat
import com.urbanmatrix.mavlink.serialization.decodeFloatArray
import com.urbanmatrix.mavlink.serialization.decodeUint64
import com.urbanmatrix.mavlink.serialization.encodeEnumValue
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
 * The filtered local position (e.g. fused computer vision and accelerometers). Coordinate frame is
 * right-handed, Z-axis down (aeronautical frame, NED / north-east-down convention)
 */
public data class LocalPositionNedCov(
  /**
   * Timestamp (UNIX Epoch time or time since system boot). The receiving end can infer timestamp
   * format (since 1.1.1970 or since system boot) by checking for the magnitude of the number.
   */
  public val timeUsec: BigInteger = BigInteger.ZERO,
  /**
   * Class id of the estimator this estimate originated from.
   */
  public val estimatorType: MavEnumValue<MavEstimatorType> = MavEnumValue.fromValue(0),
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
   * X Speed
   */
  public val vx: Float = 0F,
  /**
   * Y Speed
   */
  public val vy: Float = 0F,
  /**
   * Z Speed
   */
  public val vz: Float = 0F,
  /**
   * X Acceleration
   */
  public val ax: Float = 0F,
  /**
   * Y Acceleration
   */
  public val ay: Float = 0F,
  /**
   * Z Acceleration
   */
  public val az: Float = 0F,
  /**
   * Row-major representation of position, velocity and acceleration 9x9 cross-covariance matrix
   * upper right triangle (states: x, y, z, vx, vy, vz, ax, ay, az; first nine entries are the first
   * ROW, next eight entries are the second row, etc.). If unknown, assign NaN value to first element
   * in the array.
   */
  public val covariance: List<Float> = emptyList(),
) : MavMessage<LocalPositionNedCov> {
  public override val instanceMetadata: MavMessage.Metadata<LocalPositionNedCov> = METADATA

  public override fun serialize(): ByteArray {
    val outputBuffer = ByteBuffer.allocate(SIZE).order(ByteOrder.LITTLE_ENDIAN)
    outputBuffer.encodeUint64(timeUsec)
    outputBuffer.encodeFloat(x)
    outputBuffer.encodeFloat(y)
    outputBuffer.encodeFloat(z)
    outputBuffer.encodeFloat(vx)
    outputBuffer.encodeFloat(vy)
    outputBuffer.encodeFloat(vz)
    outputBuffer.encodeFloat(ax)
    outputBuffer.encodeFloat(ay)
    outputBuffer.encodeFloat(az)
    outputBuffer.encodeFloatArray(covariance, 180)
    outputBuffer.encodeEnumValue(estimatorType.value, 1)
    return outputBuffer.array()
  }

  public companion object {
    private const val ID: Int = 64

    private const val CRC: Int = 153

    private const val SIZE: Int = 225

    private val DESERIALIZER: MavDeserializer<LocalPositionNedCov> = MavDeserializer { bytes ->
      if (bytes.size != SIZE) {
        throw MavDeserializationException(
          """Invalid ByteArray size for LocalPositionNedCov: Expected=$SIZE Actual=${bytes.size}"""
        )
      }

      val inputBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
      val timeUsec = inputBuffer.decodeUint64()
      val x = inputBuffer.decodeFloat()
      val y = inputBuffer.decodeFloat()
      val z = inputBuffer.decodeFloat()
      val vx = inputBuffer.decodeFloat()
      val vy = inputBuffer.decodeFloat()
      val vz = inputBuffer.decodeFloat()
      val ax = inputBuffer.decodeFloat()
      val ay = inputBuffer.decodeFloat()
      val az = inputBuffer.decodeFloat()
      val covariance = inputBuffer.decodeFloatArray(180)
      val estimatorType = inputBuffer.decodeEnumValue(1).let { value ->
        val entry = MavEstimatorType.getEntryFromValueOrNull(value)
        if (entry != null) MavEnumValue.of(entry) else MavEnumValue.fromValue(value)
      }

      LocalPositionNedCov(
        timeUsec = timeUsec,
        estimatorType = estimatorType,
        x = x,
        y = y,
        z = z,
        vx = vx,
        vy = vy,
        vz = vz,
        ax = ax,
        ay = ay,
        az = az,
        covariance = covariance,
      )
    }


    private val METADATA: MavMessage.Metadata<LocalPositionNedCov> = MavMessage.Metadata(ID, CRC,
        DESERIALIZER)

    public val classMetadata: MavMessage.Metadata<LocalPositionNedCov> = METADATA
  }
}
