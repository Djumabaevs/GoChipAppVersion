package com.djumabaevs.gochipapp.login.cache.newAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.djumabaevs.gochipapp.databinding.PetItemNewBinding
import com.djumabaevs.gochipapp.login.cache.Pet

class PetAdapter :
    ListAdapter<Pet, PetAdapter.PetViewHolder>(PetComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val binding =
            PetItemNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    class PetViewHolder(private val binding: PetItemNewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(pet: Pet) {
            binding.apply {
//                Glide.with(itemView)
//                    .load(pet.logo)
//                    .into(imageViewLogo)

                textViewName.text = pet.name
                textViewType.text = pet.type
            }
        }
    }

    class PetComparator : DiffUtil.ItemCallback<Pet>() {
        override fun areItemsTheSame(oldItem: Pet, newItem: Pet) =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Pet, newItem: Pet) =
            oldItem == newItem
    }
}