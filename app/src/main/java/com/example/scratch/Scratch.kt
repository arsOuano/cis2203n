package com.example.scratch

fun main() {
    val name = "Dhonny Jepp"
    var age = 21
    age += 1
    println("My name is $name and I am $age years old.")

    val greeting = greetUser(name)
    println(greeting)
}

fun greetUser(name: String): String {
    return "Hello, $name! Welcome to Kotlin."
}
