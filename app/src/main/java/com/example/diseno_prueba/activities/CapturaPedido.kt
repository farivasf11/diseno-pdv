package com.example.diseno_prueba.activities

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout

import com.example.diseno_prueba.R
import com.example.diseno_pruebas.fragments.InicialCapturaPedido
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import android.view.View.OnTouchListener as OnTouchListener

class CapturaPedido : AppCompatActivity(){
    lateinit var toolbar: Toolbar
    lateinit var buscador: EditText
    lateinit var listaProductos: LinearLayout
    lateinit var sheetBottom: LinearLayout
    lateinit var bottomSheet : (BottomSheetBehavior<View>)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_captura_pedido)
        toolbar = findViewById(R.id.toolbar_captura_pedido)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = InicialCapturaPedido()
        fragmentTransaction.add(R.id.frame_contenedor_captura_pedido, fragment)
        fragmentTransaction.commit()

        /*
        buscador = findViewById(R.id.buscador_productos)
        listaProductos = findViewById(R.id.lista_productos)
        buscador.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus){
                hideKeyboard(v)
            }
        })
        sheetBottom = findViewById(R.id.sheet)

        listaProductos.setOnClickListener { View.OnClickListener {
            Toast.makeText(this, "Holi", Toast.LENGTH_LONG)
        } }
        bottomSheet= BottomSheetBehavior.from(sheetBottom)
        bottomSheet.apply {
            state = STATE_EXPANDED
            isGestureInsetBottomIgnored = true
        }

        bottomSheet.addBottomSheetCallback(object : BottomSheetCallback(){
            override fun onStateChanged(v: View, newState: Int) {
                Log.i("DragListener", v.toString())
                if (newState == STATE_EXPANDED){
                    Log.i("DragListener", "" + newState)
                    bottomSheet.apply { isDraggable = false }
                }
            }
            override fun onSlide(v: View, slideOffset: Float) {
                Log.i("DragListener", v.toString())
            }
        })
        */
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

