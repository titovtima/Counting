package com.example.counting

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

//class MainActivity : AppCompatActivity() {
class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start.setOnClickListener {
            try {
                Status.need = task.text.toString().toInt()
                val intent = Intent(this, Task::class.java)

                startActivity(intent)
            } catch (e : Exception){
                Toast.makeText(this, "Введите количество заданий", Toast.LENGTH_SHORT).show()
            }
        }

        task.setText(Status.need.toString())

//        task.addTextChangedListener(object : TextWatcher {
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

        val lvladap = ArrayAdapter(this, android.R.layout.simple_list_item_1, Status.levels)
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
}
