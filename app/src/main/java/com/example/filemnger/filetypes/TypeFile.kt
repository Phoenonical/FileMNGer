package com.example.filemnger.filetypes

import java.io.File

open class TypeFile(myF: File) {

    private val myFile: File = myF

    fun getfileName(): String{
        return myFile.name
    }

    fun getfileExtension(): String{
        return myFile.extension
    }

    /*fun getfileBG(): Int{
        when(getfileExtension())
            ".pdf" ->
    }*/




}