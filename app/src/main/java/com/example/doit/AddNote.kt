package com.example.doit

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.example.doit.Models.Note
import com.example.doit.databinding.ActivityAddNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class AddNote : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding

    private lateinit var note: Note
    private lateinit var old_note: Note
    var isUpdate = false
    var selectedImage = "";
    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        try {
            val oldNote = null
            if(intent?.getSerializableExtra("current_note") != null) {
                old_note = intent?.getSerializableExtra("current_note") as Note
            }

            if(old_note != null) {
                binding.etTitle.setText(old_note.title)
                binding.etNote.setText(old_note.note)
                binding.etDate.setText(old_note.date)
                binding.etNote.isEnabled = false
                binding.etNote.isFocusable = false
                binding.etNote.isFocusableInTouchMode = false
                binding.etTitle.isEnabled = false
                binding.etTitle.isFocusable = false
                binding.etTitle.isFocusableInTouchMode = false
            }

            isUpdate = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.editButton.setOnClickListener {
            binding.etNote.isEnabled = true
            binding.etNote.isFocusable = true
            binding.etNote.isFocusableInTouchMode = true
            binding.etTitle.isEnabled = true
            binding.etTitle.isFocusable = true
            binding.etTitle.isFocusableInTouchMode = true
        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val selectedOption = findViewById<RadioButton>(checkedId)
            selectedImage = selectedOption.text as String
        }

        binding.imgCheck.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val note_desc = binding.etNote.text.toString()

            if (title.isNotEmpty() || note_desc.isNotEmpty()) {
                if (isUpdate) {
                    note = Note(
                        old_note.id, title, note_desc, selectedImage)

                } else {
                    note = Note(null, title, note_desc, selectedImage)
                }

                val intent = Intent()
                intent.putExtra("note", note)
                setResult(Activity.RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this@AddNote, "Please enter some data", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun onShareButtonClick(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        val text = "Tytuł: " + binding.etTitle.text.toString() + "   Notatka: " +  binding.etNote.text.toString()
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(intent, "Udostępnij za pomocą"))
    }
}