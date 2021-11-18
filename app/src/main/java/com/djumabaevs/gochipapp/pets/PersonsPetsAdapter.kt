package com.djumabaevs.gochipapp.pets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.djumabaevs.gochipapp.GetPersonPetQuery
import com.djumabaevs.gochipapp.databinding.PersonPetItemBinding

class PersonsPetsAdapter(
    private val personData: List<GetPersonPetQuery.Persons_pet>,
) : RecyclerView.Adapter<PersonsPetsAdapter.ViewHolder>() {


    class ViewHolder(val binding: PersonPetItemBinding) : RecyclerView.ViewHolder(binding.root)

    var onEndOfListReached: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PersonPetItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val personsPet = personData[position]

        val validate = personsPet.person.persons_pets.firstOrNull()?.pet?.pet_name
        val email = personsPet.person.person_email

        holder.binding.testPersonPet.text = validate
        }

    override fun getItemCount(): Int {
        return personData.size
    }
}