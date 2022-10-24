package com.example.filemnger.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filemnger.R
import com.example.filemnger.filetypes.TypeFile
import com.example.filemnger.filetypes.GeneralFiles
import java.io.File


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var adapterList: FileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val root: View = inflater.inflate(R.layout.fragment_main, container, false)
        val recyclerView: RecyclerView =
            root.findViewById<RecyclerView>(R.id.recyclerView_File)
        val PathView: TextView = root.findViewById(R.id.FilePath)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val list: ArrayList<TypeFile> = ArrayList()
        val path = Environment.getExternalStorageDirectory().path
        viewModel.getList().observe(viewLifecycleOwner,
            Observer<List<Any?>?> { s ->
                adapterList = context?.let { FileAdapter(it, s as List<TypeFile>, viewModel) }!!
                recyclerView.adapter = adapterList
                PathView.text = path
            })


        if (checkPermission()) {
            //permission allowed
            val filesAndFolders: Array<File>? = File(path).listFiles()
            Log.d("FILETAG", path) // /storage/emulated/0
            Log.d("FILETAG", filesAndFolders.toString()) // null
            if (filesAndFolders != null) {
                for (file in filesAndFolders) {
                    Log.d("FILETAG", file.name)
                    list.add(GeneralFiles(file))
                    Log.d("FILETAG", list.size.toString()) // null
                }
            }// null
            viewModel.setList(list)
            Log.d("FILETAG", File(path).exists().toString()) // true
            Log.d("FILETAG", File(path).canRead().toString()) // false

        } else {
            //permission not allowed
            requestPermission()
        }

        return root
    }

    private fun checkPermission(): Boolean {
        val result = context?.let {
            ContextCompat.checkSelfPermission(
                it,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(){
        if(shouldShowRequestPermissionRationale(context as Activity, android.Manifest.permission.READ_EXTERNAL_STORAGE)){
            Toast.makeText(context, "Storage permission is required", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                138
            )
        }
    }

}