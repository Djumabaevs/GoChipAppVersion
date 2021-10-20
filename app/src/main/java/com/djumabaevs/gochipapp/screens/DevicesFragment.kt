package com.djumabaevs.gochipapp.screens

import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.djumabaevs.gochipapp.R
import com.djumabaevs.gochipapp.nfc.OutcomingNfcManager
import org.jetbrains.anko.support.v4.runOnUiThread


class DevicesFragment : Fragment(),  OutcomingNfcManager.NfcActivity {

    private lateinit var tvOutcomingMessage: TextView
    private lateinit var etOutcomingMessage: EditText
    private lateinit var btnSetOutcomingMessage: Button

    private var nfcAdapter: NfcAdapter? = null

    private val isNfcSupported: Boolean =
        this.nfcAdapter != null

    private lateinit var outcomingNfcCallback: OutcomingNfcManager

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_devices, container,
            false)
        this.nfcAdapter = NfcAdapter.getDefaultAdapter(requireContext())

        if (!isNfcSupported) {
            Toast.makeText(requireContext(), "Nfc is not supported on this device", Toast.LENGTH_SHORT).show()

        }

        if (!nfcAdapter?.isEnabled!!) {
            Toast.makeText(
                requireContext(),
                "NFC disabled on this device. Turn on to proceed",
                Toast.LENGTH_SHORT
            ).show()
        }

        this.tvOutcomingMessage = view.findViewById(R.id.tv_out_message)
        this.etOutcomingMessage = view.findViewById(R.id.et_message)
        this.btnSetOutcomingMessage = view.findViewById(R.id.btn_set_out_message)
        this.btnSetOutcomingMessage.setOnClickListener { setOutGoingMessage() }

        // encapsulate sending logic in a separate class
        this.outcomingNfcCallback = OutcomingNfcManager(this)
        this.nfcAdapter?.setOnNdefPushCompleteCallback(outcomingNfcCallback, requireActivity())
        this.nfcAdapter?.setNdefPushMessageCallback(outcomingNfcCallback, requireActivity())

        return view
    }


//    override fun onNewIntent(intent: Intent) {
//        this.intent = intent
//    }

    private fun setOutGoingMessage() {
        val outMessage = this.etOutcomingMessage.text.toString()
        this.tvOutcomingMessage.text = outMessage
    }

    override fun getOutcomingMessage(): String =
        this.tvOutcomingMessage.text.toString()


    override fun signalResult() {
        // this will be triggered when NFC message is sent to a device.
        // should be triggered on UI thread. We specify it explicitly
        // cause onNdefPushComplete is called from the Binder thread
        runOnUiThread {
            Toast.makeText(requireContext(), R.string.message_beaming_complete, Toast.LENGTH_SHORT).show()
        }
    }
 }







