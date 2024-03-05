package com.example.puzlegame.entity

import android.content.SharedPreferences
import kotlin.random.Random

class Game {
    private val array: IntArray

    init {
        array = intArrayOf(0,0,1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8,9,9)
        shuffleArray()
    }

    public fun getArrElement(number : Int) : Int{
        return array[number]
    }

    private fun shuffleArray() {
        for (i in array.indices) {
            val randomIndex = Random.nextInt(array.size)
            val temp = array[i]
            array[i] = array[randomIndex]
            array[randomIndex] = temp
        }
    }
}