package com.example.filemnger.ui.main

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filemnger.filetypes.TypeFile
import com.example.filemnger.R

class FileAdapter(
    context: Context?,
    Data: List<TypeFile>,
    model: MainViewModel
): RecyclerView.Adapter<FileAdapter.ViewHolder>()  {

    private var mData: List<TypeFile> = Data
    private var mInflater: LayoutInflater
    private var model: MainViewModel = model
    private var context: Context? = null

    init{
        mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = mInflater.inflate(R.layout.file_row, parent, false)
        return ViewHolder(view)
    }

    // Fill row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dato: TypeFile = mData[position]
        val pos = position
        holder.fName.text = "(" + dato.getfileName() + ")"

        //TODO("Implement later")
    }

    override fun getItemCount(): Int {
        if (mData == null)
            return 0
        Log.d("FILETAG", "Data size is != null")
        return mData.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fName: TextView
        var fType: TextView
        var fSize: TextView

        init {
            fName = itemView.findViewById(R.id.FileName)
            fType = itemView.findViewById(R.id.FileType)
            fSize = itemView.findViewById(R.id.FileInfo)
        }
    }



}