package com.djumabaevs.gochipapp.ble

import android.Manifest
import android.app.Activity
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.view.clicks
import com.vincentmasselis.rxbluetoothkotlin.*
import com.vincentmasselis.rxuikotlin.disposeOnState
import com.vincentmasselis.rxuikotlin.utils.ActivityState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_scan.*
import java.util.concurrent.TimeUnit

class ScanActivity : AppCompatActivity() {

    private var currentState = BehaviorSubject.createDefault<States>(States.NotScanning)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        currentState
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                @Suppress("UNUSED_VARIABLE") val ignoreMe = when (it) {
                    States.NotScanning -> {
                        not_scanning_group.visibility = View.VISIBLE
                        start_scan_group.visibility = View.GONE
                        scanning_group.visibility = View.GONE
                    }
                    States.StartingScan -> {
                        not_scanning_group.visibility = View.GONE
                        start_scan_group.visibility = View.VISIBLE
                        scanning_group.visibility = View.GONE
                    }
                    States.Scanning -> {
                        not_scanning_group.visibility = View.GONE
                        start_scan_group.visibility = View.GONE
                        scanning_group.visibility = View.VISIBLE
                    }
                }
            }
            .disposeOnState(ActivityState.DESTROY, this)
