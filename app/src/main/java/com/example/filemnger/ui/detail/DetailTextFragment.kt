package com.example.filemnger.ui.detail

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.filemnger.R
import com.example.filemnger.filetypes.TypeFile
import com.example.filemnger.ui.main.MainViewModel
import java.io.IOException

class DetailTextFragment : Fragment() {

    companion object {
        fun newInstance() = DetailTextFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var textdisplay: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // TODO: Use the ViewModel 
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_text_detail, container, false)
        //viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel = activity?.let { ViewModelProvider(it).get(MainViewModel::class.java) }!!
        textdisplay = root.findViewById(R.id.TextDisplay)

        viewModel.getSelected().observe(viewLifecycleOwner,
            Observer<Any> { s ->
                var type = s as TypeFile
                var text: StringBuilder = StringBuilder()
                try {
                    var line: String = ""
                    val br = s.getFile().bufferedReader().readLines()
                    for (line in br) {
                        text.append(line)
                        text.append('\n')
                    }
                } catch (e: IOException) {
                    Toast.makeText(context, "Failed to read file ${s.getfileName()}", Toast.LENGTH_SHORT).show()
                }

                textdisplay.text = SpannableStringBuilder(text)
            })

        return root
    }

}