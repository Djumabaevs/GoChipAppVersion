package com.djumabaevs.gochipapp.login.cache.newAdapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.djumabaevs.gochipapp.databinding.ActivityPetNewBinding
import com.djumabaevs.gochipapp.login.cache.Resource
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
//class PetActivityNew : AppCompatActivity() {
//
//    private val viewModel: PetViewModel by viewModels()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val binding = ActivityPetNewBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        val petAdapter = PetAdapter()
//
//        binding.apply {
//            recyclerView.apply {
//                adapter = petAdapter
//                layoutManager = LinearLayoutManager(this@PetActivityNew)
//            }
//
//            viewModel.pets.observe(this@PetActivityNew) { result ->
//                petAdapter.submitList(result.data)
//
//                progressBar.isVisible = result is Resource.Loading && result.data.isNullOrEmpty()
//                textViewError.isVisible = result is Resource.Error && result.data.isNullOrEmpty()
//                textViewError.text = result.error?.localizedMessage
//            }
//        }
//    }
//}