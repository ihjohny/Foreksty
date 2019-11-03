package com.aappeye.foreksty.utils

object UnitConverter {

    //temperature
    fun fahrenheitToCelsius(fahrenheit: Double?): Double?=
        if (fahrenheit != null)
            ((fahrenheit - 32) * 5) / 9
        else null

    //speed
    fun milepHourToMeterpSecond(milepHour: Double?): Double?=
        if (milepHour != null)
            milepHour * 0.44704
        else null

    //distance
    fun mileToKilometer(mile: Double): Double?=
        if (mile != null)
            mile * 1.609344
        else null

    //pressure
    //1mb == 1hPa

    //percentage
    fun percentage(value: Double): Double?=
        if (value != null)
            value * 100
        else null

}