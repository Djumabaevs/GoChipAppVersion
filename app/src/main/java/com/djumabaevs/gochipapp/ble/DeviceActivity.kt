package com.djumabaevs.gochipapp.ble

import android.bluetooth.BluetoothDevice
import android.annotation.SuppressLint
import android.app.Application
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.djumabaevs.gochipapp.R
import com.vincentmasselis.rxbluetoothkotlin.BluetoothIsTurnedOff
import com.vincentmasselis.rxbluetoothkotlin.DeviceDisconnected
import com.vincentmasselis.rxbluetoothkotlin.connectRxGatt
import com.vincentmasselis.rxuikotlin.disposeOnState
import com.vincentmasselis.rxbluetoothkotlin.*
import com.vincentmasselis.rxuikotlin.utils.ActivityState
import io.reactivex.rxjava3.core.Maybe
//import io.reactivex.rxjava3.kotlin.Maybes
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_device.*
import java.util.*
/*

class DeviceActivity : AppCompatActivity() {

    private val device by lazy { intent.getParcelableExtra<BluetoothDevice>(DEVICE_EXTRA) }
    private val states = BehaviorSubject.createDefault<States>(States.Connecting)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)

        (this.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter.isEnabled

        //Scan BLE
        states
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                @Suppress("UNUSED_VARIABLE") val ignoreMe = when (it) {
                    States.Connecting -> {
                        connecting_group.visibility = View.VISIBLE
                        connected_group.visibility = View.GONE
                    }
                    is States.Connected -> {
                        connecting_group.visibility = View.GONE
                        connected_group.visibility = View.VISIBLE
                    }
                }
            }
            .disposeOnState(ActivityState.DESTROY, this)

        states
            .filter { it is States.Connecting }
            .switchMapSingle { device.connectRxGatt() }
            .switchMapMaybe { gatt -> gatt.whenConnectionIsReady().map { gatt } }
            .doOnSubscribe { connecting_progress_bar.visibility = View.VISIBLE }
            .doFinally { connecting_progress_bar.visibility = View.INVISIBLE }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Toast.makeText(this, "Connected !", Toast.LENGTH_SHORT).show()
                    states.onNext(States.Connected(it))
                },
                {
                    val message =
                        when (it) {
                            is BluetoothIsTurnedOff -> "Bluetooth is turned off"
                            is DeviceDisconnected -> "Unable to connect to the device"
                            else -> "Error occurred: $it"
                        }
                    AlertDialog.Builder(this).setMessage(message).show()
                }
            )
            .disposeOnState(ActivityState.DESTROY, this)

        states
            .filter { it is States.Connecting }
            .switchMapSingle { device.connectRxGatt() }
            .switchMapMaybe { gatt -> gatt.whenConnectionIsReady().map { gatt } }
            .doOnSubscribe { connecting_progress_bar.visibility = View.VISIBLE }
            .doFinally { connecting_progress_bar.visibility = View.INVISIBLE }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Toast.makeText(this, "Connected !", Toast.LENGTH_SHORT).show()
                    states.onNext(States.Connected(it))
                },
                {
                    val message =
                        when (it) {
                            is BluetoothIsTurnedOff -> "Bluetooth is turned off"
                            is DeviceDisconnected -> "Unable to connect to the device"
                            else -> "Error occurred: $it"
                        }
                    AlertDialog.Builder(this).setMessage(message).show()
                }
            )
            .disposeOnState(ActivityState.DESTROY, this)
    }

    override fun onDestroy() {
        (states.value as? States.Connected)?.gatt?.source?.disconnect()
        super.onDestroy()
        super.onDestroy()
    }

    private sealed class States {
        object Connecting : States()
        class Connected(val gatt: RxBluetoothGatt) : States()
    }

    companion object {
        fun intent(context: Context, device: BluetoothDevice): Intent = Intent(context, DeviceActivity::class.java)
            .putExtra(DEVICE_EXTRA, device)

        private const val DEVICE_EXTRA = "DEVICE_EXTRA"
    }
}*/
