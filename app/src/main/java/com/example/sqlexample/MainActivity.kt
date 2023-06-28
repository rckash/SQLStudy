package com.example.sqlexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sqlexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        viewData()

        binding.button.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val content = binding.etContent.text.toString()
            addData(title, content)
        }

    }
    private fun viewData() {
        var allData = databaseHelper.getAllNotes()

        var resultString = ""

        for(note in allData) {
            resultString += "Note: ${note.title} ${note.content}\n"
        }

        binding.txtResult.text = resultString
    }

    private fun addData(title: String, content: String) {
        val sampleNote = Note(0, title, content)
        databaseHelper.insertNote(sampleNote)
        viewData()
    }
}