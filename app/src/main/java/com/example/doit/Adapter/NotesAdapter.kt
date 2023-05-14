package com.example.doit.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.doit.Models.Note
import com.example.doit.R
import java.util.*
import kotlin.collections.ArrayList

class NotesAdapter(private val context: Context, val listner: NotesClickListner) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
    private val NotesList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return NotesList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Note>) {
        fullList.clear()
        fullList.addAll(newList)
        NotesList.clear()
        NotesList.addAll(fullList)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(search: String) {
        NotesList.clear()
        for (item in fullList) {
            if (item.title?.lowercase()?.contains(search.lowercase()) == true ||
                item.note?.lowercase()?.contains(search.lowercase()) == true
            ) {
                NotesList.add(item)
            }
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = NotesList[position]
        holder.title.text = currentNote.title
        holder.title.isSelected = true

        holder.note.text = currentNote.note
        holder.date.text = currentNote.date
        holder.date.isSelected = true

        if(holder.date.text == "android") {
            holder.androidX.visibility = View.VISIBLE
            holder.clockX.visibility = View.GONE
            holder.arrowX.visibility = View.GONE
            holder.starX.visibility = View.GONE
        }

        if(holder.date.text == "arrow") {
            holder.arrowX.visibility = View.VISIBLE
            holder.clockX.visibility = View.GONE
            holder.androidX.visibility = View.GONE
            holder.starX.visibility = View.GONE
        }

        if(holder.date.text == "star") {
            holder.arrowX.visibility = View.GONE
            holder.clockX.visibility = View.GONE
            holder.androidX.visibility = View.GONE
            holder.starX.visibility = View.VISIBLE
        }

        if(holder.date.text == "clock") {
            holder.arrowX.visibility = View.GONE
            holder.androidX.visibility = View.GONE
            holder.starX.visibility = View.GONE
            holder.clockX.visibility = View.VISIBLE
        }

        holder.notes_layout.setCardBackgroundColor(
            holder.itemView.resources.getColor(
                randomColor(),
                null
            )
        )

        holder.notes_layout.setOnClickListener {
            listner.onItemClicked(NotesList[holder.adapterPosition])
        }

        holder.notes_layout.setOnLongClickListener {
            listner.onLongItemClicked(NotesList[holder.adapterPosition], holder.notes_layout)
            true
        }
    }

    fun randomColor(): Int {
        val list = ArrayList<Int>()
        list.add(R.color.NoteColor1)
        list.add(R.color.NoteColor2)
        list.add(R.color.NoteColor3)
        list.add(R.color.NoteColor4)
        list.add(R.color.NoteColor5)
        list.add(R.color.NoteColor6)

        val seed = System.currentTimeMillis().toLong()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]

    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notes_layout = itemView.findViewById<CardView>(R.id.card_layout)
        val title = itemView.findViewById<TextView>(R.id.tv_title)
        val note = itemView.findViewById<TextView>(R.id.tv_note)
        val date = itemView.findViewById<TextView>(R.id.tv_date)
        val androidX = itemView.findViewById<ImageView>(R.id.androidX)
        val arrowX = itemView.findViewById<ImageView>(R.id.arrowX)
        val starX = itemView.findViewById<ImageView>(R.id.starX)
        val clockX = itemView.findViewById<ImageView>(R.id.clockX)
    }

    interface NotesClickListner {
        fun onItemClicked(note: Note)
        fun onLongItemClicked(note: Note, cardView: CardView)
    }
}