package com.djumabaevs.gochipapp.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.djumabaevs.gochipapp.R

class CustomDialogStatusFragment : DialogFragment() {
    var btnWork: Button? = null
    var btnDinner: Button? = null
    var btnRepair: Button? = null
    var btnNotWorking: Button? = null
    var btnOk: Button? = null
    var btnCancel: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_custom_dialog_status, null)
        btnWork = v.findViewById(R.id.button_work)
        btnDinner = v.findViewById(R.id.button_dinner)
        btnOk = v.findViewById(R.id.button_ok)
        btnCancel = v.findViewById(R.id.button_cancel)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnWork!!.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Working",
                Toast.LENGTH_SHORT
            ).show()
        }
        btnDinner!!.setOnClickListener { }
        btnOk!!.setOnClickListener { }
        btnCancel!!.setOnClickListener { dismiss() }
    }

    companion object {
        fun newInstance(
            param1: String?,
            param2: String?
        ): CustomDialogStatusFragment {
            return CustomDialogStatusFragment()
        }
    }
}