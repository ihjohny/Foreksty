package com.aappeye.foreksty.data.provider

interface SettingsProvider {
    fun getPreferredUnitSystem(): String
    fun getPreferredLanguage(): String
    fun getPreferredUpdateFrequency(): String
}