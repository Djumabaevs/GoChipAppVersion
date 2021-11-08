package com.djumabaevs.gochipapp.login

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.djumabaevs.gochipapp.GetVetPersonByPhoneQuery
import com.djumabaevs.gochipapp.R

class LoginViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.login_item, parent, false)) {
    private var loginPersonName: TextView? = null
    private var loginPersonStatus: TextView? = null

    init {
        loginPersonName = itemView.findViewById(R.id.login_person_name)
        loginPersonStatus = itemView.findViewById(R.id.login_person_status)
    }

    fun bind(book: GetVetPersonByPhoneQuery.Person) {
        loginPersonName?.text = book.person_name
        loginPersonStatus?.text = book.status.toString()
    }

}