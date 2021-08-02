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
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.adapters.ProductosAdapter
import com.example.diseno_prueba.R
import com.example.diseno_prueba.activities.CapturaPedido
import com.example.models.Producto

class BuscadorProductos : Fragment(), CapturaPedido.IFragmentsOnBackPressed{
    private val model: CapturaPedido.BuscadorProductosViewModel by activityViewModels<CapturaPedido.BuscadorProductosViewModel>()

    lateinit var buscador: EditText
    lateinit var recyclerProductos: RecyclerView
    lateinit var actividad: CapturaPedido
    lateinit var productosTodos: List<Producto>

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

        recyclerProductos = view.findViewById(R.id.recycler_listado_productos)
        loadRecyclerProductos()

        buscador = view.findViewById(R.id.buscador_productos)
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

            addTextChangedListener(object: TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    if (!buscador.text.toString().isEmpty()){
                        val resultadoBusqueda = productosTodos.filter { it.nombre.contains(buscador.text.toString(), true) }
                        val adapterProductos = ProductosAdapter(resultadoBusqueda, actividad.LISTA_BUSQUEDA, recyclerProductos)
                        recyclerProductos.adapter = adapterProductos
                    } else {
                        recyclerProductos.adapter = null
                    }
                }
            })
        }

        model.palabraCapturada.observe(viewLifecycleOwner, Observer<String> {
            buscador.setText(it)
        })
    }


    private fun loadRecyclerProductos(){
        productosTodos = actividad.productosTodos
        recyclerProductos.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        (recyclerProductos.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
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
        buscador.hideKeyboard()
        return true
    }

}