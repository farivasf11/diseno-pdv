package com.example.diseno_prueba.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import com.example.diseno_prueba.R

class MainActivity : AppCompatActivity() {
    lateinit var mesaUno : LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar_principal))

        mesaUno = findViewById(R.id.mesa_uno)
        mesaUno.setOnClickListener(
            View.OnClickListener {
                startActivity(Intent(this, CapturaPedido::class.java ))
            }
        )
    }
}

