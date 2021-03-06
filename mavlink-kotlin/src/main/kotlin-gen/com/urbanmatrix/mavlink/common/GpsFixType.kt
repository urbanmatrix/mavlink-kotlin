package com.urbanmatrix.mavlink.common

import com.urbanmatrix.mavlink.api.MavEnum
import kotlin.Long

/**
 * Type of GPS fix
 */
public enum class GpsFixType(
  public override val `value`: Long,
) : MavEnum {
  /**
   * No GPS connected
   */
  GPS_FIX_TYPE_NO_GPS(0L),
  /**
   * No position information, GPS is connected
   */
  GPS_FIX_TYPE_NO_FIX(1L),
  /**
   * 2D position
   */
  GPS_FIX_TYPE_2D_FIX(2L),
  /**
   * 3D position
   */
  GPS_FIX_TYPE_3D_FIX(3L),
  /**
   * DGPS/SBAS aided 3D position
   */
  GPS_FIX_TYPE_DGPS(4L),
  /**
   * RTK float, 3D position
   */
  GPS_FIX_TYPE_RTK_FLOAT(5L),
  /**
   * RTK Fixed, 3D position
   */
  GPS_FIX_TYPE_RTK_FIXED(6L),
  /**
   * Static fixed, typically used for base stations
   */
  GPS_FIX_TYPE_STATIC(7L),
  /**
   * PPP, 3D position.
   */
  GPS_FIX_TYPE_PPP(8L),
  ;

  public companion object {
    public fun getEntryFromValueOrNull(v: Long): GpsFixType? = when (v) {
      0L -> GPS_FIX_TYPE_NO_GPS
      1L -> GPS_FIX_TYPE_NO_FIX
      2L -> GPS_FIX_TYPE_2D_FIX
      3L -> GPS_FIX_TYPE_3D_FIX
      4L -> GPS_FIX_TYPE_DGPS
      5L -> GPS_FIX_TYPE_RTK_FLOAT
      6L -> GPS_FIX_TYPE_RTK_FIXED
      7L -> GPS_FIX_TYPE_STATIC
      8L -> GPS_FIX_TYPE_PPP
      else -> null
    }
  }
}
