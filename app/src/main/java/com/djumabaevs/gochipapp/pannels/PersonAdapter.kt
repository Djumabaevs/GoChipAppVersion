package com.djumabaevs.gochipapp.pannels

import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.djumabaevs.gochipapp.GetPersonsDataQuery
import com.djumabaevs.gochipapp.GetPetQuery
import com.djumabaevs.gochipapp.databinding.PersonItemBinding
import com.djumabaevs.gochipapp.databinding.PetItemBinding

class PersonAdapter(
    private val personData: List<GetPersonsDataQuery.Ui_pannels_to_user>,
) : RecyclerView.Adapter<PersonAdapter.ViewHolder>() {


    class ViewHolder(val binding: PersonItemBinding) : RecyclerView.ViewHolder(binding.root)

    var onEndOfListReached: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PersonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = personData[position]

//        holder.binding.tvPersonName.text = person.person_uid.toString()
//        holder.binding.tvPersonName.text = person.ui_pannels_to_users.firstOrNull()?.person?.person_name
        holder.binding.tvPersonName.text = person.person.person_name
//        holder.binding.tvPersonUid.text = person.person.person_uid.toString()
        holder.binding.tvPersonPhone.text = person.person.person_phone
        holder.binding.tvPersonEmail.text = person.person.person_email
        holder.binding.tvPersonPassword.text = person.person.person_password

    }

    override fun getItemCount(): Int {
        return personData.size
    }
}