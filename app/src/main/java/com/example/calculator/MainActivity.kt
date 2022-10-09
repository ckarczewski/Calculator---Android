package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)

        var button_simp : Button = findViewById<Button>(R.id.simple_calc)

        button_simp.setOnClickListener(){
            setContentView(R.layout.simple_calc)
        }


    }
}