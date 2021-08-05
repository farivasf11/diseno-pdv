package com.example.diseno_pruebas.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.diseno_prueba.R
import com.example.diseno_prueba.activities.CapturaPedido

class MasOpcionesAgregar : Fragment(), CapturaPedido.IFragmentsOnBackPressed {
    lateinit var actividad: CapturaPedido

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mas_opciones_agregar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actividad = activity as CapturaPedido
        actividad.botonRealizarPedido.text = "Agregar"
        actividad.toolbar.title = "Adicionar"

    }

    override fun onBackPressed(): Boolean {
        actividad.toolbar.title = "Tomar pedido"
        actividad.botonRealizarPedido.text = "Revisar pedido"
        return true
    }
}