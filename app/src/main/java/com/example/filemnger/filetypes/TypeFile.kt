package com.example.filemnger.filetypes

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.filemnger.R
import org.joda.time.DateTime
import java.io.File
import java.util.Date

class TypeFile(context: Context, myF: File) {

    private val myFile: File = myF
    private val context: Context = context
    private var selected: Boolean = false

    private var type: String

    init {
        type = if (myFile.isDirectory)
            "DIR"
        else if (myFile.extension.equals("pdf"))
            "PDF"
        else if (myFile.extension.equals("docx") || myFile.extension.equals("doc"))
            "DOC"
        else if (myFile.extension.equals("mp4"))
            "MED"
        else if (myFile.extension.equals("txt"))
            "TXT"
        else if(myFile.extension.equals("png") || myFile.extension.equals("jpg") || myFile.extension.equals("jpeg"))
            "IMG"
        else
            "ETC"
    }

    fun getFile(): File{
        return myFile
    }

    fun getfileName(): String{
        return myFile.name
    }

    fun getfileExtension(): String{
        return myFile.extension
    }

    fun isFolder(): Boolean{
        return myFile.isDirectory
    }

    fun getfileType(): String{
        return type
    }

    fun getBGColor(): Int{
        return when(type){
            "DIR" -> {ContextCompat.getColor(context,R.color.DIR)}
            "PDF" -> {ContextCompat.getColor(context,R.color.PDF)}
            "DOC" -> {ContextCompat.getColor(context,R.color.DOC)}
            "MED" -> {ContextCompat.getColor(context,R.color.MED)}
            "TXT" -> {ContextCompat.getColor(context,R.color.TXT)}
            "IMG" -> {ContextCompat.getColor(context,R.color.IMG)}
            else -> {ContextCompat.getColor(context,R.color.ETC)}
        }
    }

    fun getfileIcon(): Drawable? {
        return when(type){
            "DIR" -> {ContextCompat.getDrawable(context,R.drawable.ic_baseline_folder_24)}
            "PDF" -> {ContextCompat.getDrawable(context,R.drawable.my_pdf_24)}
            "DOC" -> {ContextCompat.getDrawable(context,R.drawable.my_doc_24)}
            "MED" -> {ContextCompat.getDrawable(context,R.drawable.my_media_24_white)}
            "TXT" -> {ContextCompat.getDrawable(context,R.drawable.my_txt_24)}
            "IMG" -> {ContextCompat.getDrawable(context,R.drawable.ic_baseline_image_24_white)}
            else -> {ContextCompat.getDrawable(context,R.drawable.ic_baseline_folder_24_white)}
        }
    }

    fun getfileSize(): Long{
        return myFile.length()
    }

    fun getlastModifiedDate(): DateTime{
        return DateTime(myFile.lastModified())
    }

    fun getSelected(): Boolean{
        return selected
    }

    fun setSelected(value: Boolean){
        selected = value
    }




}