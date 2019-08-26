package com.example.counting

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context : Context) : SQLiteOpenHelper (context, "databaseCounting", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY, "
                                            + "name TEXT NOT NULL, "
                                            + "password TEXT, "
                                            + "solved INTEGER DEFAULT 0, "
                                            + "errors INTEGER DEFAULT 0 "
                                            + ");")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}