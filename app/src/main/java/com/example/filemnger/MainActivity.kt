package com.example.filemnger

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.filemnger.ui.main.MainFragment
import java.io.File


class MainActivity : AppCompatActivity() {

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

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
    }

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