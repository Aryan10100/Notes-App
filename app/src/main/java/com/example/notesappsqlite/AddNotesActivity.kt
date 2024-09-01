package com.example.notesappsqlite

import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notesappsqlite.databinding.ActivityAddNotesBinding

class AddNotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNotesBinding
    private lateinit var db: NotesDatabaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)      //initializing the Notes DataBaseHelper

        binding.saveButton.setOnClickListener {
            val title =binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()
            val notes =DataNotes(0,title,content)
            db.insertNotes(notes)
            finish()                        //when activity is closed it go to main activity
            Toast.makeText(this,"Note Saved",Toast.LENGTH_SHORT).show()
        }


    }
}