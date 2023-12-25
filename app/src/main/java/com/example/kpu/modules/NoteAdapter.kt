package com.example.kpu.modules

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.kpu.R
import com.example.kpu.adapters.Note

class NoteAdapter(private val context: Context, private val notesList: List<Note>) : BaseAdapter() {

    override fun getCount(): Int {
        return notesList.size
    }

    override fun getItem(position: Int): Any {
        return notesList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_note, parent, false)
            viewHolder = ViewHolder(view.findViewById(R.id.textViewNote))
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val note = getItem(position) as Note
        viewHolder.textViewNote.text = note.text

        return view
    }

    private class ViewHolder(val textViewNote: TextView)
}
