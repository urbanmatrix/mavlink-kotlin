package com.urbanmatrix.mavlink.common

import com.urbanmatrix.mavlink.api.MavDeserializationException
import com.urbanmatrix.mavlink.api.MavDeserializer
import com.urbanmatrix.mavlink.api.MavMessage
import com.urbanmatrix.mavlink.serialization.decodeFloat
import com.urbanmatrix.mavlink.serialization.decodeFloatArray
import com.urbanmatrix.mavlink.serialization.decodeInt32
import com.urbanmatrix.mavlink.serialization.decodeUint64
import com.urbanmatrix.mavlink.serialization.encodeFloat
import com.urbanmatrix.mavlink.serialization.encodeFloatArray
import com.urbanmatrix.mavlink.serialization.encodeInt32
import com.urbanmatrix.mavlink.serialization.encodeUint64
import java.math.BigInteger
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.ByteArray
import kotlin.Float
import kotlin.Int
import kotlin.collections.List

/**
 *
 * 	Contains the home position.
 * 	The home position is the default position that the system will return to and land on.
 * 	The position must be set automatically by the system during the takeoff, and may also be
 * explicitly set using MAV_CMD_DO_SET_HOME.
 * 	The global and local positions encode the position in the respective coordinate frames, while
 * the q parameter encodes the orientation of the surface.
 * 	Under normal conditions it describes the heading and terrain slope, which can be used by the
 * aircraft to adjust the approach.
 * 	The approach 3D vector describes the point to which the system should fly in normal flight mode
 * and then perform a landing sequence along the vector.
 *         Note: this message can be requested by sending the MAV_CMD_REQUEST_MESSAGE with
 * param1=242 (or the deprecated MAV_CMD_GET_HOME_POSITION command).
 *       
 */
public data class HomePosition(
  /**
   * Latitude (WGS84)
   */
  public val latitude: Int = 0,
  /**
   * Longitude (WGS84)
   */
  public val longitude: Int = 0,
  /**
   * Altitude (MSL). Positive for up.
   */
  public val altitude: Int = 0,
  /**
   * Local X position of this position in the local coordinate frame
   */
  public val x: Float = 0F,
  /**
   * Local Y position of this position in the local coordinate frame
   */
  public val y: Float = 0F,
  /**
   * Local Z position of this position in the local coordinate frame
   */
  public val z: Float = 0F,
  /**
   * World to surface normal and heading transformation of the takeoff position. Used to indicate
   * the heading and slope of the ground
   */
  public val q: List<Float> = emptyList(),
  /**
   * Local X position of the end of the approach vector. Multicopters should set this position based
   * on their takeoff path. Grass-landing fixed wing aircraft should set it the same way as
   * multicopters. Runway-landing fixed wing aircraft should set it to the opposite direction of the
   * takeoff, assuming the takeoff happened from the threshold / touchdown zone.
   */
  public val approachX: Float = 0F,
  /**
   * Local Y position of the end of the approach vector. Multicopters should set this position based
   * on their takeoff path. Grass-landing fixed wing aircraft should set it the same way as
   * multicopters. Runway-landing fixed wing aircraft should set it to the opposite direction of the
   * takeoff, assuming the takeoff happened from the threshold / touchdown zone.
   */
  public val approachY: Float = 0F,
  /**
   * Local Z position of the end of the approach vector. Multicopters should set this position based
   * on their takeoff path. Grass-landing fixed wing aircraft should set it the same way as
   * multicopters. Runway-landing fixed wing aircraft should set it to the opposite direction of the
   * takeoff, assuming the takeoff happened from the threshold / touchdown zone.
   */
  public val approachZ: Float = 0F,
  /**
   * Timestamp (UNIX Epoch time or time since system boot). The receiving end can infer timestamp
   * format (since 1.1.1970 or since system boot) by checking for the magnitude of the number.
   */
  public val timeUsec: BigInteger = BigInteger.ZERO,
) : MavMessage<HomePosition> {
  public override val instanceMetadata: MavMessage.Metadata<HomePosition> = METADATA

  public override fun serialize(): ByteArray {
    val outputBuffer = ByteBuffer.allocate(SIZE).order(ByteOrder.LITTLE_ENDIAN)
    outputBuffer.encodeInt32(latitude)
    outputBuffer.encodeInt32(longitude)
    outputBuffer.encodeInt32(altitude)
    outputBuffer.encodeFloat(x)
    outputBuffer.encodeFloat(y)
    outputBuffer.encodeFloat(z)
    outputBuffer.encodeFloatArray(q, 16)
    outputBuffer.encodeFloat(approachX)
    outputBuffer.encodeFloat(approachY)
    outputBuffer.encodeFloat(approachZ)
    outputBuffer.encodeUint64(timeUsec)
    return outputBuffer.array()
  }

  public companion object {
    private const val ID: Int = 242

    private const val CRC: Int = 53

    private const val SIZE: Int = 60

    private val DESERIALIZER: MavDeserializer<HomePosition> = MavDeserializer { bytes ->
      if (bytes.size != SIZE) {
        throw MavDeserializationException(
          """Invalid ByteArray size for HomePosition: Expected=$SIZE Actual=${bytes.size}"""
        )
      }

      val inputBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
      val latitude = inputBuffer.decodeInt32()
      val longitude = inputBuffer.decodeInt32()
      val altitude = inputBuffer.decodeInt32()
      val x = inputBuffer.decodeFloat()
      val y = inputBuffer.decodeFloat()
      val z = inputBuffer.decodeFloat()
      val q = inputBuffer.decodeFloatArray(16)
      val approachX = inputBuffer.decodeFloat()
      val approachY = inputBuffer.decodeFloat()
      val approachZ = inputBuffer.decodeFloat()
      val timeUsec = inputBuffer.decodeUint64()

      HomePosition(
        latitude = latitude,
        longitude = longitude,
        altitude = altitude,
        x = x,
        y = y,
        z = z,
        q = q,
        approachX = approachX,
        approachY = approachY,
        approachZ = approachZ,
        timeUsec = timeUsec,
      )
    }


    private val METADATA: MavMessage.Metadata<HomePosition> = MavMessage.Metadata(ID, CRC,
        DESERIALIZER)

    public val classMetadata: MavMessage.Metadata<HomePosition> = METADATA
  }
}
