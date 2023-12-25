package com.example.kpu.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import com.example.kpu.adapters.Note
import com.example.kpu.modules.NoteAdapter
import com.example.kpu.R
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var listViewNotes: ListView
    private lateinit var notesReference: DatabaseReference
    private lateinit var notesList: MutableList<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listViewNotes = findViewById(R.id.listViewNotes)

        notesReference = FirebaseDatabase.getInstance().getReference("notes")
        notesList = mutableListOf()

        // Отслеживаем изменения в Firebase и обновляем список
        notesReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                notesList.clear()

                for (noteSnapshot in snapshot.children) {
                    val noteId = noteSnapshot.key.toString()
                    val noteText = noteSnapshot.getValue(String::class.java).toString()
                    notesList.add(Note(noteId, noteText))
                }

                // Создаем адаптер для списка
                val adapter = NoteAdapter(this@MainActivity, notesList)
                listViewNotes.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Обработка ошибок, если необходимо
            }
        })

        // Обработчик клика по заметке в списке
        listViewNotes.setOnItemClickListener { _, _, position, _ ->
            val selectedNote = notesList[position]
            val intent = Intent(this@MainActivity, EditDeleteNoteActivity::class.java)
            intent.putExtra("noteId", selectedNote.id)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_note -> {
                val intent = Intent(this, AddNoteActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}



