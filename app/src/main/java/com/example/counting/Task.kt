package com.example.counting

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_task.*

class Task : AppCompatActivity() {

    var a : Int = 0
    var b : Int = 0
    var action : Int = 0
    var rightAns : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        Status.solved = 0
        Status.errors = 0
        statustext.text =
            "Задание: " + Status.need + " раз\nСделано: " + Status.solved + "\nОшибок: " + Status.errors
        generateProblem()

        submit.setOnClickListener {
            checkAns()
        }

        answer.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                checkAns()
                true
            } else
            false
        }

    }

    fun checkAns(){
        if (answer.text.toString().toInt() == rightAns) {
            Toast.makeText(this, "Правильно!", Toast.LENGTH_SHORT).show()
            answer.text.clear()
            Status.solved++
            if (Status.solved == Status.need) {
                val intent = Intent(this, Congratulations::class.java)

                startActivity(intent)
            } else {
                generateProblem()
                statustext.text =
                    "Задание: " + Status.need + " раз\nСделано: " + Status.solved + "\nОшибок: " + Status.errors
            }
        } else {
            Toast.makeText(this, "Неравильно", Toast.LENGTH_SHORT).show()
            answer.text.clear()
            Status.errors++
            statustext.text =
                "Задание: " + Status.need + " раз\nСделано: " + Status.solved + "\nОшибок: " + Status.errors
        }
    }

    fun generateProblem(){
        action = (Math.random()*4).toInt()
        a = 0
        b = 0

        if (action == 0){
            a = (Math.random()*1000).toInt()
            b = (Math.random()*1000).toInt()
            problem.text = a.toString() + " + " + b.toString()
            rightAns = a + b
        }
        if (action == 1){
            a = (Math.random()*1000).toInt()
            b = (Math.random()*a).toInt()
            problem.text = a.toString() + " - " + b.toString()
            rightAns = a - b
        }
        if (action == 2){
            val type = (Math.random()*2).toInt()
            if (type == 0){
                while (a == 0) a = (Math.random()*10).toInt()
                while (b == 0) b = (Math.random()*10).toInt()
                var step = (Math.random()*3).toInt()
                while (step > 0){
                    a *= 10
                    step--
                }
                step = (Math.random()*3).toInt()
                while (step > 0){
                    b *= 10
                    step--
                }
            }
            if (type == 1){
                while (a == 0) a = (Math.random()*100).toInt()
                while (b == 0) b = (Math.random()*10).toInt()
                if (Math.random() >= 0.5)
                    b *= 10
            }
            problem.text = a.toString() + " * " + b.toString()
            rightAns = a*b
        }
        if (action == 3){
            val type = (Math.random()*2).toInt()
            if (type == 0){
                while (a == 0) a = (Math.random()*10).toInt()
                while (b == 0) b = (Math.random()*10).toInt()
                var step = (Math.random()*3).toInt()
                while (step > 0){
                    a *= 10
                    step--
                }
                step = (Math.random()*3).toInt()
                while (step > 0){
                    b *= 10
                    step--
                }
            }
            if (type == 1){
                while (a == 0) a = (Math.random()*100).toInt()
                while (b == 0) b = (Math.random()*10).toInt()
                if (Math.random() >= 0.5)
                    b *= 10
            }
            rightAns = a
            a *= b
            problem.text = a.toString() + " / " + b.toString()
        }
    }
}