package com.example.diseno_prueba.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
        toolbar.title = "Tomar pedido"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        botonRealizarPedido = findViewById(R.id.boton_realizar_pedido)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = InicialCapturaPedido()

        fragmentTransaction.add(R.id.frame_contenedor_captura_pedido, fragment).setReorderingAllowed(false).commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        val fragment = this.supportFragmentManager.findFragmentById(R.id.frame_contenedor_captura_pedido)
        (fragment as? IFragmentsOnBackPressed)?.onBackPressed()?.not()?.let { popBackStack: Boolean ->
            if (!popBackStack){
                super.onBackPressed()
            }
        }
    }

    class BuscadorProductosViewModel : ViewModel(){
        val palabraCapturada = MutableLiveData<String>()

        fun capturarPalabra(item: String){
            palabraCapturada.value = item
        }
    }
    interface IFragmentsOnBackPressed{
        fun onBackPressed(): Boolean
    }
}

