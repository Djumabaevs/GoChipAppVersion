package com.djumabaevs.gochipapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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


}