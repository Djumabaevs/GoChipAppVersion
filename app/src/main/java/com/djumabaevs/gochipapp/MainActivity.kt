package com.djumabaevs.gochipapp

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanFilter
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.ParcelUuid
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.djumabaevs.gochipapp.login.LoginActivity
import com.djumabaevs.gochipapp.screens.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.vincentmasselis.rxbluetoothkotlin.rxScan
import com.vincentmasselis.rxuikotlin.utils.ActivityState
import dagger.hilt.android.AndroidEntryPoint
import com.vincentmasselis.rxuikotlin.disposeOnState
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert



class MainActivity : AppCompatActivity() {


    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var firebaseAuth: FirebaseAuth

//    val filter = ScanFilter.Builder().setServiceUuid(
//      //  ParcelUuid.fromString(ENVIRONMENTAL_SERVICE_UUID.toString())
//        ParcelUuid.fromString("Redmi")
//    ).build()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        val textCheck: TextView = findViewById(R.id.phoneTv)
        val btnChech: Button = findViewById(R.id.logoutBtn)

        btnChech.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navBottomView: BottomNavigationView = findViewById(R.id.bottom_navigation_view)
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_devices, R.id.pets_list_fragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navBottomView.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

//    val isLocationPermissionGranted
//        get() = hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
//    fun Context.hasPermission(permissionType: String): Boolean {
//        return ContextCompat.checkSelfPermission(this, permissionType) ==
//                PackageManager.PERMISSION_GRANTED
//    }

    private fun checkUser() {
        val textCheck: TextView = findViewById(R.id.phoneTv)
        val btnChech: Button = findViewById(R.id.logoutBtn)

        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            val phone = firebaseUser.phoneNumber
            textCheck.text = phone
        }
    }

}