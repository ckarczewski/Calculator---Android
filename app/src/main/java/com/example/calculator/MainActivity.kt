package com.example.calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.util.Log
import kotlin.math.*


class MainActivity : AppCompatActivity() {

//    private val display: TextView = findViewById(R.id.textView)
    lateinit var display :TextView
    lateinit var displaySign :TextView
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
    private val procMax = Double.MIN_VALUE
    private val procMin = procMax * (-1)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.advanced_calc)

        val buttonBack: Button = findViewById((R.id.back))

        buttonBack.setOnClickListener() {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
//            setContentView(R.layout.menu)
        }

    }
    fun goBack(view: View){
        val buttonBack: Button = findViewById((R.id.back))

        buttonBack.setOnClickListener() {
            setContentView(R.layout.menu)
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
            try {
                when (view.text){
                    "+/-" -> {
                        result = negativeNumber().toString()
                    }
                    "sin" -> {
                        result = sine().toString()
                    }
                    "cos" -> {
                        result = cosine().toString()
                    }
                    "tan" -> {
                        result = tangent().toString()
                    }
                    "%" -> {
                        result = precent().toString()
                    }
                    "ln" -> {
                        result = naturalLogarithm()
                    }
                    "√" -> {
                        result = sqrt()
                    }
                    "x²" -> {
                        result = square()
                    }
                }
            }catch (e:java.lang.NumberFormatException){
                errorFlag = true
                result = "Error"
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
        try {
            number = currentSign.toDouble()
        } catch (e:java.lang.NumberFormatException){
            number = 0.0
        }


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
            display.text = calculateResult().toBigDecimal().toPlainString()
            numberOneSave = false
            canDecimal = false
        }

    }

    private fun calculateResult(): String{
        var result = 0.0
        try {
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
                "xⁿ" -> {
                    result = numberOne.pow(numberTwo)
                }
                "log" -> {
                    if (numberOne < 0 || numberTwo < 0 || numberTwo == 1.0) {
                        errorFlag = true
                        return "Error"
                    } else {
                        result = log(numberOne,numberTwo)
                    }

                }
            }
        } catch (e:java.lang.NumberFormatException){
            return "Error"
        }

        if (result > maxDouble || result < minDouble) {
            errorFlag = true
            return "Error"
        }

        return result.toString()
    }

    private fun negativeNumber(): Double {
        val number = digitsOperators()
        return number * (-1)
    }

    private fun sine(): String {
        val number = digitsOperators()
        if (sin(number)> maxDouble || sin(number) < minDouble) {
            errorFlag = true
            return "Error"
        }
        return sin(number).toString()
    }

    private fun cosine(): String {
        val number = digitsOperators()
        if (cos(number)> maxDouble || cos(number) < minDouble) {
            errorFlag = true
            return "Error"
        }
        return cos(number).toString()
    }

    private fun tangent(): String {
        val number = digitsOperators()
        if (tan(number) > maxDouble || tan(number) < minDouble) {
            errorFlag = true
            return "Error"
        }
        return tan(number).toString()
    }

    private fun naturalLogarithm(): String {
        val number = digitsOperators()
        return if (number > 0.0){
            if (ln(number) > maxDouble || ln(number) < minDouble) {
                errorFlag = true
                return "Error"
            }
            ln(number).toString()
        } else {
            errorFlag = true
            "Error"
        }
    }

    private fun precent(): String {
        val number = digitsOperators()
        return (number / 100).toBigDecimal().toPlainString()
    }

    private fun square(): String {
        val number = digitsOperators()
        if (number.pow(2) > maxDouble || number.pow(2) < minDouble) {
            errorFlag = true
            return "Error"
        } else {
            return number.pow(2).toString()
        }

    }

    private fun sqrt(): String {
        val number = digitsOperators()
        return if (number >= 0.0) {
            if (sqrt(number)> maxDouble || sqrt(number) < minDouble) {
                errorFlag = true
                return "Error"
            }
            sqrt(number).toString()
        } else {
            errorFlag = true
            "Error"
        }
    }
}