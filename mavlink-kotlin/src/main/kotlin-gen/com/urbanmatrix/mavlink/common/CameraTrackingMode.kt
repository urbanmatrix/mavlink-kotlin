package com.urbanmatrix.mavlink.common

import com.urbanmatrix.mavlink.api.MavEnum
import kotlin.Long

/**
 * Camera tracking modes
 */
public enum class CameraTrackingMode(
  public override val `value`: Long,
) : MavEnum {
  /**
   * Not tracking
   */
  CAMERA_TRACKING_MODE_NONE(0L),
  /**
   * Target is a point
   */
  CAMERA_TRACKING_MODE_POINT(1L),
  /**
   * Target is a rectangle
   */
  CAMERA_TRACKING_MODE_RECTANGLE(2L),
  ;

  public companion object {
    public fun getEntryFromValueOrNull(v: Long): CameraTrackingMode? = when (v) {
      0L -> CAMERA_TRACKING_MODE_NONE
      1L -> CAMERA_TRACKING_MODE_POINT
      2L -> CAMERA_TRACKING_MODE_RECTANGLE
      else -> null
    }
  }
}
