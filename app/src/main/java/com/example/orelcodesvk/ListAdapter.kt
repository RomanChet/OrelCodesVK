package com.example.orelcodesvk

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(private val list: Array<Post>)
    : RecyclerView.Adapter<NoteViewHolder>() {
    val listOfElements: MutableList<NoteViewHolder> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NoteViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val post: Post = list[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = list.size

}