package com.urbanmatrix.mavlink.common

import com.urbanmatrix.mavlink.api.MavDeserializationException
import com.urbanmatrix.mavlink.api.MavDeserializer
import com.urbanmatrix.mavlink.api.MavMessage
import com.urbanmatrix.mavlink.serialization.decodeUint16
import com.urbanmatrix.mavlink.serialization.decodeUint8
import com.urbanmatrix.mavlink.serialization.encodeUint16
import com.urbanmatrix.mavlink.serialization.encodeUint8
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.ByteArray
import kotlin.Int

/**
 * Status generated by radio and injected into MAVLink stream.
 */
public data class RadioStatus(
  /**
   * Local (message sender) recieved signal strength indication in device-dependent units/scale.
   * Values: [0-254], UINT8_MAX: invalid/unknown.
   */
  public val rssi: Int = 0,
  /**
   * Remote (message receiver) signal strength indication in device-dependent units/scale. Values:
   * [0-254], UINT8_MAX: invalid/unknown.
   */
  public val remrssi: Int = 0,
  /**
   * Remaining free transmitter buffer space.
   */
  public val txbuf: Int = 0,
  /**
   * Local background noise level. These are device dependent RSSI values (scale as approx 2x dB on
   * SiK radios). Values: [0-254], UINT8_MAX: invalid/unknown.
   */
  public val noise: Int = 0,
  /**
   * Remote background noise level. These are device dependent RSSI values (scale as approx 2x dB on
   * SiK radios). Values: [0-254], UINT8_MAX: invalid/unknown.
   */
  public val remnoise: Int = 0,
  /**
   * Count of radio packet receive errors (since boot).
   */
  public val rxerrors: Int = 0,
  /**
   * Count of error corrected radio packets (since boot).
   */
  public val fixed: Int = 0,
) : MavMessage<RadioStatus> {
  public override val instanceMetadata: MavMessage.Metadata<RadioStatus> = METADATA

  public override fun serialize(): ByteArray {
    val outputBuffer = ByteBuffer.allocate(SIZE).order(ByteOrder.LITTLE_ENDIAN)
    outputBuffer.encodeUint16(rxerrors)
    outputBuffer.encodeUint16(fixed)
    outputBuffer.encodeUint8(rssi)
    outputBuffer.encodeUint8(remrssi)
    outputBuffer.encodeUint8(txbuf)
    outputBuffer.encodeUint8(noise)
    outputBuffer.encodeUint8(remnoise)
    return outputBuffer.array()
  }

  public companion object {
    private const val ID: Int = 109

    private const val CRC: Int = 185

    private const val SIZE: Int = 9

    private val DESERIALIZER: MavDeserializer<RadioStatus> = MavDeserializer { bytes ->
      if (bytes.size != SIZE) {
        throw MavDeserializationException(
          """Invalid ByteArray size for RadioStatus: Expected=$SIZE Actual=${bytes.size}"""
        )
      }

      val inputBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
      val rxerrors = inputBuffer.decodeUint16()
      val fixed = inputBuffer.decodeUint16()
      val rssi = inputBuffer.decodeUint8()
      val remrssi = inputBuffer.decodeUint8()
      val txbuf = inputBuffer.decodeUint8()
      val noise = inputBuffer.decodeUint8()
      val remnoise = inputBuffer.decodeUint8()

      RadioStatus(
        rssi = rssi,
        remrssi = remrssi,
        txbuf = txbuf,
        noise = noise,
        remnoise = remnoise,
        rxerrors = rxerrors,
        fixed = fixed,
      )
    }


    private val METADATA: MavMessage.Metadata<RadioStatus> = MavMessage.Metadata(ID, CRC,
        DESERIALIZER)

    public val classMetadata: MavMessage.Metadata<RadioStatus> = METADATA
  }
}
