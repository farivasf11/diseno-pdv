package com.example.diseno_pruebas.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.adapters.CategoriasAdapter
import com.example.adapters.ProductosAdapter
import com.example.diseno_prueba.R
import com.example.diseno_prueba.activities.CapturaPedido
import com.google.android.material.bottomsheet.BottomSheetBehavior

class InicialCapturaPedido : Fragment(), CapturaPedido.IFragmentsOnBackPressed {
    lateinit var buscador : EditText
    lateinit var listaProductos: LinearLayout
    lateinit var layoutSheet: LinearLayout
    lateinit var bottomSheet : (BottomSheetBehavior<View>)
    lateinit var coordinatorLayout : CoordinatorLayout
    lateinit var recyclerCategorias : RecyclerView
    lateinit var recyclerProductos : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.inicial_captura_pedido, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findViews(view)
        loadBuscador()
        loadBottomSheet()
        loadRecyclerCategorias()
        loadRecyclerProductos()
    }

    private fun findViews(view: View){
        buscador = view.findViewById(R.id.buscador_productos)
        layoutSheet = view.findViewById(R.id.sheet)
        recyclerCategorias = view.findViewById(R.id.recycler_categorias)
        recyclerProductos = view.findViewById(R.id.recycler_listado_productos)
        listaProductos = view.findViewById(R.id.lista_productos)
        coordinatorLayout = view.findViewById(R.id.coordinator_layout_agregar_productos)
    }

    private fun loadBuscador() {
        buscador.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                activity?.supportFragmentManager?.beginTransaction()
                    ?.add(R.id.frame_contenedor_captura_pedido, BuscadorProductos())?.commit()
            }
        })
    }

    private fun loadBottomSheet(){
        bottomSheet= BottomSheetBehavior.from(layoutSheet)
        bottomSheet.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            isGestureInsetBottomIgnored = false
            isDraggable = false
        }

    }

    private fun loadRecyclerCategorias(){
        recyclerCategorias.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val dataset = arrayOf("Favoritos","Hamburguesa", "Tortas", "Pizzas", "Burritos", "Desayunos")
        val adapterCategorias = CategoriasAdapter(dataset)
        recyclerCategorias.adapter = adapterCategorias
    }

    private fun loadRecyclerProductos(){
        recyclerProductos.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val productos = arrayOf(
            ProductosAdapter.Producto("Papa","$50"),
            ProductosAdapter.Producto("Hamburguesa","$60"),
            ProductosAdapter.Producto("Torta de Pierna","$40"),
            ProductosAdapter.Producto("Enchiladas","$45"),
            ProductosAdapter.Producto("Papa","$50"),
            ProductosAdapter.Producto("Hamburguesa","$60"),
            ProductosAdapter.Producto("Torta de Pierna","$40"),
            ProductosAdapter.Producto("Enchiladas","$45"),
            ProductosAdapter.Producto("Papa","$50"),
            ProductosAdapter.Producto("Hamburguesa","$60"),
            ProductosAdapter.Producto("Torta de Pierna","$40"),
            ProductosAdapter.Producto("Enchiladas","$45"))
        val adapterProductos = ProductosAdapter(productos)
        (recyclerProductos.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerProductos.adapter = adapterProductos
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