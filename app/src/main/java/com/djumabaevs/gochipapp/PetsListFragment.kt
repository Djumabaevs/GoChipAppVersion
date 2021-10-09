package com.djumabaevs.gochipapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.coroutines.await
import com.djumabaevs.gochipapp.databinding.FragmentPetsListBinding


class PetsListFragment : Fragment() {

    private lateinit var binding: FragmentPetsListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPetsListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pets = mutableListOf<GetPetQuery.Pet>()
        val adapter = PetsListAdapter(pets)
        binding.petsRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.petsRecycler.adapter = adapter

        lifecycleScope.launchWhenResumed {
            val response = apolloClient(requireContext()).query(GetPetQuery()).await()

            val newPets = response.data?.pets

            if (newPets != null) {
                pets.addAll(newPets)
                adapter.notifyDataSetChanged()

            }

        }
    }


}