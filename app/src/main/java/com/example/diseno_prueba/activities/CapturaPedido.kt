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
import androidx.appcompat.widget.Toolbar

import com.example.diseno_prueba.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import android.view.View.OnTouchListener as OnTouchListener

class CapturaPedido : AppCompatActivity(), OnTouchListener{
    lateinit var toolbar: Toolbar
    lateinit var buscador: EditText
    lateinit var draggableLayout: LinearLayout
    lateinit var bottomSheet : (BottomSheetBehavior<View>)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_captura_pedido)
        toolbar = findViewById(R.id.toolbar_captura_pedido)
        buscador = findViewById(R.id.buscador_productos)
        draggableLayout = findViewById(R.id.draggable_zone)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        buscador.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus){
                hideKeyboard(v)
            }
        })
        bottomSheet= BottomSheetBehavior.from(findViewById(R.id.sheet))
        bottomSheet.apply {
            peekHeight = 170
            isDraggable = false
        }


    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun hideKeyboard (v: View){
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
    }

    override fun onTouch(v: View, event: MotionEvent?): Boolean {
        Log.i("entro","que pedo")
        Log.i("view",v.toString())
        if (v != null) if (v.equals(findViewById(R.id.draggable_zone))){
            bottomSheet.apply { isDraggable = true }
        } else {
            bottomSheet.apply { isDraggable = false }
        }
        return true
    }
}