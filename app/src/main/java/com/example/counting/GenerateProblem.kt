package com.example.counting

import kotlin.random.Random

class GenerateProblem {
    companion object {
        data class Problem(val a: Int, val b: Int, val res: Int, val action: String)

        fun generateAddition(minAdd: Int, maxAdd: Int): Problem {
            val a = Random.nextInt(minAdd, maxAdd)
            val b = Random.nextInt(minAdd, maxAdd)
            return Problem(a, b, a + b, "+")
        }

        fun generateSubstraction(minRes: Int, maxRes: Int): Problem {
            val res = Random.nextInt(minRes, maxRes)
            val b = Random.nextInt(minRes, maxRes)
            return Problem(res + b, b, res, "-")
        }

        fun generateMultiplication(minMult: Int, maxMult: Int): Problem {
            val a = Random.nextInt(minMult, maxMult)
            val b = Random.nextInt(minMult, maxMult)
            return Problem(a, b, a * b, "*")
        }

        fun generateDivision(minRes: Int, maxRes: Int): Problem {
            val res = Random.nextInt(minRes, maxRes)
            val b = Random.nextInt(minRes, maxRes)
            return Problem(res * b, b, res, "÷")
        }
    }
}