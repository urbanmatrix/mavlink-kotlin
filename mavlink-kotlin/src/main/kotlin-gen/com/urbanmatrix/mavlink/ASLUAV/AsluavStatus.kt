package com.urbanmatrix.mavlink.ASLUAV

import com.urbanmatrix.mavlink.api.MavDeserializer
import com.urbanmatrix.mavlink.api.MavMessage
import com.urbanmatrix.mavlink.serialization.decodeFloat
import com.urbanmatrix.mavlink.serialization.decodeUint8
import com.urbanmatrix.mavlink.serialization.decodeUint8Array
import com.urbanmatrix.mavlink.serialization.encodeFloat
import com.urbanmatrix.mavlink.serialization.encodeUint8
import com.urbanmatrix.mavlink.serialization.encodeUint8Array
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.ByteArray
import kotlin.Float
import kotlin.Int
import kotlin.collections.List

/**
 * Extended state information for ASLUAVs
 */
public data class AsluavStatus(
  /**
   *  Motor RPM 
   */
  public val motorRpm: Float = 0F,
  /**
   *  Status of the position-indicator LEDs
   */
  public val ledStatus: Int = 0,
  /**
   *  Status of the IRIDIUM satellite communication system
   */
  public val satcomStatus: Int = 0,
  /**
   *  Status vector for up to 8 servos
   */
  public val servoStatus: List<Int> = emptyList(),
) : MavMessage<AsluavStatus> {
  public override val instanceMetadata: MavMessage.Metadata<AsluavStatus> = METADATA

  public override fun serialize(): ByteArray {
    val outputBuffer = ByteBuffer.allocate(14).order(ByteOrder.LITTLE_ENDIAN)
    outputBuffer.encodeFloat(motorRpm)
    outputBuffer.encodeUint8(ledStatus)
    outputBuffer.encodeUint8(satcomStatus)
    outputBuffer.encodeUint8Array(servoStatus, 8)
    return outputBuffer.array()
  }

  public companion object {
    private const val ID: Int = 8006

    private const val CRC: Int = 165

    private val DESERIALIZER: MavDeserializer<AsluavStatus> = MavDeserializer { bytes ->
      val inputBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
      val motorRpm = inputBuffer.decodeFloat()
      val ledStatus = inputBuffer.decodeUint8()
      val satcomStatus = inputBuffer.decodeUint8()
      val servoStatus = inputBuffer.decodeUint8Array(8)
      AsluavStatus(
        motorRpm = motorRpm,
        ledStatus = ledStatus,
        satcomStatus = satcomStatus,
        servoStatus = servoStatus,
      )
    }


    private val METADATA: MavMessage.Metadata<AsluavStatus> = MavMessage.Metadata(ID, CRC,
        DESERIALIZER)

    public val classMetadata: MavMessage.Metadata<AsluavStatus> = METADATA
  }
}