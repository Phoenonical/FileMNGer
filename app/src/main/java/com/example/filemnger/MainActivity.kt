package com.example.filemnger

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.filemnger.ui.main.MainViewModel
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var mAppBarConfiguration: AppBarConfiguration? = null
    private lateinit var drawer: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var fileModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fileModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setContentView(R.layout.activity_main)

        if (checkPermission()) {
            //permission allowed
            Log.d("FILETAG", "allowed main activity") // /storage/emulated/0
        } else {
            //permission not allowed
            requestPermission()
        }


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar

        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24)

        drawer = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val header: View = navigationView.getHeaderView(0)
        val filePath: TextView = header.findViewById(R.id.FilePath)

        fileModel.getPath().observe(this,
            Observer<Any> { s ->
                val value: String = s.toString()
                filePath.text = value
                if(value.equals(Environment.getExternalStorageDirectory().path))
                    actionBar?.title = getString(R.string.app_name)
                else
                    actionBar?.title = value.substring(value.lastIndexOf("/",value.length)+1,value.length)
            })

       /* val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val navController = navHostFragment?.childFragmentManager?.getFragment().
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration!!)
        NavigationUI.setupWithNavController(navigationView, navController)*/

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*mAppBarConfiguration = AppBarConfiguration.Builder(
            R.id.nav_home, R.id.nav_Downloads, R.id.nav_Documents
        )
            .setDrawerLayout(drawer)
            .build()*/
        //setNavigationViewListener()
        /*if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }*/
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawer.openDrawer(GravityCompat.START)
                return true
            }
        }

        Log.d("FILETAG", item.toString())

        return super.onOptionsItemSelected(item)
    }

    /*override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        return (NavigationUI.navigateUp(navController, mAppBarConfiguration!!)
                || super.onSupportNavigateUp())
    }*/

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_Downloads -> {
                Log.d("FILETAG", "CLICKED ON DOWNLOADS")
                fileModel.setPath(Environment.getExternalStorageDirectory().path + "/Download")
            }
            R.id.nav_Images -> {
                Log.d("FILETAG", "CLICKED ON Images")
                fileModel.setPath(Environment.getExternalStorageDirectory().path + "/Pictures")
            }
            R.id.nav_home -> {
                Log.d("FILETAG", "CLICKED ON Home")
                fileModel.setPath(Environment.getExternalStorageDirectory().path)
            }
        }
        fileModel.getPath().value?.let { Log.d("FILETAG", it) }
        //close navigation drawer
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        val path: String? = fileModel.getPath().value
        if (fileModel.isSelected()) {
            fileModel.unselect()
            super.onBackPressed()
        }else if (this.drawer.isDrawerOpen(GravityCompat.START)) {
            this.drawer.closeDrawer(GravityCompat.START)
        } else if(!path.equals(Environment.getExternalStorageDirectory().path)) {
            fileModel.setPath(path!!.substring(0,path.lastIndexOf("/",path.length)))
        } else super.onBackPressed()
    }

    /*private fun setNavigationViewListener() {
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
    }*/

    private fun checkPermission(): Boolean {
        val result =
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(this, "Storage permission is required", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                111
            )
        }
    }


}