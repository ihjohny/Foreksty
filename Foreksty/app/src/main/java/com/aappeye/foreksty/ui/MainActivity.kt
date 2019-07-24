package com.aappeye.foreksty.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.aappeye.foreksty.R
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import android.view.MenuItem
import com.aappeye.foreksty.ui.settings.Settings


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navController = findNavController(R.id.nav_host_fragment)
        bottom_nav.setupWithNavController(navController)
       // NavigationUI.setupActionBarWithNavController(this,navController)

        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()?.setHomeButtonEnabled(true);
        getSupportActionBar()?.setHomeAsUpIndicator(R.drawable.menu_gray_60_24dp)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.toolbar, menu)
        return true
    }

/*

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }
*/


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
