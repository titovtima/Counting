package com.example.counting

import android.app.AlertDialog
import android.content.DialogInterface
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.signing_fragment.view.*

class SigningFragment : DialogFragment() {

    val dbHelper = DBHelper(Status.contextForSignDialog!!)
    var main_view : View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        main_view = inflater.inflate(R.layout.signing_fragment, null)

        main_view!!.reg.setOnClickListener {
            onClickReg(main_view!!.username.text.toString(), main_view!!.password.text.toString())
        }

        main_view!!.signin.setOnClickListener {
            onClickSignIn(main_view!!.username.text.toString(), main_view!!.password.text.toString())
        }

        main_view!!.delete.setOnClickListener {
            onClickDelete(main_view!!.username.text.toString(), main_view!!.password.text.toString())
        }

        main_view!!.showpassw.setOnClickListener {
            onClickShowPassw()
        }

        main_view!!.guest.setOnClickListener {
            Status.userID = -1
            Status.userName = "Гость"
            dismiss()
        }

        return main_view
    }

    private fun addUser(db : SQLiteDatabase, name : String, password : String) {
        var id = 1
        while ((id <= Status.existID.size)&&(Status.existID[id-1] == id))
            id++
        db.execSQL("INSERT INTO users (id, name, password) VALUES (?, ?, ?);",
            arrayOf(id, name, password))
        Status.userID = id
        Status.userName = name
    }

    private fun deleteUser(db: SQLiteDatabase, id : Int, name : String) {
        val dialog = AlertDialog.Builder(Status.contextForSignDialog)
        dialog.setTitle("Вы уверены, что хотите удалить пользователя " + name + "?")
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
    }

    private fun onClickReg(usname : String, password : String) {
        val db = dbHelper.writableDatabase

        val cur = db.rawQuery("SELECT * FROM users WHERE name = ?;", arrayOf(usname))
        if (cur.moveToFirst()){
            Toast.makeText(this.context, "Пользователь с таким именем уже существует", Toast.LENGTH_SHORT).show()
            cur.close()
        } else {
            cur.close()
            addUser(db, usname, password)
            dismiss()
        }
    }

    private fun onClickSignIn(usname : String, password : String) {
        val db = dbHelper.readableDatabase

        val cur = db.rawQuery("SELECT id, password FROM users WHERE name = ?;",
            arrayOf(usname))

        if (cur.moveToFirst()){
            if (cur.getString(cur.getColumnIndex("password")) == password){
                Status.userID = cur.getInt(cur.getColumnIndex("id"))
                Status.userName = usname
                dismiss()
            } else {
                Toast.makeText(this.context, "Вы ввели неверный пароль", Toast.LENGTH_SHORT).show()
                main_view!!.password.setText("")
            }
        } else {
            Toast.makeText(this.context, "Пользователя с таким именем не существует", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onClickDelete(usname : String, password : String) {
        val db = dbHelper.writableDatabase

        val cur = db.rawQuery("SELECT id, password FROM users WHERE usname = ?;", arrayOf(usname))

        if (cur.moveToFirst()) {
            if (password == cur.getString(cur.getColumnIndex("password"))) {
                deleteUser(db, cur.getInt(cur.getColumnIndex("id")), usname)
            } else {
                Toast.makeText(Status.contextForSignDialog, "Вы ввели неверный пароль",
                    Toast.LENGTH_SHORT).show()
                main_view!!.password.setText("")
            }
        } else {
            Toast.makeText(Status.contextForSignDialog, "Пользователя с таким именем не существует",
                Toast.LENGTH_SHORT).show()
        }
        cur.close()
    }

    private fun onClickShowPassw() {
        if (Status.showpassw){
            main_view!!.password.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD
            Status.showpassw = false
            main_view!!.showpassw.text = "Показать"
            main_view!!.password.setSelection(main_view!!.password.text.toString().length)
        } else {
            main_view!!.password.inputType = InputType.TYPE_CLASS_NUMBER
            Status.showpassw = true
            main_view!!.showpassw.text = "Скрыть"
            main_view!!.password.setSelection(main_view!!.password.text.toString().length)
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)

        Status.contextForSignDialog!!.update()
    }

}