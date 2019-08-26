package com.example.counting

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.signing_fragment.view.*

class SigningFragment : DialogFragment() {

    val dbHelper = DBHelper(Status.contextForSignDialog!!)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.signing_fragment, null)
        view.reg.setOnClickListener {
            val db = dbHelper.writableDatabase

            val cur = db.rawQuery("SELECT * FROM users WHERE name = ?;", arrayOf(view.username.text.toString()))
            if (cur.moveToFirst()){
                Toast.makeText(this.context, "Пользователь с таким именем уже существует", Toast.LENGTH_SHORT).show()
                cur.close()
            } else {
                cur.close()
                var id = 1
                while ((id <= Status.existID.size)&&(Status.existID[id-1] == id))
                    id++
                db.execSQL("INSERT INTO users (id, name, password) VALUES (?, ?, ?);",
                        arrayOf(id, view.username.text.toString(), view.password.text.toString()))
                Status.userID = id
                Status.userName = view.username.text.toString()
                dismiss()
            }
        }

        view.signin.setOnClickListener {
            val db = dbHelper.readableDatabase

            val cur = db.rawQuery("SELECT id, password FROM users WHERE name = ?;",
                arrayOf(view.username.text.toString()))

            if (cur.moveToFirst()){
                if (cur.getString(cur.getColumnIndex("password")) == view.password.text.toString()){
                    Status.userID = cur.getInt(cur.getColumnIndex("id"))
                    Status.userName = view.username.text.toString()
                    dismiss()
                } else {
                    Toast.makeText(this.context, "Вы ввели неверный пароль", Toast.LENGTH_SHORT).show()
                    view.password.setText("")
                }
            } else {
                Toast.makeText(this.context, "Пользователя с таким именем не существует", Toast.LENGTH_SHORT).show()
            }
        }

        view.delete.setOnClickListener {

        }

        return view
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)

        Status.contextForSignDialog!!.update()
    }

}