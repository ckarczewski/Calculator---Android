package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity() {

//    private val display: TextView = findViewById(R.id.textView)
    lateinit var display :TextView
    private var canOperation = false
    private var canDecimal = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)

        val buttonSimp: Button = findViewById(R.id.simple_calc)

        buttonSimp.setOnClickListener() {
            setContentView(R.layout.simple_calc)
        }

    }


    fun numberAction(view: View) {
        display = findViewById(R.id.textView)
        if (view is Button) {
            if (view.text == "."){
                if (canDecimal) { display.append(view.text) }
                canDecimal = false
            } else {
                display.append(view.text)
                canOperation = true
            }
        }
    }

    fun operationAction(view: View) {
        display = findViewById(R.id.textView)
        if (view is Button && canOperation) {
            display.append(view.text)
            canOperation = false
            canDecimal = true
        }
    }

    fun clearBtn(view: View) {
        display = findViewById(R.id.textView)
        val length = display.length()
        if (length > 0) {
            display.text = display.text.subSequence(0, length - 1)
        }
    }

    fun okBtn(view: View) {
        display = findViewById(R.id.textView)

        display.text = ""
    }

    fun equalBtn(view: View) {
        display = findViewById(R.id.textView)
        display.text = calculateResult()
    }
//log.i
    private fun calculateResult(): String{
        val digitsOperators = digitsOperators()
        if(digitsOperators.isEmpty()) return ""

        val timeDivision = timesDivisionCalculate(digitsOperators)
        if(timeDivision.isEmpty()) return ""

        val result = addSubtractCalculate(timeDivision)
        return result.toString()
    }
    private fun addSubtractCalculate(passedList: MutableList<Any>): Float{
        var result = passedList[0] as Float

        for (i in passedList.indices){
            if (passedList[i] is Char && i != passedList.lastIndex){
                val operator = passedList[i]
                val nextDigit = passedList[i + 1] as Float
                if (operator == '+'){
                    result += nextDigit
                }
                if (operator == '-'){
                    result -= nextDigit
                }
            }
        }
        return result
    }

    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any>{
        var list = passedList
        while (list.contains('x') || list.contains('/')){
            list = calcTimeDiv(list)
        }
        return list
    }
    private fun calcTimeDiv(passedList: MutableList<Any>):MutableList<Any>{
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size

        for (i in passedList.indices){
            if (passedList[i] is Char && i != passedList.lastIndex && i < restartIndex){
                val operator = passedList[i]
                val prevDigit = passedList[i - 1] as Float
                val nextDigit = passedList[i + 1] as Float
                when (operator){
                    'x' -> {
                        newList.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }
                    '/' -> {
                        newList.add(prevDigit / nextDigit)
                        restartIndex = i + 1
                    }else -> {
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }
            }
            if (i > restartIndex){
                newList.add(passedList[i])
            }
        }
        return newList
    }

    private fun digitsOperators(): MutableList<Any>{
        display = findViewById(R.id.textView)
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for(character in display.text){
            if (character.isDigit() || character == '.'){ //make decimal
                currentDigit += character
            } else {
                list.add(currentDigit.toFloat())
                currentDigit = ""
                list.add(character)
            }
        }
        if (currentDigit != "")
            list.add(currentDigit.toFloat())
        return list
    }
}