package com.example.counting

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private val dbHelper = DBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadDBdata()
        setButtonsListeners()
        setNubmerTasks()
        setLevelsList()
    }

    private fun update(){
        userbutton.text = Status.userName
    }

    override fun onResume() {
        super.onResume()

        update()
    }

    override fun onStop() {
        super.onStop()

        try { Status.need = number_tasks.text.toString().toInt() } catch (e : Exception){}

        val editor = Status.saveSettings!!.edit()
        editor.putInt(Status.saveLevelKey, Status.level)
        editor.putInt(Status.saveNeedKey, Status.need)
        editor.apply()
    }

    private fun setNubmerTasks() {
        number_tasks.setText(Status.need.toString())

//        number_tasks.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                try {
//                    raz.text = " раз" + Status.makeEnding(task.text.toString().toInt())
//                } catch (e : Exception){
//                    raz.text = " раз"
//                }
//            }
//        })
    }

    private fun setLevelsList() {
        val lvladap = ArrayAdapter(this, android.R.layout.simple_spinner_item, Status.levels)
        lvladap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        lvl.adapter = lvladap
        lvl.setSelection(Status.level)
        lvl.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Status.level = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

    private fun loadDBdata() {
        val db = dbHelper.writableDatabase

        val cur = db.rawQuery("SELECT id FROM users;", null)
        if (cur.moveToFirst())
            do {
                Status.existID.add(cur.getInt(cur.getColumnIndex("id")))
            } while (cur.moveToNext())
        cur.close()

        if (!Status.loaded) try {
            Status.saveSettings = getSharedPreferences(Status.nameFileSettings, Context.MODE_PRIVATE)

            if (Status.saveSettings!!.contains(Status.saveIdKey))
                Status.userID = Status.saveSettings!!.getInt(Status.saveIdKey, -1)
            if (Status.saveSettings!!.contains(Status.saveNameKey))
                Status.userName = Status.saveSettings!!.getString(Status.saveNameKey, "Гость")!!
            if (Status.saveSettings!!.contains(Status.saveNeedKey))
                Status.need = Status.saveSettings!!.getInt(Status.saveNeedKey, 7)
            if (Status.saveSettings!!.contains(Status.saveLevelKey))
                Status.level = Status.saveSettings!!.getInt(Status.saveLevelKey, 2)
            if (Status.saveSettings!!.contains(Status.saveTimeModeKey))
                Status.timeMode = Status.saveSettings!!.getBoolean(Status.saveTimeModeKey, false)
            Log.d("tag123", "loaded")
            Status.loaded = true
        } catch (e : Exception){}
    }

    private fun setButtonsListeners() {
        userbutton.setOnClickListener {
            val intent = Intent(this, UsersActivity::class.java)

            startActivity(intent)
        }

        settings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)

            startActivity(intent)
        }

        start.setOnClickListener {
            try {
                Status.need = number_tasks.text.toString().toInt()
                val intent = Intent(this, Task::class.java)
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                startActivity(intent)
            } catch (e : Exception){
                Toast.makeText(this, "Введите количество заданий", Toast.LENGTH_SHORT).show()
            }
        }
    }
}