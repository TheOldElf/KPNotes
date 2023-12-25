package com.example.kpu.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.kpu.R
import com.google.firebase.database.*

class EditDeleteNoteActivity : AppCompatActivity() {

    private lateinit var editTextNote: EditText
    private lateinit var noteReference: DatabaseReference
    private lateinit var currentNoteId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_delete_note)

        editTextNote = findViewById(R.id.editTextNote)

        // Получаем ID текущей заметки из Intent
        currentNoteId = intent.getStringExtra("noteId") ?: ""
        noteReference = FirebaseDatabase.getInstance().getReference("notes").child(currentNoteId)

        // Заполняем поле редактирования текущим текстом заметки
        noteReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentNote = snapshot.getValue(String::class.java)
                editTextNote.setText(currentNote)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun updateNote(view: View) {
        val updatedNote = editTextNote.text.toString().trim()

        if (updatedNote.isNotEmpty()) {
            // Обновляем заметку в Firebase
            noteReference.setValue(updatedNote)

            Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Please enter a note", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteNote(view: View) {
        // Удаляем заметку из Firebase
        noteReference.removeValue()

        Toast.makeText(this, "Note deleted successfully", Toast.LENGTH_SHORT).show()
        finish()
    }
}

