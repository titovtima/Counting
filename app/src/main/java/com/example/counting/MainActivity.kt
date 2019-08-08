package com.example.counting

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

//class MainActivity : AppCompatActivity() {
class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start.setOnClickListener {
            Status.need = task.text.toString().toInt()
            val intent = Intent(this, Task::class.java)

            startActivity(intent)
        }

        task.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                try {
                    raz.text = "раз" + Status.makeEnding(task.text.toString().toInt())
                } catch (e : Exception){
                    raz.text = "раз"
                }
            }
        })
    }
}
