package com.aappeye.foreksty.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.aappeye.foreksty.R
import kotlinx.android.synthetic.main.activity_settings.*

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

            val receiveTime : Preference? = findPreference(resources.getString(R.string.receive_time_key))
            receiveTime?.setOnPreferenceClickListener {
                Toast.makeText(context,"Receive Time Clicked", Toast.LENGTH_SHORT).show()
                true
            }

            val about : Preference? = findPreference(resources.getString(R.string.about_key))
            about?.setOnPreferenceClickListener {
                Toast.makeText(context,"About Clicked", Toast.LENGTH_SHORT).show()
                true
            }

        }
    }
}
