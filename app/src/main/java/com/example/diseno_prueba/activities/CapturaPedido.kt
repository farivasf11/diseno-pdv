package com.example.diseno_prueba.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import androidx.appcompat.widget.Toolbar
import com.example.diseno_prueba.R
import com.example.diseno_pruebas.fragments.InicialCapturaPedido
import com.google.android.material.button.MaterialButton

class CapturaPedido : AppCompatActivity(){
    lateinit var toolbar: Toolbar
    lateinit var botonRealizarPedido: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_captura_pedido)
        toolbar = findViewById(R.id.toolbar_captura_pedido)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        botonRealizarPedido = findViewById(R.id.boton_realizar_pedido)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = InicialCapturaPedido()

        fragmentTransaction.add(R.id.frame_contenedor_captura_pedido, fragment)
        fragmentTransaction.commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        val fragment = this.supportFragmentManager.findFragmentById(R.id.frame_contenedor_captura_pedido)
        (fragment as? IFragmentsOnBackPressed)?.onBackPressed()?.not()?.let {
            super.onBackPressed()
        }
    }

    interface IFragmentsOnBackPressed{
        fun onBackPressed(): Boolean
    }
}

