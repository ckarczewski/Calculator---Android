package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity() {

//    private val display: TextView = findViewById(R.id.textView)
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
        var display : TextView = findViewById(R.id.textView)
        if (view is Button) {
            if (view.text == "."){
                if (canDecimal) { display.append(view.text) }
                canDecimal = false
            } else
                display.append(view.text)
            canOperation = true
        }
    }

    fun operationAction(view: View) {
        var display : TextView = findViewById(R.id.textView)
        if (view is Button && canOperation) {
            display.append(view.text)
            canOperation = false
            canDecimal = true
        }
    }

    fun clearBtn(view: View) {
        var display : TextView = findViewById(R.id.textView)
        val length = display.length()
        if (length > 0) {
            display.text = display.text.subSequence(0, length - 1)
        }
    }

    fun okBtn(view: View) {
        var display : TextView = findViewById(R.id.textView)
        display.text = ""
    }

    fun equalBtn(view: View) {

    }
}