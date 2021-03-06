package com.urbanmatrix.mavlink.common

import com.urbanmatrix.mavlink.api.MavDeserializationException
import com.urbanmatrix.mavlink.api.MavDeserializer
import com.urbanmatrix.mavlink.api.MavMessage
import com.urbanmatrix.mavlink.serialization.decodeFloat
import com.urbanmatrix.mavlink.serialization.decodeInt16
import com.urbanmatrix.mavlink.serialization.decodeUint32
import com.urbanmatrix.mavlink.serialization.encodeFloat
import com.urbanmatrix.mavlink.serialization.encodeInt16
import com.urbanmatrix.mavlink.serialization.encodeUint32
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.ByteArray
import kotlin.Float
import kotlin.Int
import kotlin.Long

/**
 * The pressure readings for the typical setup of one absolute and differential pressure sensor. The
 * units are as specified in each field.
 */
public data class ScaledPressure(
  /**
   * Timestamp (time since system boot).
   */
  public val timeBootMs: Long = 0L,
  /**
   * Absolute pressure
   */
  public val pressAbs: Float = 0F,
  /**
   * Differential pressure 1
   */
  public val pressDiff: Float = 0F,
  /**
   * Absolute pressure temperature
   */
  public val temperature: Int = 0,
  /**
   * Differential pressure temperature (0, if not available). Report values of 0 (or 1) as 1 cdegC.
   */
  public val temperaturePressDiff: Int = 0,
) : MavMessage<ScaledPressure> {
  public override val instanceMetadata: MavMessage.Metadata<ScaledPressure> = METADATA

  public override fun serialize(): ByteArray {
    val outputBuffer = ByteBuffer.allocate(SIZE).order(ByteOrder.LITTLE_ENDIAN)
    outputBuffer.encodeUint32(timeBootMs)
    outputBuffer.encodeFloat(pressAbs)
    outputBuffer.encodeFloat(pressDiff)
    outputBuffer.encodeInt16(temperature)
    outputBuffer.encodeInt16(temperaturePressDiff)
    return outputBuffer.array()
  }

  public companion object {
    private const val ID: Int = 29

    private const val CRC: Int = 115

    private const val SIZE: Int = 16

    private val DESERIALIZER: MavDeserializer<ScaledPressure> = MavDeserializer { bytes ->
      if (bytes.size != SIZE) {
        throw MavDeserializationException(
          """Invalid ByteArray size for ScaledPressure: Expected=$SIZE Actual=${bytes.size}"""
        )
      }

      val inputBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
      val timeBootMs = inputBuffer.decodeUint32()
      val pressAbs = inputBuffer.decodeFloat()
      val pressDiff = inputBuffer.decodeFloat()
      val temperature = inputBuffer.decodeInt16()
      val temperaturePressDiff = inputBuffer.decodeInt16()

      ScaledPressure(
        timeBootMs = timeBootMs,
        pressAbs = pressAbs,
        pressDiff = pressDiff,
        temperature = temperature,
        temperaturePressDiff = temperaturePressDiff,
      )
    }


    private val METADATA: MavMessage.Metadata<ScaledPressure> = MavMessage.Metadata(ID, CRC,
        DESERIALIZER)

    public val classMetadata: MavMessage.Metadata<ScaledPressure> = METADATA
  }
}
