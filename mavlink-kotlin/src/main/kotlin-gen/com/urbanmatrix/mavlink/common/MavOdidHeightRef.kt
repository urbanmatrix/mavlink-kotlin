package com.urbanmatrix.mavlink.common

import com.urbanmatrix.mavlink.api.MavEnum
import kotlin.Long

public enum class MavOdidHeightRef(
  public override val `value`: Long,
) : MavEnum {
  /**
   * The height field is relative to the take-off location.
   */
  MAV_ODID_HEIGHT_REF_OVER_TAKEOFF(0L),
  /**
   * The height field is relative to ground.
   */
  MAV_ODID_HEIGHT_REF_OVER_GROUND(1L),
  ;

  public companion object {
    public fun getEntryFromValueOrNull(v: Long): MavOdidHeightRef? = when (v) {
      0L -> MAV_ODID_HEIGHT_REF_OVER_TAKEOFF
      1L -> MAV_ODID_HEIGHT_REF_OVER_GROUND
      else -> null
    }
  }
}
