package com.urbanmatrix.mavlink.common

import com.urbanmatrix.mavlink.api.MavEnum
import kotlin.Long

/**
 * List of possible units where failures can be injected.
 */
public enum class FailureUnit(
  public override val `value`: Long,
) : MavEnum {
  FAILURE_UNIT_SENSOR_GYRO(0L),
  FAILURE_UNIT_SENSOR_ACCEL(1L),
  FAILURE_UNIT_SENSOR_MAG(2L),
  FAILURE_UNIT_SENSOR_BARO(3L),
  FAILURE_UNIT_SENSOR_GPS(4L),
  FAILURE_UNIT_SENSOR_OPTICAL_FLOW(5L),
  FAILURE_UNIT_SENSOR_VIO(6L),
  FAILURE_UNIT_SENSOR_DISTANCE_SENSOR(7L),
  FAILURE_UNIT_SENSOR_AIRSPEED(8L),
  FAILURE_UNIT_SYSTEM_BATTERY(100L),
  FAILURE_UNIT_SYSTEM_MOTOR(101L),
  FAILURE_UNIT_SYSTEM_SERVO(102L),
  FAILURE_UNIT_SYSTEM_AVOIDANCE(103L),
  FAILURE_UNIT_SYSTEM_RC_SIGNAL(104L),
  FAILURE_UNIT_SYSTEM_MAVLINK_SIGNAL(105L),
  ;

  public companion object {
    public fun getEntryFromValueOrNull(v: Long): FailureUnit? = when (v) {
      0L -> FAILURE_UNIT_SENSOR_GYRO
      1L -> FAILURE_UNIT_SENSOR_ACCEL
      2L -> FAILURE_UNIT_SENSOR_MAG
      3L -> FAILURE_UNIT_SENSOR_BARO
      4L -> FAILURE_UNIT_SENSOR_GPS
      5L -> FAILURE_UNIT_SENSOR_OPTICAL_FLOW
      6L -> FAILURE_UNIT_SENSOR_VIO
      7L -> FAILURE_UNIT_SENSOR_DISTANCE_SENSOR
      8L -> FAILURE_UNIT_SENSOR_AIRSPEED
      100L -> FAILURE_UNIT_SYSTEM_BATTERY
      101L -> FAILURE_UNIT_SYSTEM_MOTOR
      102L -> FAILURE_UNIT_SYSTEM_SERVO
      103L -> FAILURE_UNIT_SYSTEM_AVOIDANCE
      104L -> FAILURE_UNIT_SYSTEM_RC_SIGNAL
      105L -> FAILURE_UNIT_SYSTEM_MAVLINK_SIGNAL
      else -> null
    }
  }
}