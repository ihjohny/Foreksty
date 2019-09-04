package com.aappeye.foreksty.utils

import kotlinx.android.synthetic.main.current_weather_fragment.*
import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

object StringFormatter {

    const val percentage = "%"
    //Imperial
    const val degreeFahrenheit ="ºF"
    const val milesPerHour ="mph"
    const val miles = "mi"
    const val millibar = "mb"
    //Metric
    const val degreeCelsius = "ºC"
    const val metersPerSecond = "m/s"
    const val kilometers = "km"
    const val hectopascals ="hPa"

    fun getTemperaturesWithUnit(value: Double, isMetricUnit: Boolean): String =
            if(isMetricUnit) { "${UnitConverter.fahrenheitToCelsius(value)?.toInt()}${degreeCelsius}"}
            else {"${value.toInt()}${degreeFahrenheit}"}

    fun getSpeedWithUnit(value: Double, isMetricUnit: Boolean): String =
        if(isMetricUnit) { "${UnitConverter.milepHourToMeterpSecond(value)?.toInt()} ${metersPerSecond}"}
        else {"${value.toInt()} ${milesPerHour}"}

    fun getPressureWithUnit(value: Double, isMetricUnit: Boolean): String =
        if(isMetricUnit) { "${value.toInt()} ${hectopascals}"}
        else {"${value.toInt()} ${millibar}"}

    fun getDistanceWithUnit(value: Double, isMetricUnit: Boolean): String =
        if(isMetricUnit) { "${UnitConverter.mileToKilometer(value)?.toInt()} ${kilometers}"}
        else {"${value.toInt()} ${miles}"}

    fun getPercentage(value: Double): String =
        "${(value*100).toInt()}${percentage}"

    fun getTime(time: Long): String =
        Instant.ofEpochSecond(time).atZone(ZoneId.systemDefault()).toLocalDateTime()
            .format(DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault()))

    fun getDay(time: Long): String =
        Instant.ofEpochSecond(time).atZone(ZoneId.systemDefault()).toLocalDateTime()
            .format(DateTimeFormatter.ofPattern("EEEE", Locale.getDefault()))

    fun getDate(time: Long): String =
        Instant.ofEpochSecond(time).atZone(ZoneId.systemDefault()).toLocalDateTime()
            .format(DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.getDefault()))



}