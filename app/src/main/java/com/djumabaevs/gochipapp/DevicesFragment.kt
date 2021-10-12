package com.djumabaevs.gochipapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dagger.hilt.android.AndroidEntryPoint
import android.bluetooth.BluetoothManager
import androidx.appcompat.app.AppCompatActivity
import com.djumabaevs.gochipapp.databinding.FragmentDevicesBinding
import com.vincentmasselis.rxbluetoothkotlin.rxScan
import com.vincentmasselis.rxuikotlin.disposeOnState
import com.vincentmasselis.rxuikotlin.utils.ActivityState
import com.vincentmasselis.rxuikotlin.postForUI


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

        (getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager)
            .rxScan()
            .subscribe {
                applicationContext
            }
            .disposeOnState(ActivityState.DESTROY, this)

        return binding.root
    }

    }

    fun setMessage(message: String) {
        postForUI {  = message }
    }





