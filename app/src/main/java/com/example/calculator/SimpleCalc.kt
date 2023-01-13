package com.example.calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.util.Log


class SimpleCalc : AppCompatActivity() {

    //    private val display: TextView = findViewById(R.id.textView)
    lateinit var display : TextView
    lateinit var displaySign : TextView
    //    after open calculator
    private var canDecimal = true


    private var numberOne = 0.0
    private var numberOneSave = false
    private var numberTwo = 0.0
    private var numberTwoSave = false

    private var signOperator = ""
    private var clearNumFlag = false

    private var errorFlag = false
    private val maxDouble = Double.MAX_VALUE
    private val minDouble = maxDouble * (-1.0)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simple_calc)

        val buttonBack: Button = findViewById((R.id.back))

        buttonBack.setOnClickListener() {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }


    }

    fun numberAction(view: View) {
        displaySign = findViewById(R.id.textView2)
        display = findViewById(R.id.textView)

        if (view is Button) {
            if (errorFlag){
                display.text = ""
                errorFlag = false
            }
            if (clearNumFlag) {
                display.text = ""
                clearNumFlag = false
            }
            if (view.text == "."){
                if (canDecimal || numberOneSave) { display.append(view.text) }
                canDecimal = false
            } else {
                display.append(view.text)
            }
        }
    }

    fun operationAction(view: View) {
        displaySign = findViewById(R.id.textView2)
        display = findViewById(R.id.textView)

        if (view is Button && errorFlag){
            display.text = ""
            canDecimal = true
            errorFlag = false
        }

        val mainDisplayLength = display.length()
        if (view is Button && mainDisplayLength > 0 && !numberOneSave) {
            displaySign.text = ""
            displaySign.append(view.text)
            signOperator = displaySign.text.toString()
            numberOne = digitsOperators()
            numberOneSave = true
            clearNumFlag = true
        } else if(view is Button && mainDisplayLength > 0) {
            displaySign.text = ""
            displaySign.append(view.text)
            signOperator = displaySign.text.toString()
        }
    }

    fun operationSpecial(view: View) {
        display = findViewById(R.id.textView)
        if (view is Button && errorFlag){
            display.text = ""
            canDecimal = true
            errorFlag = false
        }
        var result = ""
        val mainDisplayLength = display.length()
        if (view is Button && mainDisplayLength > 0) {
            when (view.text){
                "+/-" -> {
                    result = negativeNumber().toString()
                }
            }
            canDecimal = false
            display.text = result
        }
    }

    fun clearBtn(view: View) {
        display = findViewById(R.id.textView)
        if (errorFlag) {
            display.text = ""
            canDecimal = true
            errorFlag = false
        }
        val length = display.length()
        if (length > 0) {
            if (display.text.get(length-1) == '.'){
                println("found dot lol !!!")
                canDecimal = true
            }
            display.text = display.text.subSequence(0, length - 1)
        }
    }

    fun okBtn(view: View) {
        display = findViewById(R.id.textView)
        displaySign = findViewById(R.id.textView2)
        display.text = ""
        displaySign.text = ""

        numberOneSave = false
        clearNumFlag = false
        canDecimal = true
        errorFlag = false
    }

    private fun digitsOperators(): Double{
        display = findViewById(R.id.textView)
        var number = 0.0
        var currentSign = ""
        for(character in display.text){
            if (character.isDigit() || character == '.' || character == '-'){ //make decimal
                currentSign += character
            }
        }
        if (currentSign == "."){
            currentSign = "0.0"
        }
        number = currentSign.toDouble()
        return number
    }
    fun equalBtn(view: View) {
        displaySign = findViewById(R.id.textView2)
        display = findViewById(R.id.textView)

        val isSignChosen = displaySign.length()
        if (isSignChosen > 0) {
            displaySign.text = ""
            numberTwo = digitsOperators()
            display.text = ""
            display.text = calculateResult()
            numberOneSave = false
            canDecimal = false
        }

    }

    private fun calculateResult(): String{
        var result = 0.0
        when (signOperator){
            "x" -> {
                result = numberOne * numberTwo
            }
            "/" -> {
                if (numberTwo == 0.0){
                    errorFlag = true
                    return "Error"
                } else {
                    result = numberOne / numberTwo
                }

            }
            "+" -> {
                result = numberOne + numberTwo
            }
            "-" -> {
                result = numberOne - numberTwo
            }
        }
        if (result > maxDouble) {
            errorFlag = true
            return "Error"
        } else if (result < minDouble) {
            errorFlag = true
            return "Error"

        }
        return result.toString()
    }

    private fun negativeNumber(): Double {
        val number = digitsOperators()
        return number * (-1)
    }

}
