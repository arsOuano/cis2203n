package com.example.scratch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class CalculatorActivity : AppCompatActivity() {
    var currentInput = ""
    var firstOperand = 0.0
    var currentOperator = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
    }

    fun addNumbers(a: Double, b: Double): Double {
        return a + b
    }
}
