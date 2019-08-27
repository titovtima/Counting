package com.example.counting

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        switchTime.isChecked = Status.timeMode

        switchTime.setOnClickListener {
            Status.timeMode = switchTime.isChecked
            Log.d("tag123", switchTime.isChecked.toString())
        }

        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    override fun onStop() {
        super.onStop()

        Status.saveSettings = getSharedPreferences(Status.nameFileSettings, Context.MODE_PRIVATE)

        val editor = Status.saveSettings!!.edit()
        editor.putBoolean(Status.saveTimeModeKey, Status.timeMode)
        editor.apply()
    }
}
