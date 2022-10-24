package com.example.filemnger

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.filemnger.ui.main.MainFragment
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {

    private var mAppBarConfiguration: AppBarConfiguration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
        if (checkPermission()) {
            //permission allowed
            val path = Environment.getExternalStorageDirectory().path + "/Download"
            val filesAndFolders: Array<File>? = File(path).listFiles()
            Log.d("FILETAG", path) // /storage/emulated/0
            Log.d("FILETAG", filesAndFolders.toString()) // null
            if (filesAndFolders != null) {
                for (file in filesAndFolders)
                    Log.d("FILETAG", file.name)
            }// null
            Log.d("FILETAG", File(path).exists().toString()) // true
            Log.d("FILETAG", File(path).canRead().toString()) // false
        } else {
            //permission not allowed
            requestPermission()
        }
        */

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        var navigationView = findViewById<NavigationView>(R.id.nav_view)

        val header: View = navigationView.getHeaderView(0)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = AppBarConfiguration.Builder(
            R.id.nav_home, R.id.nav_Downloads, R.id.nav_Documents
        )
            .setDrawerLayout(drawer)
            .build()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_login -> {
                Log.d("DEBUG", "LOGGED OUT")
                com.example.ium_reservation.MainActivity.Logout(model, this).execute()
                model.setSession("")
                val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
                (NavigationUI.onNavDestinationSelected(item, navController)
                        || super.onOptionsItemSelected(item))
            }
            else ->                 // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                super.onOptionsItemSelected(item)
        }
    }*/

    /*override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        return (NavigationUI.navigateUp(navController, mAppBarConfiguration!!)
                || super.onSupportNavigateUp())
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