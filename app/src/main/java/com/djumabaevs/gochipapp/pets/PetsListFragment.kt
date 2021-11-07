package com.djumabaevs.gochipapp.pets

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.djumabaevs.gochipapp.GetPetQuery
import com.djumabaevs.gochipapp.apollo.apolloClient
import com.djumabaevs.gochipapp.databinding.FragmentPetsListBinding
import kotlinx.coroutines.channels.Channel


class PetsListFragment : Fragment() {

    private lateinit var binding: FragmentPetsListBinding


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

        binding.progressBar.visibility = View.VISIBLE

        val channel = Channel<Unit>(Channel.CONFLATED)

        channel.trySend(Unit)
        adapter.onEndOfListReached = {
            channel.trySend(Unit)
        }



        lifecycleScope.launchWhenResumed {
            var petUid: String? = null
            for (item in channel) {
                val response = try {
                    apolloClient(requireContext()).query(GetPetQuery(pet_uid = Input.fromNullable(petUid))).await()
                } catch (e: ApolloException) {
                    Log.d("PetsListGoChip", "Failure", e)
                    return@launchWhenResumed
                }

                Log.d("PersonListGoChip", "response pets: $response")

                binding.progressBar.visibility = View.GONE

                val newPets = response.data?.pets?.filterNotNull()



                if (newPets != null) {
                    pets.addAll(newPets)
                    adapter.notifyDataSetChanged()
                }
            //    petUid = response.data?.pets?.get(0)?.pet_uid.toString()


            }
            adapter.onEndOfListReached = null
            channel.close()
        }

    }
}