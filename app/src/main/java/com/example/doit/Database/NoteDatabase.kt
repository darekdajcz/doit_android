package com.example.doit.Database

import androidx.room.Database
import com.example.doit.Models.Note

@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
abstract class NoteDatabase {
}