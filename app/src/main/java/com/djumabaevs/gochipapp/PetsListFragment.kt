package com.djumabaevs.gochipapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
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

        lifecycleScope.launchWhenResumed {

            val response = apolloClient(requireContext()).query(GetPetQuery()).await()

//            Log.d("PetInfo", "Success ${response.data}")

            val pet = response.data?.pets

            //  binding.petName.text = pet.pet_name.toString()


        }
    }


}