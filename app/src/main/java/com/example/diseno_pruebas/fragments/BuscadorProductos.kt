package com.example.diseno_pruebas.fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.diseno_prueba.R
import com.example.diseno_prueba.activities.CapturaPedido

class BuscadorProductos : Fragment(), CapturaPedido.IFragmentsOnBackPressed{

    lateinit var buscador: EditText
    lateinit var actividad: CapturaPedido
    lateinit var busqueda: String
    private val model: CapturaPedido.BuscadorProductosViewModel by activityViewModels<CapturaPedido.BuscadorProductosViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        busqueda = savedInstanceState?.getString("BUSQUEDA").toString()
        return inflater.inflate(R.layout.fragment_buscador_productos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actividad = activity as CapturaPedido
        actividad.botonRealizarPedido.visibility = View.GONE
        actividad.toolbar.title = "Buscar productos"

        Log.i("Buscar","QUQUQUUQ")
        busqueda?.let { Log.i("Busqueda", it) }

        buscador = view.findViewById(R.id.buscador_productos)
        buscador.setText(busqueda)
        buscador.apply {
            showKeyboard()
            requestFocus()
            setOnEditorActionListener(object : TextView.OnEditorActionListener {
                override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        actividad.onBackPressed()
                        model.capturarPalabra(buscador.text.toString())
                        return true;
                    }
                    return false;
                }
            })
        }
        model.capturarPalabra("Hola")
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
        fun newInstance(busqueda: String) =
            BuscadorProductos().apply {
                arguments = Bundle().apply {putString("BUSQUEDA", busqueda)}
            }
    }

    override fun onBackPressed(): Boolean {
        actividad.toolbar.title = "Tomar pedido"
        actividad.botonRealizarPedido.visibility = View.VISIBLE
        buscador?.hideKeyboard()
        return true
    }

}