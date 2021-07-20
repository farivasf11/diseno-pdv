package com.example.diseno_pruebas.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import com.example.diseno_prueba.R
import com.example.diseno_prueba.activities.CapturaPedido
import com.google.android.material.bottomsheet.BottomSheetBehavior

class InicialCapturaPedido : Fragment(), CapturaPedido.IFragmentsOnBackPressed {
    lateinit var buscador : EditText
    lateinit var listaProductos: LinearLayout
    lateinit var layoutSheet: LinearLayout
    lateinit var bottomSheet : (BottomSheetBehavior<View>)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.inicial_captura_pedido, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buscador = view.findViewById(R.id.buscador_productos)
        buscador.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                activity?.supportFragmentManager?.beginTransaction()
                    ?.add(R.id.frame_contenedor_captura_pedido, BuscadorProductos())?.commit()
            }
        })
        layoutSheet = view.findViewById(R.id.sheet)
        bottomSheet= BottomSheetBehavior.from(layoutSheet)

        bottomSheet.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            isGestureInsetBottomIgnored = true
        }
    }

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

    companion object {
        fun newInstance() =
            InicialCapturaPedido().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}