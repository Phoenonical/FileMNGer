package com.example.filemnger.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filemnger.filetypes.TypeFile

class MainViewModel : ViewModel() {

    private var list: MutableLiveData<List<TypeFile>> = MutableLiveData<List<TypeFile>>()
    private var path: MutableLiveData<String> = MutableLiveData()
    private var file: MutableLiveData<TypeFile> = MutableLiveData()

    private var openDetail: Boolean = false

    fun setSelected(f: TypeFile){
        file.value = f
        openDetail = true
    }

    fun getSelected(): MutableLiveData<TypeFile>{
        return file
    }

    fun isSelected(): Boolean{
        return openDetail
    }

    fun unselect(){
        openDetail = false
    }

    fun setPath(p: String){
        path.value = p
    }

    fun getPath(): MutableLiveData<String> {
        return path
    }

    fun getList(): MutableLiveData<List<TypeFile>> {
        return list;
    }
    fun setList(list: List<TypeFile>) {
        this.list.value = list
    }

    // TODO: Implement the ViewModel
}