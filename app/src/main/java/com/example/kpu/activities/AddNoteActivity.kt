package com.example.kpu.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.kpu.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddNoteActivity : AppCompatActivity() {

    private lateinit var editTextNote: EditText
    private lateinit var notesReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        editTextNote = findViewById(R.id.editTextNote)
        notesReference = FirebaseDatabase.getInstance().getReference("notes")
    }

    fun addNoteClicked(view: View) {
        val newNote = editTextNote.text.toString().trim()

        if (newNote.isNotEmpty()) {
            // Добавляем новую заметку в Firebase
            val noteId = notesReference.push().key!!
            notesReference.child(noteId).setValue(newNote)

            Toast.makeText(this, "Note added successfully", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Please enter a note", Toast.LENGTH_SHORT).show()
        }
    }
}

