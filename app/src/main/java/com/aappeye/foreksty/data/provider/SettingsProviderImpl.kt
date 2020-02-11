package com.aappeye.foreksty.data.provider

import android.content.Context
import javax.inject.Inject

const val UNIT_SYSTEM = "unit_system"
const val UNIT_SYSTEM_DEF = "si"
const val LANGUAGE ="language"
const val LANGUAGE_DEF = "en"
const val UPDATE_FREQUENCY ="update_freq"
const val UPDATE_FREQUENCY_DEF = "30"

class SettingsProviderImpl @Inject constructor(
    context: Context
): PreferenceProvider(context), SettingsProvider {


    override fun getPreferredUnitSystem(): String {
        return preferences.getString(UNIT_SYSTEM, UNIT_SYSTEM_DEF)!!
    }

    override fun getPreferredLanguage(): String {
        return preferences.getString(LANGUAGE, LANGUAGE_DEF)!!
    }

    override fun getPreferredUpdateFrequency(): String {
        return preferences.getString(UPDATE_FREQUENCY, UPDATE_FREQUENCY_DEF)!!
    }

}