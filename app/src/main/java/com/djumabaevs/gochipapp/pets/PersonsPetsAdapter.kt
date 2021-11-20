package com.djumabaevs.gochipapp.pets

import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.djumabaevs.gochipapp.GetPersonPetQuery
import com.djumabaevs.gochipapp.databinding.PersonPetItemBinding

class PersonsPetsAdapter(
) : RecyclerView.Adapter<PersonsPetsAdapter.ViewHolder>() {

    private var pets: List<GetPersonPetQuery.Persons_pet1> = listOf()

    class ViewHolder(val binding: PersonPetItemBinding) : RecyclerView.ViewHolder(binding.root)

    var onEndOfListReached: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PersonPetItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val personsPet = pets[position]
        holder.binding.testPersonPet.text = personsPet.pet.pet_name
        holder.binding.petColorTxt.text = personsPet.pet.cats.firstOrNull()?.colour ?: personsPet.pet.dogs.firstOrNull()?.colour ?: "raw"
        holder.binding.petWeightTxt.text = (personsPet.pet.cats.firstOrNull()?.weight ?: personsPet.pet.dogs.firstOrNull()?.weight).toString() ?: "raw"
        holder.binding.petTypeTxt.text = personsPet.pet.cats.firstOrNull()?.gender ?: personsPet.pet.dogs.firstOrNull()?.gender ?: "raw"
        holder.binding.petBreedTxt.text = personsPet.pet.cats.firstOrNull()?.cats_breed?.breed_name ?: personsPet.pet.dogs.firstOrNull()?.dogs_breed?.breed_name

        val imageByteArray = Base64.decode(personsPet.pet.pet_photo?.substringAfter(",") ?: "", Base64.DEFAULT)
        val context = holder.itemView.context
        val petImage = holder.binding.petView
        Glide.with(context).load(imageByteArray).circleCrop().into(petImage)

    }

    override fun getItemCount(): Int {
        return pets.size
    }

    fun submitData(pets: List<GetPersonPetQuery.Persons_pet1>) {
        this.pets = pets
        notifyDataSetChanged()
    }
}