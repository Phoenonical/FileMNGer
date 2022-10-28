package com.example.filemnger.ui.main

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.filemnger.R
import com.example.filemnger.filetypes.TypeFile


class FileAdapter(
    fragment: Fragment,
    context: Context,
    Data: List<TypeFile>,
    model: MainViewModel
): RecyclerView.Adapter<FileAdapter.ViewHolder>()  {

    private var mfragment: Fragment = fragment
    private var mData: List<TypeFile> = Data
    private var mInflater: LayoutInflater
    private var model: MainViewModel = model
    private var context: Context = context
    private var selected: Boolean = false

    init{
        mInflater = LayoutInflater.from(context)
        mData = mData.filter { !it.getfileName().get(0).equals('.') }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = mInflater.inflate(R.layout.file_row, parent, false)
        return ViewHolder(view)
    }

    // Fill row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dato: TypeFile = mData[position]
        var fileSize: Long = dato.getfileSize()
        var sizeUnit: String = "Bytes"

        holder.fName.text = dato.getfileName()
        holder.fType.background = dato.getfileIcon()
        holder.fBG.setBackgroundColor(dato.getBGColor())
        if (fileSize>=1024){
            fileSize /= 1024
            sizeUnit="KB"
            if (fileSize>=1024){
                fileSize /= 1024
                sizeUnit="MB"
                if (fileSize>=1024){
                    fileSize /= 1024
                    sizeUnit="GB"
                    if (fileSize>=1024){
                        fileSize /= 1024
                        sizeUnit="TB"

                    }
                }
            }
        }
        holder.fDate.text = "Last modified: ${dato.getlastModifiedDate().dayOfMonth().get()}/${dato.getlastModifiedDate().monthOfYear().get()}/${dato.getlastModifiedDate().year().get()}"
        holder.fSize.text = "Size: $fileSize$sizeUnit"
        //holder.fType.setBackground()

        holder.itemView.setOnClickListener{
            //verifyStoragePermission(context as Activity);

            if(dato.isFolder()){
                model.setPath(model.getPath().value + "/" + dato.getfileName())
            } else if(dato.getfileType().equals("PDF")){

                openpdf(dato)


                /*var intent = Intent(Intent.ACTION_VIEW)
                intent.setDataAndType(Uri.fromFile(dato.getFile()), "application/pdf")
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                startActivity(context,intent,null)*/
            }else if (dato.getfileType().equals("TXT")){
                // If session is not empty, go to home page
                model.setSelected(dato)
                NavHostFragment.findNavController(mfragment)
                    .navigate(R.id.action_nav_files_to_detailFragment)
            }
            else Toast.makeText(context, "Clicked on: ${dato.getfileExtension()}", Toast.LENGTH_SHORT).show()
        }

        holder.itemView.setOnLongClickListener{
          Toast.makeText(context, "Longed Clicked on: ${dato.getfileName()}", Toast.LENGTH_SHORT).show()
            dato.setSelected(!dato.getSelected())
            if(dato.getSelected())
                holder.rowBG.setBackgroundColor(ContextCompat.getColor(context,R.color.Row_background_selected))
            else
                holder.rowBG.setBackgroundColor(ContextCompat.getColor(context,R.color.Row_background))
            true
        }

    }

 /*   fun verifyStoragePermission(activity: Activity?) {
        val permission = ActivityCompat.checkSelfPermission(activity!!, WRITE_EXTERNAL_STORAGE)

        // Surrounded with if statement for Android R to get access of complete file.
        if (SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager() && permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    activity,
                    PERMISSION_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
                )
            }
        }
    }*/

    private fun openpdf(f: TypeFile){

        // Get the File location and file name.
        val file = f.getFile()
        Log.d("pdfFIle", "" + file)

        // Get the URI Path of file.
        val uriPdfPath = FileProvider.getUriForFile(
            context,
            (context as Activity).getPackageName() + ".provider",
            file
        )
        Log.d("pdfPath", "" + uriPdfPath)

        // Start Intent to View PDF from the Installed Applications.
        val pdfOpenIntent = Intent(Intent.ACTION_VIEW)
        pdfOpenIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        pdfOpenIntent.clipData = ClipData.newRawUri("", uriPdfPath)
        pdfOpenIntent.setDataAndType(uriPdfPath, "application/pdf")
        //pdfOpenIntent = Intent.createChooser(pdfOpenIntent, "Open File");
        pdfOpenIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

        try {
            startActivity(context, pdfOpenIntent, null)
        } catch (activityNotFoundException: ActivityNotFoundException) {
            Toast.makeText(context, "There is no app to load corresponding PDF", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fName: TextView
        var fType: ImageView
        var fSize: TextView
        var fDate: TextView
        var fBG: ConstraintLayout
        var rowBG: ConstraintLayout

        init {
            fName = itemView.findViewById(R.id.FileName)
            fType = itemView.findViewById(R.id.FileType)
            fSize = itemView.findViewById(R.id.FileSize)
            fDate = itemView.findViewById(R.id.FileDate)
            fBG = itemView.findViewById(R.id.FileTypeBG)
            rowBG = itemView.findViewById(R.id.Row)
        }
    }



}