package com.example.calculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Info : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info)

        val buttonBack: Button = findViewById((R.id.back))

        buttonBack.setOnClickListener() {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
//            setContentView(R.layout.menu)
        }

        val info : TextView = findViewById(R.id.textViewInfo)
        info.append("Kalkulator \n")
        info.append("Autor: Cezary Karczewski 229268 \n")
        info.append("Kalkulator posiada dwie wersję - prostą oraz zaawansowaną. Wersja prosta wykonuja podstawowe działania, wersja zaawansowana posiada dodatkowe funkcje.")
    }
}