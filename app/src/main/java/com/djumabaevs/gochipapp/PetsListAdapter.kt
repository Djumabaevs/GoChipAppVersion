package com.djumabaevs.gochipapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.djumabaevs.gochipapp.databinding.FragmentPetsListBinding

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
        holde = pet.pet_name ?: ""
    }

    override fun getItemCount(): Int {
        return pets.size
    }

}