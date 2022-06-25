package com.urbanmatrix.mavlink.ardupilotmega

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
 * Status generated by radio.
 */
public data class Radio(
  /**
   * Local signal strength.
   */
  public val rssi: Int = 0,
  /**
   * Remote signal strength.
   */
  public val remrssi: Int = 0,
  /**
   * How full the tx buffer is.
   */
  public val txbuf: Int = 0,
  /**
   * Background noise level.
   */
  public val noise: Int = 0,
  /**
   * Remote background noise level.
   */
  public val remnoise: Int = 0,
  /**
   * Receive errors.
   */
  public val rxerrors: Int = 0,
  /**
   * Count of error corrected packets.
   */
  public val fixed: Int = 0,
) : MavMessage<Radio> {
  public override val instanceMetadata: MavMessage.Metadata<Radio> = METADATA

  public override fun serialize(): ByteArray {
    val outputBuffer = ByteBuffer.allocate(9).order(ByteOrder.LITTLE_ENDIAN)
    outputBuffer.encodeUint8(rssi)
    outputBuffer.encodeUint8(remrssi)
    outputBuffer.encodeUint8(txbuf)
    outputBuffer.encodeUint8(noise)
    outputBuffer.encodeUint8(remnoise)
    outputBuffer.encodeUint16(rxerrors)
    outputBuffer.encodeUint16(fixed)
    return outputBuffer.array()
  }

  public companion object {
    private const val ID: Int = 166

    private const val CRC: Int = 93

    private val DESERIALIZER: MavDeserializer<Radio> = MavDeserializer { bytes ->
      val inputBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
      val rssi = inputBuffer.decodeUint8()
      val remrssi = inputBuffer.decodeUint8()
      val txbuf = inputBuffer.decodeUint8()
      val noise = inputBuffer.decodeUint8()
      val remnoise = inputBuffer.decodeUint8()
      val rxerrors = inputBuffer.decodeUint16()
      val fixed = inputBuffer.decodeUint16()
      Radio(
        rssi = rssi,
        remrssi = remrssi,
        txbuf = txbuf,
        noise = noise,
        remnoise = remnoise,
        rxerrors = rxerrors,
        fixed = fixed,
      )
    }


    private val METADATA: MavMessage.Metadata<Radio> = MavMessage.Metadata(ID, CRC, DESERIALIZER)

    public val classMetadata: MavMessage.Metadata<Radio> = METADATA
  }
}
