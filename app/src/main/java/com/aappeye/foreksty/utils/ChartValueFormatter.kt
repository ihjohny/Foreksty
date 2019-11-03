package com.aappeye.foreksty.utils

import com.aappeye.foreksty.utils.StringFormatter.getHour
import com.github.mikephil.charting.formatter.ValueFormatter

object ChartValueFormatter {
    class AxisValuePercentageFormatter: ValueFormatter(){
        override fun getFormattedValue(value: Float): String {
            return "${value.toInt()}%"
        }
    }
    class AxisValueHourFormatter: ValueFormatter(){
        override fun getFormattedValue(value: Float): String {
            return getHour(value.toLong())
        }
    }
}