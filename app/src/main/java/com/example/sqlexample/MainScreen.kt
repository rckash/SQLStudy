package com.example.sqlexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlexample.databinding.ActivityMainScreenBinding
import com.example.sqlexample.databinding.DialogLayoutBinding

class MainScreen : AppCompatActivity() {
    //global
    private lateinit var binding: ActivityMainScreenBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var noteList: MutableList<Note>
    private lateinit var adapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //object instantiation
        databaseHelper = DatabaseHelper(this)

        //setup the recyclerview
        recyclerView = binding.recyclerview
        //add layout to recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        //declare data
        noteList = getData()

        //initialize adapter object
        adapter = NotesAdapter(noteList)
        //on delete click
        adapter.onDeleteClick = {note ->
            delete(note.id)
            noteList.remove(Note(note.id, note.title, note.content))
            recyclerView.adapter?.notifyItemRemoved(note.id)

//            Toast.makeText(applicationContext, "${note.id}", Toast.LENGTH_SHORT).show()
        }
        //on update click
        adapter.onUpdateClick = {note ->
            Toast.makeText(applicationContext, "${note.id}", Toast.LENGTH_SHORT).show()
        }

        recyclerView.adapter = adapter

        binding.floatingActionButton.setOnClickListener {
            showAddDialog()
        }

    }

    private fun showAddDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Add New Note")

        val dialogLayout = layoutInflater.inflate(R.layout.dialog_layout, null)
        val dialogBinding = DialogLayoutBinding.bind(dialogLayout)
        alertDialogBuilder.setView(dialogLayout)

        alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
            val title = dialogBinding.etDialogTitle.text.toString()
            val content = dialogBinding.etDialogContent.text.toString()

            var newNote = Note(0, title, content)
            //add new data to database table
            addData(newNote)
            //add new note to list
            noteList.add(newNote)
            //notify adapter that data has changed
            recyclerView.adapter?.notifyDataSetChanged()
            dialog.dismiss()
        }
        alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun getData(): MutableList<Note> {
        return databaseHelper.getAllNotes()
    }

    private fun addData(note: Note) {
        databaseHelper.insertNote(note)
        Toast.makeText(applicationContext, "Note Added", Toast.LENGTH_SHORT).show()
    }

    private fun update(id: Int, title: String, content: String) {
        val noteObject = Note(id, title, content)
        databaseHelper.updateData(noteObject)
        getData()
        Toast.makeText(applicationContext, "Note Updated", Toast.LENGTH_SHORT).show()
    }

    private fun delete(id: Int) {
        databaseHelper.deleteData(id)
        getData()
        Toast.makeText(applicationContext, "Note Deleted", Toast.LENGTH_SHORT).show()
    }
}