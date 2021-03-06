package com.urbanmatrix.mavlink.common

import com.urbanmatrix.mavlink.api.MavEnum
import kotlin.Long

public enum class MavOdidStatus(
  public override val `value`: Long,
) : MavEnum {
  /**
   * The status of the (UA) Unmanned Aircraft is undefined.
   */
  MAV_ODID_STATUS_UNDECLARED(0L),
  /**
   * The UA is on the ground.
   */
  MAV_ODID_STATUS_GROUND(1L),
  /**
   * The UA is in the air.
   */
  MAV_ODID_STATUS_AIRBORNE(2L),
  /**
   * The UA is having an emergency.
   */
  MAV_ODID_STATUS_EMERGENCY(3L),
  /**
   * The remote ID system is failing or unreliable in some way.
   */
  MAV_ODID_STATUS_REMOTE_ID_SYSTEM_FAILURE(4L),
  ;

  public companion object {
    public fun getEntryFromValueOrNull(v: Long): MavOdidStatus? = when (v) {
      0L -> MAV_ODID_STATUS_UNDECLARED
      1L -> MAV_ODID_STATUS_GROUND
      2L -> MAV_ODID_STATUS_AIRBORNE
      3L -> MAV_ODID_STATUS_EMERGENCY
      4L -> MAV_ODID_STATUS_REMOTE_ID_SYSTEM_FAILURE
      else -> null
    }
  }
}
