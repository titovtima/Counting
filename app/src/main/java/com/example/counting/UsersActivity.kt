package com.example.counting

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_users.*
import java.lang.Exception

class UsersActivity : AppCompatActivity() {

    val dbHelper = DBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)



        exit.setOnClickListener {
            Status.contextForSignDialog = this
            val signingDialog = SigningFragment()

            signingDialog.show(supportFragmentManager, "signingDialog")
        }

        update()

        menu.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    fun update() {
        textuser.text = "Пользователь: " + Status.userName

        try {
            if (Status.userID != -1) {
                val db = dbHelper.readableDatabase

                val cur = db.rawQuery("SELECT solved, errors FROM users WHERE id = ?;", arrayOf(Status.userID.toString()))

                if (!cur.moveToFirst()){
                    cur.close()
                    throw Exception()
                }
                var solved = cur.getInt(cur.getColumnIndex("solved"))
                var errors = cur.getInt(cur.getColumnIndex("errors"))

                cur.close()

                allTime.text = "Всего выполнено: " + solved + "\nОшибок: " + errors
            } else {
                allTime.text = "Для пользователя \"Гость\" статистика не сохраняется"
            }
        } catch (e : Exception) {
            Toast.makeText(this, "Невозможно найти данные для пользователя", Toast.LENGTH_SHORT).show()
        }
    }
}
