package com.urbanmatrix.mavlink.ardupilotmega

import com.urbanmatrix.mavlink.api.MavDeserializationException
import com.urbanmatrix.mavlink.api.MavDeserializer
import com.urbanmatrix.mavlink.api.MavMessage
import com.urbanmatrix.mavlink.serialization.decodeFloat
import com.urbanmatrix.mavlink.serialization.decodeUint16
import com.urbanmatrix.mavlink.serialization.encodeFloat
import com.urbanmatrix.mavlink.serialization.encodeUint16
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.ByteArray
import kotlin.Float
import kotlin.Int

/**
 * Status of compassmot calibration.
 */
public data class CompassmotStatus(
  /**
   * Throttle.
   */
  public val throttle: Int = 0,
  /**
   * Current.
   */
  public val current: Float = 0F,
  /**
   * Interference.
   */
  public val interference: Int = 0,
  /**
   * Motor Compensation X.
   */
  public val compensationx: Float = 0F,
  /**
   * Motor Compensation Y.
   */
  public val compensationy: Float = 0F,
  /**
   * Motor Compensation Z.
   */
  public val compensationz: Float = 0F,
) : MavMessage<CompassmotStatus> {
  public override val instanceMetadata: MavMessage.Metadata<CompassmotStatus> = METADATA

  public override fun serialize(): ByteArray {
    val outputBuffer = ByteBuffer.allocate(SIZE).order(ByteOrder.LITTLE_ENDIAN)
    outputBuffer.encodeFloat(current)
    outputBuffer.encodeFloat(compensationx)
    outputBuffer.encodeFloat(compensationy)
    outputBuffer.encodeFloat(compensationz)
    outputBuffer.encodeUint16(throttle)
    outputBuffer.encodeUint16(interference)
    return outputBuffer.array()
  }

  public companion object {
    private const val ID: Int = 177

    private const val CRC: Int = 240

    private const val SIZE: Int = 20

    private val DESERIALIZER: MavDeserializer<CompassmotStatus> = MavDeserializer { bytes ->
      if (bytes.size != SIZE) {
        throw MavDeserializationException(
          """Invalid ByteArray size for CompassmotStatus: Expected=$SIZE Actual=${bytes.size}"""
        )
      }

      val inputBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
      val current = inputBuffer.decodeFloat()
      val compensationx = inputBuffer.decodeFloat()
      val compensationy = inputBuffer.decodeFloat()
      val compensationz = inputBuffer.decodeFloat()
      val throttle = inputBuffer.decodeUint16()
      val interference = inputBuffer.decodeUint16()

      CompassmotStatus(
        throttle = throttle,
        current = current,
        interference = interference,
        compensationx = compensationx,
        compensationy = compensationy,
        compensationz = compensationz,
      )
    }


    private val METADATA: MavMessage.Metadata<CompassmotStatus> = MavMessage.Metadata(ID, CRC,
        DESERIALIZER)

    public val classMetadata: MavMessage.Metadata<CompassmotStatus> = METADATA
  }
}
