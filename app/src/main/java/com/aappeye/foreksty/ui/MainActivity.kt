package com.aappeye.foreksty.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.aappeye.foreksty.R
import com.aappeye.foreksty.di.component.AppComponent
import com.aappeye.foreksty.di.component.DaggerAppComponent
import com.aappeye.foreksty.ui.settings.Settings
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.android.DaggerActivity
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

private const val  MY_PERMISSION_ACCESS_COARSE_LOCATION = 1

class MainActivity : DaggerAppCompatActivity() {

    @Inject lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val locationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult?) {
            super.onLocationResult(p0)
        }
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

       // fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        navController = findNavController(R.id.nav_host_fragment)
        bottom_nav.setupWithNavController(navController)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setHomeButtonEnabled(true)
        getSupportActionBar()?.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)

        requestLocationPermission()
        if(hasLocaitonPermission()){
            bindLocationManager()
        }
        else {
            requestLocationPermission()
        }
    }

    private fun bindLocationManager() {
        LifecycleBoundLocationManager(
            this,
            fusedLocationProviderClient,
            locationCallback
        )
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
            MY_PERMISSION_ACCESS_COARSE_LOCATION
        )
    }

    private fun hasLocaitonPermission(): Boolean {
        return ContextCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {
        if(requestCode == MY_PERMISSION_ACCESS_COARSE_LOCATION){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                bindLocationManager()
            }
            else{
                Toast.makeText(this,"Please, set location manually in settings",Toast.LENGTH_SHORT).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.getItemId()

        if (id == android.R.id.home) {
            val intent = Intent(this@MainActivity, Settings::class.java)
            startActivity(intent)
            return true
        }
        else if(id == R.id.refresh){
            Toast.makeText(this,"Refresh Clicked",Toast.LENGTH_SHORT).show()
        }
        else if(id == R.id.location){
            Toast.makeText(this,"Location Clicked",Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }

}
