# Notes App

A simple Notes App built using Kotlin, XML, and SQLite for Android. This app allows users to create, view, update, and delete notes. It's a great example for anyone looking to learn about basic CRUD operations with SQLite in Android.

## Features

- **Create Notes**: Users can create new notes by entering a title and content.
- **View Notes**: All saved notes are displayed in a list format.
- **Update Notes**: Users can update the title or content of existing notes.
- **Delete Notes**: Unwanted notes can be deleted permanently.

## Project Structure

- **Kotlin**: The app is written in Kotlin, leveraging the language's features to create a clean and concise codebase.
- **XML**: The user interface is designed using XML layouts.
- **SQLite**: The app uses SQLite to manage data locally on the device.

## Setup Instructions

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/notes-app.git
   cd notes-app
   ```

2. **Open the Project in Android Studio**
   - Open Android Studio.
   - Select `Open an existing project` and navigate to the cloned directory.
   - Let Android Studio sync and build the project.

3. **Build and Run the App**
   - Connect an Android device or start an emulator.
   - Click on the "Run" button in Android Studio.

## How It Works

### 1. Creating a Note
- Navigate to the "Add Note" screen.
- Enter the title and content of the note.
- Tap "Save" to add the note to the list.

### 2. Viewing Notes
- The main screen displays a list of all saved notes.
- Each item shows the note's title and a preview of its content.

### 3. Updating a Note
- Tap on a note in the list to view its details.
- Click the "Edit" button to update the title or content.
- Tap "Save" to apply changes.

### 4. Deleting a Note
- Long press on a note in the list to select it.
- Tap the "Delete" button to remove it from the database.

## Code Snippets

### SQLite Database Helper

```kotlin
class NotesDatabaseHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null, DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_NAME = "notesapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allnotes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTable = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTable)
        onCreate(db)
    }

    // CRUD operations go here
}
```

### Insert Note

```kotlin
fun insertNote(note: DataNotes) {
    val db = writableDatabase
    val values = ContentValues().apply {
        put(COLUMN_TITLE, note.title)
        put(COLUMN_CONTENT, note.content)
    }
    db.insert(TABLE_NAME, null, values)
    db.close()
}
```

### Get Note by ID

```kotlin
fun getNoteById(noteId: Int): DataNotes {
    val db = readableDatabase
    val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId"
    val cursor = db.rawQuery(query, null)
    cursor.moveToFirst()

    val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
    val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
    val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

    cursor.close()
    db.close()

    return DataNotes(id, title, content)
}
```

### Update Note

```kotlin
fun updateNote(note: DataNotes) {
    val db = writableDatabase
    val values = ContentValues().apply {
        put(COLUMN_TITLE, note.title)
        put(COLUMN_CONTENT, note.content)
    }
    val whereClause = "$COLUMN_ID = ?"
    val whereArgs = arrayOf(note.id.toString())
    db.update(TABLE_NAME, values, whereClause, whereArgs)
    db.close()
}
```

### Delete Note

```kotlin
fun deleteNoteById(noteId: Int) {
    val db = writableDatabase
    val whereClause = "$COLUMN_ID = ?"
    val whereArgs = arrayOf(noteId.toString())
    db.delete(TABLE_NAME, whereClause, whereArgs)
    db.close()
}
```

## Contributing

Contributions are welcome! If you have ideas for improvements or new features, feel free to fork the repository and submit a pull request.

## License

This project is licensed under the MIT License. See the `LICENSE` file for more details.

---

Feel free to customize this template with your project's specific details, including screenshots and any additional sections you think are necessary.
