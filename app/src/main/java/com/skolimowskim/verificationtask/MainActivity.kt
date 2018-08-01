package com.skolimowskim.verificationtask

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.location.*
import com.skolimowskim.verificationtask.utils.LogUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_start.setOnClickListener { onStartClicked() }
        button_stop.setOnClickListener { onStopClicked() }

        param_a_input.setText("10")
        param_b_input.setText("10")
        param_c_input.setText("10")
    }

    private fun onStartClicked() {
        val aParam = param_a_input.text.toString().toInt()
        val bParam = param_b_input.text.toString().toInt()
        val cParam = param_c_input.text.toString().toInt()

        startLocalizationUpdates(aParam)
    }

    private fun onStopClicked() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission") // app is only to show working on multiple task so I assume user manually granted permissions
    private fun startLocalizationUpdates(aParam: Int) {
        locationRequest = LocationRequest().apply {
            interval = (aParam * 1000).toLong()
            fastestInterval = (aParam * 1000).toLong()
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                LogUtils.log("Current location : " + locationResult.lastLocation)
            }
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.requestLocationUpdates(locationRequest,
                                                   locationCallback,
                                                   null /* Looper */)
    }
}
