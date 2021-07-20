package com.example.diseno_pruebas.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.diseno_prueba.R
import com.example.diseno_prueba.activities.CapturaPedido

class BuscadorProductos : Fragment(), CapturaPedido.IFragmentsOnBackPressed {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_buscador_productos, container, false)
    }

    companion object {
        fun newInstance() =
            BuscadorProductos().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onBackPressed(): Boolean {
        Toast.makeText(activity, "Back", Toast.LENGTH_LONG)
        return true
    }

}