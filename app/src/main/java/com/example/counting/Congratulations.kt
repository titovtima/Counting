package com.example.counting

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_congratulations.*

class Congratulations : Activity() {

    val dbHelper = DBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_congratulations)

        results.text = "Режим: " + Status.levels[Status.level] +
                "\nВыполнено: " + Status.solved + "\nОшибок: " + Status.errors

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
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }

        menu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
}
