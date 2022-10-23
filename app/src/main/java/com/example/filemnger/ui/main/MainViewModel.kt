package com.example.filemnger.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filemnger.filetypes.TypeFile

class MainViewModel : ViewModel() {

    private var list: MutableLiveData<List<TypeFile>> = MutableLiveData<List<TypeFile>>()

    fun getList(): MutableLiveData<List<TypeFile>> {
        return list;
    }
    fun setList(list: List<TypeFile>) {
        this.list.value = list
    }

    // TODO: Implement the ViewModel
}