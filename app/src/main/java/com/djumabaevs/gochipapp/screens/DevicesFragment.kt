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
import androidx.work.*
import com.djumabaevs.gochipapp.R
import com.djumabaevs.gochipapp.nfc.OutcomingNfcManager
import com.djumabaevs.gochipapp.nfc.ReceiverActivity
import com.djumabaevs.gochipapp.util.OneTimeRequestWorker
import com.djumabaevs.gochipapp.util.PeriodicRequestWorker
import org.jetbrains.anko.support.v4.runOnUiThread
import java.util.concurrent.TimeUnit


class DevicesFragment : Fragment(),  OutcomingNfcManager.NfcActivity {

    private lateinit var tvOutcomingMessage: TextView
    private lateinit var etOutcomingMessage: EditText
    private lateinit var btnSetOutcomingMessage: Button

    private lateinit var buttonRequest: Button
    private lateinit var buttonPeriodic: Button

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

        this.buttonRequest = view.findViewById(R.id.button)
        this.buttonPeriodic = view.findViewById(R.id.buttonMore)
        buttonRequest.setOnClickListener {
            val oneTimeRequestConstraints = Constraints.Builder()
                .setRequiresCharging(false)
                .setRequiresStorageNotLow(true)
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val data = Data.Builder()
            data.putString("inputKey", "input value")

            val sampleWork = OneTimeWorkRequest
                .Builder(OneTimeRequestWorker::class.java)
                .setInputData(data.build())
                .setConstraints(oneTimeRequestConstraints)
                .build()

            WorkManager.getInstance(requireContext()).enqueue(sampleWork)

            WorkManager.getInstance(requireContext())
                .getWorkInfoByIdLiveData(sampleWork.id)
                .observe(this, { workInfo ->
                    OneTimeRequestWorker.Companion.logger(workInfo.state.name)
                    if(workInfo != null) {
                        when(workInfo.state) {
                            WorkInfo.State.ENQUEUED -> {
                                Toast.makeText(requireContext(), "Process is Enqueued", Toast.LENGTH_LONG).show()
                            }
                            WorkInfo.State.RUNNING -> {
                                Toast.makeText(requireContext(), "Process is Running", Toast.LENGTH_LONG).show()
                            }
                            WorkInfo.State.BLOCKED -> {
                                Toast.makeText(requireContext(), "Process is Blocked", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    if(workInfo != null && workInfo.state.isFinished) {

                    }
                })
        }

        buttonPeriodic.setOnClickListener {
            val periodicRequestConstraints = Constraints.Builder()
                .setRequiresCharging(false)
                .setRequiresStorageNotLow(true)
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val periodicWorkRequest = PeriodicWorkRequest
                .Builder(PeriodicRequestWorker::class.java, 5, TimeUnit.SECONDS)
                .setConstraints(periodicRequestConstraints)
                .build()

            WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
                "Periodic Work Request",
                ExistingPeriodicWorkPolicy.KEEP,
                periodicWorkRequest
            )
            Toast.makeText(requireContext(), "Process is notified", Toast.LENGTH_LONG).show()
        }

        this.tvOutcomingMessage = view.findViewById(R.id.tv_out_message)
        this.etOutcomingMessage = view.findViewById(R.id.et_message)
        this.btnSetOutcomingMessage = view.findViewById(R.id.btn_set_out_message)
        this.btnSetOutcomingMessage.setOnClickListener {
            setOutGoingMessage()
//        val intent = Intent(this@DevicesFragment.context, ReceiverActivity::class.java)
//            startActivity(intent)

        }

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







