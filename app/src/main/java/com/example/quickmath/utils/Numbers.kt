package com.example.quickmath.utils

fun getNumbersAsList(number: Int): List<Int>{
    val numberList = mutableListOf<Int>()

    repeat(number){
       numberList.add((Math.random() * 10).toInt())
    }

    return numberList
}