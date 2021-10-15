package com.djumabaevs.gochipapp

import android.bluetooth.BluetoothManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vincentmasselis.rxbluetoothkotlin.rxScan
import com.vincentmasselis.rxuikotlin.utils.ActivityState
import dagger.hilt.android.AndroidEntryPoint
import com.vincentmasselis.rxuikotlin.disposeOnState



class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val device by lazy { intent.getParcelableExtra<BluetoothDevice>(DEVICE_EXTRA) }

    private val states = BehaviorSubject.createDefault<States>(States.Connecting)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navBottomView : BottomNavigationView = findViewById(R.id.bottom_navigation_view)
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_home, R.id.nav_devices, R.id.pets_list_fragment), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navBottomView.setupWithNavController(navController)


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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}