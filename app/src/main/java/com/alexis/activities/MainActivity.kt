package com.alexis.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.diseno_prueba.R
import com.example.diseno_prueba.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var mesaUno : LinearLayout
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBar?.title = ""
        setSupportActionBar(binding.toolbarPrincipal)

        mesaUno = findViewById(R.id.mesa_uno)
        mesaUno.setOnClickListener(
            View.OnClickListener {
                startActivity(Intent(this, CapturaPedido::class.java))
            }
        )
    }
}