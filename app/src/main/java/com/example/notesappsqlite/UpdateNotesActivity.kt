package com.example.notesappsqlite

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notesappsqlite.databinding.ActivityUpdateNoteBinding

class UpdateNotesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db: NotesDatabaseHelper
    private var noteId: Int = -1                                           //-1 will help us to deal with null values
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)
        noteId =intent.getIntExtra("note_id",-1)         // through intent we have sent id from adapter to updateNoteActivity(this activity ) so , we will receive it here
          if (noteId ==-1){                                               //note_id will act as key, so pass the same name , default value :if due an issue id is not able to pass then set default value will be -1
              finish()                                                    // if the default id is -1 i.e. is no value is received then exit the activity and
              return
          }
           // we need to know which note was clicked so according to it we will display title and content
        val note = db.GetNoteById(noteId)
        binding.EditTitleText.setText(note.title)                       // through note we will goto to GetNoteById Function and inside it we had already fetched the  data title and content , so through this we will set the set the title and data within it fields
        binding.contentEditText.setText(note.content)

        binding.UpdateSaveButton.setOnClickListener{           // when user had update the data the data will be stored in the same edit text but with the different content so save the contents in the new variable
            val newTitle =binding.EditTitleText.text.toString()
            val newContent = binding.contentEditText.text.toString()
            val updateContent = DataNotes(noteId,newTitle,newContent)       // everything will be passed as an argument in the data class

            db.UpdateNotes(updateContent)                                 // use updateNotes function  in the helper to update the database
            finish()                                                      //close the activity when button is clicked
            Toast.makeText(this,"Changes Saved ",Toast.LENGTH_SHORT).show()
        }
    }
}