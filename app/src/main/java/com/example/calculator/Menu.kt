package com.example.calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)
        val buttonSimp: Button = findViewById(R.id.simple_calc)
        val buttonAdv: Button = findViewById(R.id.adv_calc)
        val buttonInf: Button = findViewById(R.id.info)

        buttonSimp.setOnClickListener() {
            val intent = Intent(this, SimpleCalc::class.java)
            startActivity(intent)

        }

        buttonAdv.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        buttonInf.setOnClickListener() {
            val intent = Intent(this, Info::class.java)
            startActivity(intent)
        }
    }
}