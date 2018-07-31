package com.skolimowskim.verificationtask

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_start.setOnClickListener { onStartClicked() }
        button_stop.setOnClickListener{ onStopClicked() }
    }

    private fun onStartClicked() {
        val aParam = param_a_input.text.toString().toInt()
    }

    private fun onStopClicked() {

    }
}
