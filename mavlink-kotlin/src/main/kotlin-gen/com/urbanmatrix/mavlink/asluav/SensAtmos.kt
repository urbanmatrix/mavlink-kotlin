package com.urbanmatrix.mavlink.asluav

import com.urbanmatrix.mavlink.api.MavDeserializationException
import com.urbanmatrix.mavlink.api.MavDeserializer
import com.urbanmatrix.mavlink.api.MavMessage
import com.urbanmatrix.mavlink.serialization.decodeFloat
import com.urbanmatrix.mavlink.serialization.decodeUint64
import com.urbanmatrix.mavlink.serialization.encodeFloat
import com.urbanmatrix.mavlink.serialization.encodeUint64
import java.math.BigInteger
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.ByteArray
import kotlin.Float
import kotlin.Int

/**
 * Atmospheric sensors (temperature, humidity, ...) 
 */
public data class SensAtmos(
  /**
   * Time since system boot
   */
  public val timestamp: BigInteger = BigInteger.ZERO,
  /**
   *  Ambient temperature
   */
  public val tempambient: Float = 0F,
  /**
   *  Relative humidity
   */
  public val humidity: Float = 0F,
) : MavMessage<SensAtmos> {
  public override val instanceMetadata: MavMessage.Metadata<SensAtmos> = METADATA

  public override fun serialize(): ByteArray {
    val outputBuffer = ByteBuffer.allocate(SIZE).order(ByteOrder.LITTLE_ENDIAN)
    outputBuffer.encodeUint64(timestamp)
    outputBuffer.encodeFloat(tempambient)
    outputBuffer.encodeFloat(humidity)
    return outputBuffer.array()
  }

  public companion object {
    private const val ID: Int = 8009

    private const val CRC: Int = 144

    private const val SIZE: Int = 16

    private val DESERIALIZER: MavDeserializer<SensAtmos> = MavDeserializer { bytes ->
      if (bytes.size != SIZE) {
        throw MavDeserializationException(
          """Invalid ByteArray size for SensAtmos: Expected=$SIZE Actual=${bytes.size}"""
        )
      }

      val inputBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
      val timestamp = inputBuffer.decodeUint64()
      val tempambient = inputBuffer.decodeFloat()
      val humidity = inputBuffer.decodeFloat()

      SensAtmos(
        timestamp = timestamp,
        tempambient = tempambient,
        humidity = humidity,
      )
    }


    private val METADATA: MavMessage.Metadata<SensAtmos> = MavMessage.Metadata(ID, CRC,
        DESERIALIZER)

    public val classMetadata: MavMessage.Metadata<SensAtmos> = METADATA
  }
}
