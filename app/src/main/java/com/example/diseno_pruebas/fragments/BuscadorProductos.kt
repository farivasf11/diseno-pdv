package com.example.diseno_pruebas.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.example.diseno_prueba.R
import com.example.diseno_prueba.activities.CapturaPedido

class BuscadorProductos : Fragment(), CapturaPedido.IFragmentsOnBackPressed{

    lateinit var buscador: EditText
    lateinit var actividad: CapturaPedido

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_buscador_productos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actividad = activity as CapturaPedido
        actividad.botonRealizarPedido.visibility = View.GONE
        actividad.toolbar.title = "Buscar productos"

        buscador = view.findViewById(R.id.buscador_productos)
        buscador.requestFocus()
        buscador.showKeyboard()


    }

    private fun View.showKeyboard(){
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    private fun View.hideKeyboard(){
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    companion object {
        fun newInstance() =
            BuscadorProductos().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onBackPressed(): Boolean {
        actividad.toolbar.title = "Tomar pedido"
        actividad.botonRealizarPedido.visibility = View.VISIBLE
        buscador?.hideKeyboard()
        return true
    }

}