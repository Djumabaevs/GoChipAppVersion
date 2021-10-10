package com.djumabaevs.gochipapp

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil .load
import coil.loadAny
import com.djumabaevs.gochipapp.databinding.FragmentPetsListBinding
import com.djumabaevs.gochipapp.databinding.PetItemBinding

class PetsListAdapter(
    private val pets: List<GetPetQuery.Pet>,
) : RecyclerView.Adapter<PetsListAdapter.ViewHolder>() {

    class ViewHolder(val binding: PetItemBinding) : RecyclerView.ViewHolder(binding.root)

    var onEndOfListReached: (() -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PetItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetsListAdapter.ViewHolder, position: Int) {
        val pet = pets[position]
        holder.binding.petNames.text = pet.pet_name ?: ""
        holder.binding.petBreeds.text = pet.pets_type.pet_type_name
     //   holder.binding.petDogs.text = pet.dogs[position].colour
        holder.binding.petPhotos.load(pet.pet_photo) {
            placeholder(R.drawable.ic_launcher_background)
        }
        Log.d("Pet", "onBindViewHolder: ${pet.pet_photo} ")
        if (position == pets.size - 1) {
            onEndOfListReached?.invoke()
        }

    }

    override fun getItemCount(): Int {
        return pets.size
    }


}