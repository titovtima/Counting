package com.example.counting

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
            val db = dbHelper.writableDatabase
            val usname = view.username.text.toString()

            val cur = db.rawQuery("SELECT id, password FROM users WHERE name = ?;", arrayOf(usname))

            if (cur.moveToFirst()) {
                if (view.password.text.toString() == cur.getString(cur.getColumnIndex("password"))) {

                    val id = cur.getInt(cur.getColumnIndex("id"))

                    val dialog = AlertDialog.Builder(Status.contextForSignDialog)
                    dialog.setTitle("Вы уверены, что хотите удалить пользователя " + usname + "?")
                    dialog.setMessage("Все данные пользователя будут удалены")
                    dialog.setPositiveButton("Удалить", { dialog, which ->
                        db.execSQL("DELETE FROM users WHERE id = ?;", arrayOf(id))
                        Status.existID.remove(id)
                        dialog.dismiss()
                    })
                    dialog.setNegativeButton("Отмена", { dialog, which ->
                        dialog.cancel()
                    })
                    dialog.setCancelable(true)

                    dialog.show()
                } else {
                    Toast.makeText(Status.contextForSignDialog, "Вы ввели неверный пароль",
                        Toast.LENGTH_SHORT).show()
                    view.password.setText("")
                }
            } else {
                Toast.makeText(Status.contextForSignDialog, "Пользователя с таким именем не существует",
                    Toast.LENGTH_SHORT).show()
            }
            cur.close()
        }

        view.showpassw.setOnClickListener {
            if (Status.showpassw){
                view.password.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
                Status.showpassw = false
                view.showpassw.text = "Показать"
                view.password.setSelection(view.password.text.toString().length)
            } else {
                view.password.inputType = InputType.TYPE_CLASS_NUMBER
                Status.showpassw = true
                view.showpassw.text = "Скрыть"
                view.password.setSelection(view.password.text.toString().length)
            }
        }

        view.guest.setOnClickListener {
            Status.userID = -1
            Status.userName = "Гость"
            dismiss()
        }

        return view
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)

        Status.contextForSignDialog!!.update()
    }

}