package com.djumabaevs.gochipapp.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.djumabaevs.gochipapp.databinding.FragmentDevicesBinding


class DevicesFragment : Fragment() {

    private lateinit var devicesViewModel: DevicesViewModel
    private lateinit var binding: FragmentDevicesBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDevicesBinding.inflate(inflater, container, false)

        devicesViewModel =
            ViewModelProviders.of(this).get(DevicesViewModel::class.java)
        devicesViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textDevices.text = it
        })



        return binding.root
    }

    }







