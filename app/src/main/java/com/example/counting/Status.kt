package com.example.counting

import android.content.SharedPreferences

class Status {
    companion object {
        var solved = 0
        var need = 7
        var errors = 0
        var level = 2
        var userID = -1
        var userName = "Гость"
        var showpassw = false
        val nameFileSettings = "settings"
        val saveNameKey = "name"
        val saveIdKey = "id"
        val saveNeedKey = "tasksNeed"
        val saveLevelKey = "level"
        val saveTimeModeKey = "timeMode"
        var timeMode = false
        var time : Long = 0
        var loaded = false

        var existID : MutableList<Int> = emptyList<Int>().toMutableList()

        var contextForSignDialog : UsersActivity? = null

        var saveSettings : SharedPreferences? = null

        val levels = arrayOf("Таблица умножения", "Уровень 1", "Уровень 2", "Уровень 3", "Уровень 4")

//        fun makeEnding() : String {
//            if ((this.need % 10 > 1) && (this.need % 10 < 5)
//                && ((this.need % 100 < 10) || (this.need % 100 > 15))) return "а"
//            return ""
//        }
//
//        fun makeEnding(i : Int) : String {
//            if ((i % 10 > 1) && (i % 10 < 5) && ((i % 100 < 10) || (i % 100 > 15))) return "а"
//            return ""
//        }
    }
}