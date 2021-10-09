package com.djumabaevs.gochipapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
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

        val channel = Channel<Unit>(Channel.CONFLATED)

        channel.trySend(Unit)
        adapter.onEndOfListReached = {
            channel.trySend(Unit)
        }



        lifecycleScope.launchWhenResumed {
            for (item in channel) {
                val response = try {
                    apolloClient(requireContext()).query(GetPetQuery()).await()
                } catch (e: ApolloException) {
                    Log.d("LaunchList", "Failure", e)
                    return@launchWhenResumed
                }

            val newPets = response.data?.pets

            if (newPets != null) {
                pets.addAll(newPets)
                adapter.notifyDataSetChanged()

            }

        }
            adapter.onEndOfListReached = null
            channel.close()
    }


}