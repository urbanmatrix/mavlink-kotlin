package com.urbanmatrix.mavlink.ardupilotmega

import com.urbanmatrix.mavlink.api.MavEnum
import kotlin.Long

public enum class GoproVideoSettingsFlags(
  public override val `value`: Long,
) : MavEnum {
  /**
   * 0=NTSC, 1=PAL.
   */
  GOPRO_VIDEO_SETTINGS_TV_MODE(1L),
  ;

  public companion object {
    public fun getEntryFromValueOrNull(v: Long): GoproVideoSettingsFlags? = when (v) {
      1L -> GOPRO_VIDEO_SETTINGS_TV_MODE
      else -> null
    }
  }
}
