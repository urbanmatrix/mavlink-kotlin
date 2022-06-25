package com.urbanmatrix.mavlink.common

import com.urbanmatrix.mavlink.api.MavDeserializer
import com.urbanmatrix.mavlink.api.MavMessage
import com.urbanmatrix.mavlink.serialization.decodeInt16
import com.urbanmatrix.mavlink.serialization.decodeUint16
import com.urbanmatrix.mavlink.serialization.decodeUint8
import com.urbanmatrix.mavlink.serialization.encodeInt16
import com.urbanmatrix.mavlink.serialization.encodeUint16
import com.urbanmatrix.mavlink.serialization.encodeUint8
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.ByteArray
import kotlin.Int

/**
 * Temperature and humidity from hygrometer.
 */
public data class HygrometerSensor(
  /**
   * Hygrometer ID
   */
  public val id: Int = 0,
  /**
   * Temperature
   */
  public val temperature: Int = 0,
  /**
   * Humidity
   */
  public val humidity: Int = 0,
) : MavMessage<HygrometerSensor> {
  public override val instanceMetadata: MavMessage.Metadata<HygrometerSensor> = METADATA

  public override fun serialize(): ByteArray {
    val outputBuffer = ByteBuffer.allocate(5).order(ByteOrder.LITTLE_ENDIAN)
    outputBuffer.encodeUint8(id)
    outputBuffer.encodeInt16(temperature)
    outputBuffer.encodeUint16(humidity)
    return outputBuffer.array()
  }

  public companion object {
    private const val ID: Int = 12920

    private const val CRC: Int = 48

    private val DESERIALIZER: MavDeserializer<HygrometerSensor> = MavDeserializer { bytes ->
      val inputBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
      val id = inputBuffer.decodeUint8()
      val temperature = inputBuffer.decodeInt16()
      val humidity = inputBuffer.decodeUint16()
      HygrometerSensor(
        id = id,
        temperature = temperature,
        humidity = humidity,
      )
    }


    private val METADATA: MavMessage.Metadata<HygrometerSensor> = MavMessage.Metadata(ID, CRC,
        DESERIALIZER)

    public val classMetadata: MavMessage.Metadata<HygrometerSensor> = METADATA
  }
}
