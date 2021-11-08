package com.djumabaevs.gochipapp.login

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.djumabaevs.gochipapp.R

class LoginViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.recyclerview_item, parent, false)) {
    private var title: TextView? = null
    private var author: TextView? = null

    init {
        title = itemView.findViewById(R.id.title)
        author = itemView.findViewById(R.id.author)
    }

    fun bind(book: BooksQuery.FindBook) {
        title?.text = book.title()
        author?.text = book.author()
    }

}