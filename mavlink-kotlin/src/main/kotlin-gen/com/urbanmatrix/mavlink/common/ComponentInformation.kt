package com.urbanmatrix.mavlink.common

import com.urbanmatrix.mavlink.api.MavDeserializationException
import com.urbanmatrix.mavlink.api.MavDeserializer
import com.urbanmatrix.mavlink.api.MavMessage
import com.urbanmatrix.mavlink.serialization.decodeString
import com.urbanmatrix.mavlink.serialization.decodeUint32
import com.urbanmatrix.mavlink.serialization.encodeString
import com.urbanmatrix.mavlink.serialization.encodeUint32
import java.nio.ByteBuffer
import java.nio.ByteOrder
import kotlin.ByteArray
import kotlin.Deprecated
import kotlin.Int
import kotlin.Long
import kotlin.String

/**
 *
 *         Component information message, which may be requested using MAV_CMD_REQUEST_MESSAGE.
 *       
 */
@Deprecated(message = "")
public data class ComponentInformation(
  /**
   * (Optional) MAVLink FTP URI for the peripherals metadata file (COMP_METADATA_TYPE_PERIPHERALS),
   * which may be compressed with xz. This contains data about "attached components" such as UAVCAN
   * nodes. The peripherals are in a separate file because the information must be generated
   * dynamically at runtime. The string needs to be zero terminated.
   */
  public val peripheralsMetadataUri: String = "",
  /**
   * CRC32 of peripherals metadata file (peripherals_metadata_uri).
   */
  public val peripheralsMetadataFileCrc: Long = 0L,
  /**
   * MAVLink FTP URI for the general metadata file (COMP_METADATA_TYPE_GENERAL), which may be
   * compressed with xz. The file contains general component metadata, and may contain URI links for
   * additional metadata (see COMP_METADATA_TYPE). The information is static from boot, and may be
   * generated at compile time. The string needs to be zero terminated.
   */
  public val generalMetadataUri: String = "",
  /**
   * CRC32 of the general metadata file (general_metadata_uri).
   */
  public val generalMetadataFileCrc: Long = 0L,
  /**
   * Timestamp (time since system boot).
   */
  public val timeBootMs: Long = 0L,
) : MavMessage<ComponentInformation> {
  public override val instanceMetadata: MavMessage.Metadata<ComponentInformation> = METADATA

  public override fun serialize(): ByteArray {
    val outputBuffer = ByteBuffer.allocate(SIZE).order(ByteOrder.LITTLE_ENDIAN)
    outputBuffer.encodeUint32(peripheralsMetadataFileCrc)
    outputBuffer.encodeUint32(generalMetadataFileCrc)
    outputBuffer.encodeUint32(timeBootMs)
    outputBuffer.encodeString(peripheralsMetadataUri, 100)
    outputBuffer.encodeString(generalMetadataUri, 100)
    return outputBuffer.array()
  }

  public companion object {
    private const val ID: Int = 395

    private const val CRC: Int = 39

    private const val SIZE: Int = 212

    private val DESERIALIZER: MavDeserializer<ComponentInformation> = MavDeserializer { bytes ->
      if (bytes.size != SIZE) {
        throw MavDeserializationException(
          """Invalid ByteArray size for ComponentInformation: Expected=$SIZE Actual=${bytes.size}"""
        )
      }

      val inputBuffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN)
      val peripheralsMetadataFileCrc = inputBuffer.decodeUint32()
      val generalMetadataFileCrc = inputBuffer.decodeUint32()
      val timeBootMs = inputBuffer.decodeUint32()
      val peripheralsMetadataUri = inputBuffer.decodeString(100)
      val generalMetadataUri = inputBuffer.decodeString(100)

      ComponentInformation(
        peripheralsMetadataUri = peripheralsMetadataUri,
        peripheralsMetadataFileCrc = peripheralsMetadataFileCrc,
        generalMetadataUri = generalMetadataUri,
        generalMetadataFileCrc = generalMetadataFileCrc,
        timeBootMs = timeBootMs,
      )
    }


    private val METADATA: MavMessage.Metadata<ComponentInformation> = MavMessage.Metadata(ID, CRC,
        DESERIALIZER)

    public val classMetadata: MavMessage.Metadata<ComponentInformation> = METADATA
  }
}
