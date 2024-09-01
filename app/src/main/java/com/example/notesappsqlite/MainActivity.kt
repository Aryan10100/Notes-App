package com.example.notesappsqlite

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesappsqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding :ActivityMainBinding
    private lateinit var db: NotesDatabaseHelper
    private lateinit var notesAdapter: NotesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fabButton.setOnClickListener{
            startActivity(Intent(this,AddNotesActivity::class.java))
        }
        db =NotesDatabaseHelper(this)          //initializing notes database helper
        notesAdapter=NotesAdapter(db.getAllNotes(),this)

        binding.recyclerView.layoutManager =LinearLayoutManager(this)
        binding.recyclerView.adapter= notesAdapter
    }

    override fun onResume() {
        super.onResume()
        notesAdapter.refreshData(db.getAllNotes())          //automatically refreshes the data
    }
}