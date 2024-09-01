package com.example.notesappsqlite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private var notes: List<DataNotes>, context: Context) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {
        private val db :NotesDatabaseHelper=NotesDatabaseHelper(context)
    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
                val itemTVTitle = itemView.findViewById<TextView>(R.id.title_tv_rv)
                val itemTVContent = itemView.findViewById<TextView>(R.id.content_tv_rv)
                val updateButton = itemView.findViewById<ImageView>(R.id.updateButton)
                val deleteButton = itemView.findViewById<ImageView>(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {            //we setup the item layout
           val view = LayoutInflater.from(parent.context).inflate(R.layout.each_item,parent,false)
            return NotesViewHolder(view)
    }

    override fun getItemCount(): Int =notes.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {  //(holder :setData, position :determine which item was clicked ) //we setup data on element
        val notes = notes[position]
        holder.itemTVTitle.text=notes.title
        holder.itemTVContent.text=notes.content

        holder.updateButton.setOnClickListener {
                    val intent = Intent(holder.itemView.context,UpdateNotesActivity::class.java).apply {
                        putExtra("note_id",notes.id)                   // used to put extra data with intent  logic when we click on a card to edit the intent will take us tu update activity but the data is present in database to access the data we need primary key and primary key is Column id
                    }
            holder.itemView.context.startActivity(intent)
        }
        holder.deleteButton.setOnClickListener{
            db.deleteNotes(notes.id)
            refreshData(db.getAllNotes())
            Toast.makeText(holder.itemView.context,"Note Deleted",Toast.LENGTH_SHORT).show()
        }
    }
    fun refreshData (newNotes :List<DataNotes>){
        notes = newNotes
        notifyDataSetChanged()
    }
}