package com.example.diseno_pruebas.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.example.diseno_prueba.R
import com.example.diseno_prueba.activities.CapturaPedido

class InicialCapturaPedido : Fragment(), CapturaPedido.IFragmentsOnBackPressed {
    lateinit var buscador : EditText
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
                activity?.title ="Buscar productos"
                activity?.supportFragmentManager?.beginTransaction()
                    ?.add(R.id.frame_contenedor_captura_pedido, BuscadorProductos())?.commit()
            }
        })
    }

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