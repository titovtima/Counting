package com.example.counting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_congratulations.*

class Congratulations : Activity() {

    val dbHelper = DBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_congratulations)

        results.text = "Режим: " + Status.levels[Status.level] +
                "\nВыполнено: " + Status.solved + "\nОшибок: " + Status.errors
        if (Status.timeMode) {
            var m = (Status.time/1000/60).toString()
            var s = (Status.time/1000 % 60).toString()
            if (m.toInt() < 10)
                m = "0" + m
            if (s.toInt() < 10)
                s = "0" + s

            results.text = results.text.toString() + "\nВремя: " + m + ":" + s
        }

        Log.d("tag123", Status.time.toString())

        if (Status.userID != -1) {
            var db = dbHelper.writableDatabase

            db.execSQL("UPDATE users SET " +
                    "solved = solved + " + Status.solved
                    + ", errors = errors + " + Status.errors
                    + " WHERE id = " + Status.userID
                    + ";")
        }

        again.setOnClickListener {
            val intent = Intent(this, Task::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }

        menu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
