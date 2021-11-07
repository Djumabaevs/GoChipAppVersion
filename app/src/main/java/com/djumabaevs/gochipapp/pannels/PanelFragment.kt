package com.djumabaevs.gochipapp.pannels

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.djumabaevs.gochipapp.R
import com.djumabaevs.gochipapp.databinding.FragmentPanelBinding
import com.djumabaevs.gochipapp.databinding.FragmentPetsListBinding

class PanelFragment : Fragment() {

    private lateinit var binding: FragmentPanelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPanelBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}