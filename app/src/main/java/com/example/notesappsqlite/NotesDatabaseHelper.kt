package com.example.notesappsqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NotesDatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null,
    DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_NAME = "notesapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allnotes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"

    }

    //this is implemented to create table
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY , $COLUMN_TITLE TEXT ,$COLUMN_CONTENT TEXT)"
        db?.execSQL(createTable)


    }

    //to drop a table if a similar name table exists
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTable = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTable)
        onCreate(db)
    }

    fun insertNotes(notes: DataNotes) {
        val db = writableDatabase                                  //the database is writable allows to insert , update and delete data
        val value = ContentValues().apply {       // ContentValues store key value pairs
            put(COLUMN_TITLE, notes.title)                        // for understanding  => put("title" , notes.title)
            put(COLUMN_CONTENT, notes.content)                     //add content
        }
        db.insert(TABLE_NAME, null, value)          // add a new row in table
        db.close()                                                // close database
    }

    fun getAllNotes ():List<DataNotes> {           //this is to show the data
        val notesList = mutableListOf<DataNotes>()                //flexible database


        val db = readableDatabase                                 //we need to read the database only
        val query ="SELECT*FROM $TABLE_NAME"

        val cursor = db.rawQuery(query,null)          // used to iterate through  table A.K.A  val cursor = readableDatabase.rawQuery("SELECT * FROM $TABLE_NAME",null)

        while (cursor.moveToNext()){                             //cursor.moveToNext() is actually instructs to move the cursor to next row
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))                  //id retrieval
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))          //title retrieval
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))        //content retrieval

            val notes = DataNotes(id,title,content)              // notes store the data as DataNotes Format
            notesList.add(notes)                                 //we have passed the notes in notesList
        }
        cursor.close()
        db.close()
        return notesList
    }

    fun UpdateNotes(note:DataNotes){
        val db = writableDatabase
        val values = ContentValues().apply {   //contentValue class is used to store columns that are to be inserted or updated
            put(COLUMN_TITLE,note.title)
            put(COLUMN_CONTENT,note.content)
        }
        val whereClause ="$COLUMN_ID = ?"        //identify the rows which is to be updated
        val whereArgs = arrayOf(note.id.toString())  //  It contains the id of the note as a string, which identifies the specific note to be updated.
                                                    //whereArgs is the actual value of the "?" in whereClause
        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()
    }

    //we need to know which note was clicked so for that we have to create a function GetNoteById , and accordingly we will display title and content
    fun GetNoteById( noteId:Int):DataNotes{
        val db = readableDatabase                                             //identify that the database is readable
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID =$noteId"     // its a sql query , which select the row from the table who's column id will match provided notes id

        val cursor =db.rawQuery(query,null)                       // val cursor is used to execute the query using rawQuery method
        cursor.moveToFirst()                                                 // will move the cursor to the first row of the table i.e. it will move to the row which matches the column id and notes id

        val id =cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))               //retrieval of the data
        val title =cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content =cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return DataNotes(id,title,content)                                    // will return the id , title and content
    }

    fun deleteNotes(noteId :Int){
        val db = writableDatabase
        val WhereClause = "$COLUMN_ID = ?"
        val WhereArgs = arrayOf(noteId.toString())

        db.delete(TABLE_NAME,WhereClause,WhereArgs)
        db.close()

    }
}