package com.example.counting

class Status {
    companion object {
        var solved = 0
        var need = 7
        var errors = 0

        fun makeEnding() : String {
            if ((this.need % 10 > 1) && (this.need % 10 < 5)
                && ((this.need % 100 < 10) || (this.need % 100 > 15))) return "а"
            return ""
        }
    }
}