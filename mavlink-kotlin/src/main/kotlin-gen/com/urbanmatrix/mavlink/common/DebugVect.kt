package com.urbanmatrix.mavlink.common

import com.urbanmatrix.mavlink.api.MavDeserializationException
import com.urbanmatrix.mavlink.api.MavDeserializer
import com.urbanmatrix.mavlink.api.MavMessage
import com.urbanmatrix.mavlink.serialization.decodeFloat
import com.urbanmatrix.mavlink.serialization.decodeString
import com.urbanmatrix.mavlink.serialization.decodeUint64
import com.urbanmatrix.mavlink.serialization.encodeFloat
import com.urbanmatrix.mavlink.serialization.encodeString
import com.urbanmatrix.mavlink.serialization.encodeUint64
import java.math.BigInteger
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.ByteArray
import kotlin.Float
import kotlin.Int
import kotlin.String

/**
 * To debug something using a named 3D vector.
 */
public data class DebugVect(
  /**
   * Name
   */
  public val name: String = "",
  /**
   * Timestamp (UNIX Epoch time or time since system boot). The receiving end can infer timestamp
   * format (since 1.1.1970 or since system boot) by checking for the magnitude of the number.
   */
  public val timeUsec: BigInteger = BigInteger.ZERO,
  /**
   * x
   */
  public val x: Float = 0F,
  /**
   * y
   */
  public val y: Float = 0F,
  /**
   * z
   */
  public val z: Float = 0F,
) : MavMessage<DebugVect> {
  public override val instanceMetadata: MavMessage.Metadata<DebugVect> = METADATA

  public override fun serialize(): ByteArray {
    val outputBuffer = ByteBuffer.allocate(SIZE).order(ByteOrder.LITTLE_ENDIAN)
    outputBuffer.encodeUint64(timeUsec)
    outputBuffer.encodeFloat(x)
    outputBuffer.encodeFloat(y)
    outputBuffer.encodeFloat(z)
    outputBuffer.encodeString(name, 10)
    return outputBuffer.array()
  }

  public companion object {
    private const val ID: Int = 250

    private const val CRC: Int = 245

    private const val SIZE: Int = 30

    private val DESERIALIZER: MavDeserializer<DebugVect> = MavDeserializer { bytes ->
      if (bytes.size != SIZE) {
        throw MavDeserializationException(
          """Invalid ByteArray size for DebugVect: Expected=$SIZE Actual=${bytes.size}"""
        )
      }

      val inputBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
      val timeUsec = inputBuffer.decodeUint64()
      val x = inputBuffer.decodeFloat()
      val y = inputBuffer.decodeFloat()
      val z = inputBuffer.decodeFloat()
      val name = inputBuffer.decodeString(10)

      DebugVect(
        name = name,
        timeUsec = timeUsec,
        x = x,
        y = y,
        z = z,
      )
    }


    private val METADATA: MavMessage.Metadata<DebugVect> = MavMessage.Metadata(ID, CRC,
        DESERIALIZER)

    public val classMetadata: MavMessage.Metadata<DebugVect> = METADATA
  }
}
