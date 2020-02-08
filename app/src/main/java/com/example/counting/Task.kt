package com.example.counting

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.RelativeLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_task.*
import java.lang.Exception

class Task : AppCompatActivity() {

    var a : Int = 0
    var b : Int = 0
    var action : Int = 0
    var rightAns : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        if (Status.timeMode){
            time.visibility = View.VISIBLE
            val par = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
            par.addRule(RelativeLayout.BELOW, time.id)
            par.addRule(RelativeLayout.CENTER_HORIZONTAL)
            answer.layoutParams = par
        } else {
            time.visibility = View.INVISIBLE
            val par = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
            par.addRule(RelativeLayout.BELOW, problem.id)
            par.addRule(RelativeLayout.CENTER_HORIZONTAL)
            answer.layoutParams = par
        }

        Status.time = 0
        Status.solved = 0
        Status.errors = 0
//        statustext.text =
//            "Задание: " + Status.need + " раз" + Status.makeEnding() +
//                    "\nСделано: " + Status.solved + "\nОшибок: " + Status.errors
        statustext.text = "Необходимо: " + Status.need + "\nСделано: " + Status.solved + "\nОшибок: " + Status.errors
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

    override fun onResume() {
        super.onResume()

        problem.background = null

        if (Status.timeMode) {
            time.base = SystemClock.elapsedRealtime() - Status.time
            time.start()
        }
    }

    override fun onPause() {
        super.onPause()

        problem.setBackgroundColor(resources.getColor(R.color.colorDarkGrey))

        if (Status.timeMode) {
            Status.time = SystemClock.elapsedRealtime() - time.base
            time.stop()
            Log.d("tag123", Status.time.toString())
        }
    }

    fun checkAns(){
        try {
            if (answer.text.toString().toInt() == rightAns) {
                whenOneSolved()
            } else {
                Toast.makeText(this, "Неправильно", Toast.LENGTH_SHORT).show()
                answer.text.clear()
                Status.errors++
                statustext.text = "Необходимо: " + Status.need +
                        "\nСделано: " + Status.solved + "\nОшибок: " + Status.errors
            }
        } catch (e : Exception){
            Toast.makeText(this, "Введите число", Toast.LENGTH_SHORT).show()
        }
    }

    fun whenOneSolved() {
        Toast.makeText(this, "Правильно!", Toast.LENGTH_SHORT).show()
        answer.text.clear()
        Status.solved++
        if (Status.solved == Status.need) {
            whenAllSolved()
        } else {
            generateProblem()
            statustext.text = "Необходимо: " + Status.need +
                    "\nСделано: " + Status.solved + "\nОшибок: " + Status.errors
        }
    }

    fun whenAllSolved() {
        if (Status.timeMode) {
            Status.time = SystemClock.elapsedRealtime() - time.base
            time.stop()
            Log.d("tag123", Status.time.toString())
        }
        val intent = Intent(this, Congratulations::class.java)
        startActivity(intent)
    }

    fun generateProblem(){
        a = 0
        b = 0
        when (Status.level){
            0 -> {
                action = (Math.random()*2).toInt()
                a = (Math.random()*20).toInt()
                b = (Math.random()*(31 - a)).toInt()
                if (action == 0) rightAns = a+b
                else {
                    rightAns = a
                    a += b
                }
            }
            1 -> {
                action = (Math.random()*4).toInt()
                if ((action == 0)||(action == 1)){
                    a = (Math.random()*100).toInt()
                    b = (Math.random()*(201 - a)).toInt()
                    if (action == 0) rightAns = a+b
                    else {
                        rightAns = a
                        a += b
                    }
                }
                if ((action == 2)||(action == 3)){
                    while (a == 0) a = (Math.random()*10).toInt()
                    while (b == 0) b = (Math.random()*10).toInt()
                    if (action == 2) rightAns = a*b
                    else {
                        rightAns = a
                        a *= b
                    }
                }
            }
            2 -> {
                action = (Math.random()*4).toInt()

                var maxSum = 1
                for (i in 1..Status.level){
                    maxSum *= 10
                }
                if (action == 0){
                    a = (Math.random()*1000).toInt()
                    b = (Math.random()*(1001 - a)).toInt()
//                    problem.text = a.toString() + " + " + b.toString()
                    rightAns = a + b
                }
                if (action == 1){
                    a = (Math.random()*1000).toInt()
                    b = (Math.random()*(1001 - a)).toInt()
                    rightAns = a
                    a += b
//                    problem.text = a.toString() + " - " + b.toString()
                }
                if ((action == 2)||(action == 3)){
                    val type = (Math.random()*(Status.level - 1)).toInt()
                    if (type == 0){
                        while (a == 0) a = (Math.random()*10).toInt()
                        while (b == 0) b = (Math.random()*10).toInt()
                        var step = (Math.random()*3).toInt()
                        while (step > 0){
                            a *= 10
                            step--
                        }
                        step = (Math.random()*2).toInt()
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
                    if (action == 2) {
//                        problem.text = a.toString() + " * " + b.toString()
                        rightAns = a * b
                    }
                    if (action == 3){
                        rightAns = a
                        a *= b
//                        problem.text = a.toString() + " / " + b.toString()
                    }
                }
            }
            3 -> {
                action = (Math.random()*4).toInt()

                if (action < 2){
                    a = (Math.random()*5000).toInt()
                    b = (Math.random()*5000).toInt()
                    if (action == 0) rightAns = a+b
                    else {
                        rightAns = a
                        a += b
                    }
                }
                if ((action == 2)||(action == 3)){
                    while (a == 0) a = (Math.random()*100).toInt()
                    while (b == 0) b = (Math.random()*100).toInt()
                    if (action == 2) rightAns = a*b
                    else {
                        rightAns = a
                        a *= b
                    }
                }
            }
            4 -> {
                action = (Math.random()*2).toInt() + 2
                while (a == 0) a = (Math.random()*10).toInt()
                while (b == 0) b = (Math.random()*10).toInt()
                if (action == 2) rightAns = a*b
                else {
                    rightAns = a
                    a *= b
                }
            }
        }
        when (action){
            0 -> problem.text = a.toString() + " + " + b.toString()
            1 -> problem.text = a.toString() + " - " + b.toString()
            2 -> problem.text = a.toString() + " * " + b.toString()
            3 -> problem.text = a.toString() + " / " + b.toString()
        }
    }
}