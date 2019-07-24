package com.aappeye.foreksty.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.aappeye.foreksty.R
import kotlinx.android.synthetic.main.activity_settings.*
import android.widget.TimePicker
import android.app.TimePickerDialog
import android.os.Build
import androidx.preference.PreferenceManager

class Settings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(settings_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.settings, rootKey)
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val receiveTimeStored = sharedPreferences.getInt(resources.getString(R.string.receive_time_key),0)
            val receiveTimeStoredText = "${receiveTimeStored/60}:${receiveTimeStored%60}"
            val receiveTime : Preference? = findPreference(resources.getString(R.string.receive_time_key))
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
}
