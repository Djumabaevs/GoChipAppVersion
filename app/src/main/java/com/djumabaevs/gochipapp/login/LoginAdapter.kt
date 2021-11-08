package com.djumabaevs.gochipapp.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.djumabaevs.gochipapp.GetVetPersonByPhoneQuery

class LoginAdapter(private val list:  List<GetVetPersonByPhoneQuery.Person>)
    : RecyclerView.Adapter<LoginViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoginViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return LoginViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: LoginViewHolder, position: Int) {
        val loginData: GetVetPersonByPhoneQuery.Person = list[position]
        holder.bind(loginData)
    }

    override fun getItemCount(): Int = list.size
}