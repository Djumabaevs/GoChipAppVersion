package com.djumabaevs.gochipapp.screens

/*
import android.Manifest
import android.app.Activity
import android.bluetooth.*
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.djumabaevs.gochipapp.R
import com.djumabaevs.gochipapp.ScanResultAdapter
import com.djumabaevs.gochipapp.bleThrough.ConnectionManager
import com.djumabaevs.gochipapp.databinding.FragmentDevicesBinding
import com.djumabaevs.gochipapp.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.runOnUiThread
import timber.log.Timber
import java.util.*

const val ENABLE_BLUETOOTH_REQUEST_CODE = 1
const val LOCATION_PERMISSION_REQUEST_CODE = 2

class HomeFragment : Fragment() {

    private val bleScanner by lazy {
        bluetoothAdapter.bluetoothLeScanner
    }

    private val bluetoothAdapter: BluetoothAdapter by lazy {
        val bluetoothManager = requireActivity().getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    private val scanSettings = ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
        .build()

    private val scanResults = mutableListOf<ScanResult>()

//    private val scanResultAdapter: ScanResultAdapter by lazy {
//        ScanResultAdapter(scanResults) { result ->
//            // User tapped on a scan result
//            if (isScanning) {
//                stopBleScan()
//            }
//            with(result.device) {
//                Log.w("ScanResultAdapter", "Connecting to $address")
//                connectGatt(context, false, gattCallback)
//            }
//        }
//    }

    private val scanResultAdapter: ScanResultAdapter by lazy {
        ScanResultAdapter(scanResults) { result ->
            if (isScanning) {
                stopBleScan()
            }
            with(result.device) {
                Timber.w("Connecting to $address")
                ConnectionManager.connect(this, requireContext())
            }
        }
    }

    private val gattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt, status: Int, newState: Int) {
            val deviceAddress = gatt.device.address

            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    Log.w("BluetoothGattCallback", "Successfully connected to $deviceAddress")
                //    bluetoothGatt = gatt
//                    Handler(Looper.getMainLooper()).post {
//                        gatt?.discoverServices()
//                    }
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    Log.w("BluetoothGattCallback", "Successfully disconnected from $deviceAddress")
                    gatt.close()
                }
            } else {
                Log.w("BluetoothGattCallback", "Error $status encountered for $deviceAddress! Disconnecting...")
                gatt.close()
            }
        }
        override fun onServicesDiscovered(gatt: BluetoothGatt, status: Int) {
            with(gatt) {
                Log.w("BluetoothGattCallback", "Discovered ${services.size} services for ${device.address}")
                printGattTable()
                // Consider connection setup as complete here
            }
        }
        override fun onCharacteristicRead(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic,
            status: Int
        ) {
            with(characteristic) {
                when (status) {
                    BluetoothGatt.GATT_SUCCESS -> {
                        Log.i("BluetoothGattCallback", "Read characteristic $uuid:\n${value.toHexString()}")
                    }
                    BluetoothGatt.GATT_READ_NOT_PERMITTED -> {
                        Log.e("BluetoothGattCallback", "Read not permitted for $uuid!")
                    }
                    else -> {
                        Log.e("BluetoothGattCallback", "Characteristic read failed for $uuid, error: $status")
                    }
                }
            }
        }
        override fun onCharacteristicChanged(
            gatt: BluetoothGatt,
            characteristic: BluetoothGattCharacteristic
        ) {
            with(characteristic) {
                Log.i("BluetoothGattCallback", "Characteristic $uuid changed | value: ${value.toHexString()}")
            }
        }
    }

    private fun BluetoothGatt.printGattTable() {
        if (services.isEmpty()) {
            Log.i("printGattTable", "No service and characteristic available, call discoverServices() first?")
            return
        }
        services.forEach { service ->
            val characteristicsTable = service.characteristics.joinToString(
                separator = "\n|--",
                prefix = "|--"
            ) { it.uuid.toString() }
            Log.i("printGattTable", "\nService ${service.uuid}\nCharacteristics:\n$characteristicsTable"
            )
        }
    }

//    private fun readBatteryLevel() {
//        val batteryServiceUuid = UUID.fromString("0000180f-0000-1000-8000-00805f9b34fb")
//        val batteryLevelCharUuid = UUID.fromString("00002a19-0000-1000-8000-00805f9b34fb")
//        val batteryLevelChar = gatt
//            .getService(batteryServiceUuid)?.getCharacteristic(batteryLevelChar)
//        if (batteryLevelChar?.isReadable() == true) {
//            gatt.readCharacteristic(batteryLevelChar)
//        }
//    }

//    fun writeCharacteristic(characteristic: BluetoothGattCharacteristic, payload: ByteArray) {
//        val writeType = when {
//            characteristic.isWritable() -> BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT
//            characteristic.isWritableWithoutResponse() -> {
//                BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE
//            }
//            else -> error("Characteristic ${characteristic.uuid} cannot be written to")
//        }
//
//        bluetoothGatt?.let { gatt ->
//            characteristic.writeType = writeType
//            characteristic.value = payload
//            gatt.writeCharacteristic(characteristic)
//        } ?: error("Not connected to a BLE device!")
//    }


//    private val scanCallback = object : ScanCallback() {
//        override fun onScanResult(callbackType: Int, result: ScanResult) {
//            val indexQuery = scanResults.indexOfFirst { it.device.address == result.device.address }
//            if (indexQuery != -1) { // A scan result already exists with the same address
//                scanResults[indexQuery] = result
//                scanResultAdapter.notifyItemChanged(indexQuery)
//            } else {
//                with(result.device) {
//                    Log.i("ScanCallback", "Found BLE device! Name: ${name ?: "Unnamed"}, address: $address")
//                }
//                scanResults.add(result)
//                scanResultAdapter.notifyItemInserted(scanResults.size - 1)
//            }
//        }

    private val scanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val indexQuery = scanResults.indexOfFirst { it.device.address == result.device.address }
            if (indexQuery != -1) { // A scan result already exists with the same address
                scanResults[indexQuery] = result
                scanResultAdapter.notifyItemChanged(indexQuery)
            } else {
                with(result.device) {
                    Timber.i("Found BLE device! Name: ${name ?: "Unnamed"}, address: $address")
                }
                scanResults.add(result)
                scanResultAdapter.notifyItemInserted(scanResults.size - 1)
            }
        }

        override fun onScanFailed(errorCode: Int) {
            Timber.e("onScanFailed: code $errorCode")
        }
    }
}

    private lateinit var appBarConfiguration: AppBarConfiguration

//    val filter = ScanFilter.Builder().setServiceUuid(
//      //  ParcelUuid.fromString(ENVIRONMENTAL_SERVICE_UUID.toString())
//        ParcelUuid.fromString("Redmi")
//    ).build()


    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
        //    binding.textDevices.text = it
        })


        binding.scanButtonF.setOnClickListener {

            if (isScanning) {
                stopBleScan()
            } else {
                startBleScan()
            }
            requestLocationPermission()
            setupRecyclerView()
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        scan_results_recycler_view.apply {
            adapter = scanResultAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                RecyclerView.VERTICAL,
                false
            )
            isNestedScrollingEnabled = false
        }

        val animator = scan_results_recycler_view.itemAnimator
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    private fun startBleScan() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !fineLocationPermissionApproved()) {
            requestLocationPermission()
        }
        else {
            scanResults.clear()
            scanResultAdapter.notifyDataSetChanged()
            bleScanner.startScan(null, scanSettings, scanCallback)
            isScanning = true
        }
    }


    private var isScanning = false
        set(value) {
            field = value
            runOnUiThread {

                binding.scanButtonF.text = if (value) "Stop Scan" else "Start Scan"
            }
        }

    private fun stopBleScan() {
        bleScanner.stopScan(scanCallback)
        isScanning = false
    }

    private fun requestLocationPermission() {
        if (fineLocationPermissionApproved()) {
            return
        }
        runOnUiThread {
            alert {
                title = "Location permission required"
                message = "Starting from Android M (6.0), the system requires apps to be granted " +
                        "location access in order to scan for BLE devices."
                isCancelable = false
                positiveButton(android.R.string.ok) {
                    requestPermission(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        LOCATION_PERMISSION_REQUEST_CODE
                    )
                }
            }.show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.firstOrNull() == PackageManager.PERMISSION_DENIED) {
                    requestLocationPermission()
                } else {
                    startBleScan()
                }
            }
        }
    }


    private fun Fragment.requestPermission(permission: String, requestCode: Int) {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), requestCode)
    }

//    val isLocationPermissionGranted
//        get() = hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
//    fun Context.hasPermission(permissionType: String): Boolean {
//        return ContextCompat.checkSelfPermission(this, permissionType) ==
//                PackageManager.PERMISSION_GRANTED
//    }

    private fun fineLocationPermissionApproved(): Boolean {

        val context = context ?: return false

        return PackageManager.PERMISSION_GRANTED == checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    override fun onResume() {
        super.onResume()
        if (!bluetoothAdapter.isEnabled) {
            promptEnableBluetooth()
        }
    }
    private fun promptEnableBluetooth() {
        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent,
                ENABLE_BLUETOOTH_REQUEST_CODE
            )
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            ENABLE_BLUETOOTH_REQUEST_CODE -> {
                if (resultCode != Activity.RESULT_OK) {
                    promptEnableBluetooth()
                }
            }
        }


    }

//    fun enableNotifications(characteristic: BluetoothGattCharacteristic) {
//        val cccdUuid = UUID.fromString(CCC_DESCRIPTOR_UUID)
//        val payload = when {
//            characteristic.isIndicatable() -> BluetoothGattDescriptor.ENABLE_INDICATION_VALUE
//            characteristic.isNotifiable() -> BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE
//            else -> {
//                Log.e("ConnectionManager", "${characteristic.uuid} doesn't support notifications/indications")
//                return
//            }
//        }
//
//        characteristic.getDescriptor(cccdUuid)?.let { cccDescriptor ->
//            if (bluetoothGatt?.setCharacteristicNotification(characteristic, true) == false) {
//                Log.e("ConnectionManager", "setCharacteristicNotification failed for ${characteristic.uuid}")
//                return
//            }
//            writeDescriptor(cccDescriptor, payload)
//        } ?: Log.e("ConnectionManager", "${characteristic.uuid} doesn't contain the CCC descriptor!")
//    }

//    fun disableNotifications(characteristic: BluetoothGattCharacteristic) {
//        if (!characteristic.isNotifiable() && !characteristic.isIndicatable()) {
//            Log.e("ConnectionManager", "${characteristic.uuid} doesn't support indications/notifications")
//            return
//        }
//
//        val cccdUuid = UUID.fromString(CCC_DESCRIPTOR_UUID)
//        characteristic.getDescriptor(cccdUuid)?.let { cccDescriptor ->
//            if (bluetoothGatt?.setCharacteristicNotification(characteristic, false) == false) {
//                Log.e("ConnectionManager", "setCharacteristicNotification failed for ${characteristic.uuid}")
//                return
//            }
//            writeDescriptor(cccDescriptor, BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE)
//        } ?: Log.e("ConnectionManager", "${characteristic.uuid} doesn't contain the CCC descriptor!")
//    }
}*/
