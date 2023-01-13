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
                    result = square().toString()
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
            "xⁿ" -> {
                result = numberOne.pow(numberTwo)
            }
            "log" -> {
                if (numberOne > 0 || numberTwo > 0 || numberTwo == 1.0) {
                    result = log(numberOne,numberTwo)
                } else {
                    errorFlag = true
                    return "Error"

                }

            }
        }

        return result.toString()
    }

    private fun negativeNumber(): Double {
        val number = digitsOperators()
        return number * (-1)
    }

    private fun sine(): Double {
        val number = digitsOperators()
        return sin(number)
    }

    private fun cosine(): Double {
        val number = digitsOperators()
        return cos(number)
    }

    private fun tangent(): Double {
        val number = digitsOperators()
        return tan(number)
    }

    private fun naturalLogarithm(): String {
        val number = digitsOperators()
        return if (number > 0.0){
            ln(number).toString()
        } else {
            errorFlag = true
            "Error"
        }
    }

    private fun precent(): Double {
        val number = digitsOperators()
        return number / 100
    }

    private fun square(): Double {
        val number = digitsOperators()
        return number.pow(2)
    }

    private fun sqrt(): String {
        val number = digitsOperators()
        return if (number >= 0.0) {
            sqrt(number).toString()
        } else {
            errorFlag = true
            "Error"
        }
    }

//    fun numberAction(view: View) {
//        display = findViewById(R.id.textView)
//        if (view is Button) {
//            if (view.text == "."){
//                if (canDecimal) { display.append(view.text) }
//                canDecimal = false
//            } else {
//                display.append(view.text)
//                canOperation = true
//            }
//        }
//    }

//    fun operationAction(view: View) {
//        display = findViewById(R.id.textView)
//        if (view is Button && canOperation) {
//            display.append(view.text)
////            after add operation sign
//            canOperation = false
//            println("zmienilem flage decimal na false")
//            canDecimal = true
//        }
//    }

//    fun clearBtn(view: View) {
//        display = findViewById(R.id.textView)
//        val length = display.length()
//        if (length > 0) {
//            if (display.text.get(length-1) == '.'){
//                println("found dot lol !!!")
//                canDecimal = true
//            }
//            display.text = display.text.subSequence(0, length - 1)
//        }
//    }

//    fun okBtn(view: View) {
//        display = findViewById(R.id.textView)
//
//        display.text = ""
//        canDecimal = true
//        canOperation = false
//    }

//    fun equalBtn(view: View) {
//        display = findViewById(R.id.textView)
//        val length = display.length()
//        if (length > 0) {
//            display.text = calculateResult()
//            canOperation = true
//            canDecimal = false
//        }
//
//    }
//log.i
//    private fun calculateResult(): String{
//        val digitsOperators = digitsOperators()
//        if(digitsOperators.isEmpty()) return ""
//
//        val timeDivision = timesDivisionCalculate(digitsOperators)
//        if(timeDivision.isEmpty()) return ""
//
//        val result = addSubtractCalculate(timeDivision)
//        return result.toString()
//    }

//    private fun addSubtractCalculate(passedList: MutableList<Any>): Float{
//        var result = 0.0f as Float
//        if (passedList[0] == '-') {
//            passedList[1] = passedList[1] as Float * (-1)
//            passedList.removeAt(0)
//            result = passedList[0] as Float
//        } else {
//            result = passedList[0] as Float
//        }
//
//
////        var result = passedList[0] as Float
//        println(result)
//        println(passedList)
//        for (i in passedList.indices){
//            if (passedList[i] is Char && i != passedList.lastIndex){
//                val operator = passedList[i]
//                val nextDigit = passedList[i + 1] as Float
//                if (operator == '+'){
//                    result += nextDigit
//                }
//                if (operator == '-'){
//                    result -= nextDigit
//                }
//            }
//        }
//        return result
//    }

//    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any>{
//        var list = passedList
//        while (list.contains('x') || list.contains('/')){
//            list = calcTimeDiv(list)
//        }
//        return list
//    }

//    private fun calcTimeDiv(passedList: MutableList<Any>):MutableList<Any>{
//        val newList = mutableListOf<Any>()
//        var restartIndex = passedList.size
//        println(passedList)
//        for (i in passedList.indices){
//            if (passedList[i] is Char && i != passedList.lastIndex && i < restartIndex){
//                val operator = passedList[i]
//                val prevDigit = passedList[i - 1] as Float
//                val nextDigit = passedList[i + 1] as Float
//                when (operator){
//                    'x' -> {
//                        newList.add(prevDigit * nextDigit)
//                        restartIndex = i + 1
//                    }
//                    '/' -> {
//                        newList.add(prevDigit / nextDigit)
//                        restartIndex = i + 1
//                    }else -> {
//                        newList.add(prevDigit)
//                        newList.add(operator)
//                    }
//                }
//            }
//            if (i > restartIndex){
//                newList.add(passedList[i])
//            }
//        }
//        return newList
//    }

//    private fun digitsOperators(): MutableList<Any>{
//        display = findViewById(R.id.textView)
//        val list = mutableListOf<Any>()
//        var currentDigit = ""
//        for(character in display.text){
//            if (character.isDigit() || character == '.'){ //make decimal
//                currentDigit += character
//            } else {
//                if (currentDigit != ""){
//                    list.add(currentDigit.toFloat())
//                }
//                currentDigit = ""
//                list.add(character)
//            }
//        }
//        if (currentDigit != "")
//            list.add(currentDigit.toFloat())
//        return list
//    }
}