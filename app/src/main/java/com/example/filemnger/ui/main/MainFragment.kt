package com.example.filemnger.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filemnger.R
import com.example.filemnger.filetypes.TypeFile
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
        viewModel = activity?.let { ViewModelProvider(it).get(MainViewModel::class.java) }!!

        val root: View = inflater.inflate(R.layout.fragment_main, container, false)
        val recyclerView: RecyclerView =
            root.findViewById<RecyclerView>(R.id.recyclerView_File)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val list: ArrayList<TypeFile> = ArrayList()

        viewModel.getPath().observe(viewLifecycleOwner,
            Observer<Any> { s ->
                var l: ArrayList<TypeFile> = ArrayList()
                updateList(s as String,l)
                viewModel.setList(l)
            })

        val path = Environment.getExternalStorageDirectory().path
        viewModel.setPath(path)

        viewModel.getList().observe(viewLifecycleOwner,
            Observer<List<Any?>?> { s ->
                adapterList = context?.let { FileAdapter(this,it, s as List<TypeFile>, viewModel) }!!
                recyclerView.adapter = adapterList
            })



       updateList(viewModel.getPath().value!!,list)

        return root
    }

    private fun updateList(path: String, list: ArrayList<TypeFile>){
        if (checkPermission()) {
            //permission allowed
            val filesAndFolders: Array<File>? = File(path).listFiles()
            if (filesAndFolders != null) {
                for (file in filesAndFolders) {
                    list.add(TypeFile(requireContext(),file))
                }
            }// null
            viewModel.setList(list)
        } else {
            //permission not allowed
            requestPermission()
        }
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