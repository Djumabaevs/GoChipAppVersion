package com.djumabaevs.gochipapp.pets

import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.djumabaevs.gochipapp.GetPetQuery
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pet = pets[position]

        holder.binding.petNameTxt.text = pet.pet_name ?: "def"
        holder.binding.petColorTxt.text = pet.cats.firstOrNull()?.colour ?: pet.dogs.firstOrNull()?.colour ?: "def"
        holder.binding.petWeightTxt.text = (pet.cats.firstOrNull()?.weight ?: pet.dogs.firstOrNull()?.weight ?: "def").toString()
        holder.binding.petBreedTxt.text = (pet.cats.firstOrNull()?.cats_breed?.breed_name ?: pet.dogs.firstOrNull()?.dogs_breed?.breed_name ?: "def").toString()
        holder.binding.petTypeTxt.text = pet.cats.firstOrNull()?.gender ?: pet.dogs.firstOrNull()?.gender ?: "def"
 //       holder.binding.petView.load(pet.pet_photo?.substringAfter(",") ?: "")

        val imageByteArray = Base64.decode(pet.pet_photo?.substringAfter(",") ?: "", Base64.DEFAULT)
        val context = holder.itemView.context
        val petImage = holder.binding.petView
        Glide.with(context).load(imageByteArray).circleCrop().into(petImage)



    }

    override fun getItemCount(): Int {
        return pets.size
    }


}