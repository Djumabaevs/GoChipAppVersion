package com.djumabaevs.gochipapp.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.djumabaevs.gochipapp.R
import android.content.Intent
import com.djumabaevs.gochipapp.pannels.PannelActivity
import com.djumabaevs.gochipapp.vets.VetActivity


class CustomDialogStatusFragment : DialogFragment() {
    var btnOwner: Button? = null
    var btnVet: Button? = null
  //  var btnOk: Button? = null
    var btnCancel: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_custom_dialog_status, null)
        btnOwner = v.findViewById(R.id.button_owner)
        btnVet = v.findViewById(R.id.button_vet)
      //  btnOk = v.findViewById(R.id.button_ok)
        btnCancel = v.findViewById(R.id.button_cancel)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnOwner!!.setOnClickListener {
            val intent = Intent(activity, VetActivity::class.java)
            startActivity(intent)
        }
        btnVet!!.setOnClickListener {
            val intent = Intent(activity, PannelActivity::class.java)
            startActivity(intent)
        }
//        btnOk!!.setOnClickListener {
//
//        }
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