package com.urbanmatrix.mavlink.common

import com.urbanmatrix.mavlink.api.MavEnum
import kotlin.Long

public enum class FenceBreach(
  public override val `value`: Long,
) : MavEnum {
  /**
   * No last fence breach
   */
  FENCE_BREACH_NONE(0L),
  /**
   * Breached minimum altitude
   */
  FENCE_BREACH_MINALT(1L),
  /**
   * Breached maximum altitude
   */
  FENCE_BREACH_MAXALT(2L),
  /**
   * Breached fence boundary
   */
  FENCE_BREACH_BOUNDARY(3L),
  ;

  public companion object {
    public fun getEntryFromValueOrNull(v: Long): FenceBreach? = when (v) {
      0L -> FENCE_BREACH_NONE
      1L -> FENCE_BREACH_MINALT
      2L -> FENCE_BREACH_MAXALT
      3L -> FENCE_BREACH_BOUNDARY
      else -> null
    }
  }
}
