package com.aappeye.foreksty.ui.settings

import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.widget.TimePicker
import androidx.appcompat.app.AlertDialog
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.aappeye.foreksty.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        notificationReceiveTime()
        aboutDialogShow()
    }

    private fun notificationReceiveTime() {
        val receiveTime : Preference? = findPreference(resources.getString(R.string.receive_time_key))
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val receiveTimeStored = sharedPreferences.getInt(resources.getString(R.string.receive_time_key),0)
        val receiveTimeStoredText = "${receiveTimeStored/60}:${receiveTimeStored%60}"
        receiveTime?.summaryProvider = Preference.SummaryProvider<Preference> { preference -> receiveTimeStoredText }

        receiveTime?.setOnPreferenceClickListener {
            val timePickerDialog: TimePickerDialog
            val timePicker = TimePicker(context)
            val currentHour = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.hour
            } else {
                timePicker.currentHour
            }

            val currentMinute = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                timePicker.minute
            }else{
                timePicker.currentMinute
            }

            timePickerDialog = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { timePicker, i, i1 ->
                receiveTime.summaryProvider = Preference.SummaryProvider<Preference> { preference -> "$i:$i1" }
                val editor1 = sharedPreferences.edit()
                editor1.putInt(resources.getString(R.string.receive_time_key),(i*60)+i1)
                editor1.apply()
            }, currentHour, currentMinute, true)
            timePickerDialog.show()
            true
        }
    }

    private fun aboutDialogShow() {
        val about : Preference? = findPreference(resources.getString(R.string.about_key))
        about?.setOnPreferenceClickListener {
            val about = context?.let { it1 -> AlertDialog.Builder(it1) }
            val view = layoutInflater.inflate(R.layout.about_dialog, null)
            about?.setView(view)
            val dialog = about?.create()
            dialog?.show()
            true
        }
    }
}