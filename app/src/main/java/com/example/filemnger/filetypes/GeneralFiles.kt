package com.example.filemnger.filetypes

import java.io.File

class GeneralFiles(myF: File): TypeFile(myF) {

    override var myFile: File = myF

    override fun onClick() {
        TODO("Not yet implemented")
    }

}