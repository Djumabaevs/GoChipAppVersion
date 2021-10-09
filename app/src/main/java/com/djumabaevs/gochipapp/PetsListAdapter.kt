package com.djumabaevs.gochipapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.djumabaevs.gochipapp.databinding.FragmentPetsListBinding
import com.djumabaevs.gochipapp.databinding.PetItemBinding

class PetsListAdapter(
    private val pets: List<GetPetQuery.Pet>
) : RecyclerView.Adapter<PetsListAdapter.ViewHolder>() {

    class ViewHolder(val binding: PetItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PetItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetsListAdapter.ViewHolder, position: Int) {
        val pet = pets[position]
        holder.binding.petNames.text = pet.pet_name ?: ""
        holder.binding.petBreeds.text = pet.pets_type.toString()
        holder.binding.petPhotos.load(pet.pet_photo)
    }

    override fun getItemCount(): Int {
        return pets.size
    }

}