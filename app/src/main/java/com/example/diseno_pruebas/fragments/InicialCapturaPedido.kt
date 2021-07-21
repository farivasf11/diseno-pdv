package com.example.diseno_pruebas.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.adapters.CategoriasAdapter
import com.example.adapters.PedidoAdapter
import com.example.adapters.ProductosAdapter
import com.example.diseno_prueba.R
import com.example.diseno_prueba.activities.CapturaPedido
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.button.MaterialButton
import kotlin.collections.ArrayList

class InicialCapturaPedido : Fragment(), CapturaPedido.IFragmentsOnBackPressed {
    lateinit var buscador : EditText
    lateinit var listaProductos: LinearLayout
    lateinit var layoutSheet: LinearLayout
    lateinit var bottomSheet : (BottomSheetBehavior<View>)
    lateinit var coordinatorLayout : CoordinatorLayout
    lateinit var recyclerCategorias : RecyclerView
    lateinit var recyclerProductos : RecyclerView
    lateinit var recyclerPedido : RecyclerView
    lateinit var disminuirComensal : MaterialButton
    lateinit var aumentarComensal : MaterialButton
    lateinit var numeroComensales : TextView
    lateinit var agregarProducto : MaterialButton
    lateinit var agregarComentario : MaterialButton
    lateinit var productos : Array<ProductosAdapter.Producto>
    lateinit var productosPedido : ArrayList<PedidoAdapter.ProductoPedido>
    var comensales = 1

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
        loadRecyclerPedidoComensal()
        setOnClickListeners()
    }

    private fun findViews(view: View){
        buscador = view.findViewById(R.id.buscador_productos)
        layoutSheet = view.findViewById(R.id.sheet)
        recyclerCategorias = view.findViewById(R.id.recycler_categorias)
        recyclerProductos = view.findViewById(R.id.recycler_listado_productos)
        recyclerPedido = view.findViewById(R.id.recycler_pedido)
        listaProductos = view.findViewById(R.id.lista_productos)
        coordinatorLayout = view.findViewById(R.id.coordinator_layout_agregar_productos)

        disminuirComensal = view.findViewById(R.id.disminuirComensal)
        aumentarComensal = view.findViewById(R.id.aumentarComensal)
        numeroComensales = view.findViewById(R.id.numeroComensales)
        agregarProducto = view.findViewById(R.id.boton_agregar_producto)
        agregarComentario = view.findViewById(R.id.boton_comentario_producto)
    }

    private fun setOnClickListeners(){
        disminuirComensal.setOnClickListener {
            if (comensales > 1){
                comensales --
                numeroComensales.text = comensales.toString()
            }
        }
        aumentarComensal.setOnClickListener {
            comensales ++
            numeroComensales.text = comensales.toString()
        }
        agregarProducto.setOnClickListener {
            val adapter = recyclerProductos.adapter as ProductosAdapter
            val seleccionProducto = adapter?.seleccionActual
            if (seleccionProducto != -1){

                val indexProductoPedido = productosPedido.indexOfFirst { it.nombre == productos[seleccionProducto].nombre}
                if (indexProductoPedido == -1){
                    productosPedido.add(PedidoAdapter.ProductoPedido(1, productos[seleccionProducto].nombre, productos[seleccionProducto].precio))
                    recyclerPedido.adapter?.notifyItemInserted(productosPedido.size)
                } else {
                    val nuevaCantidad = productosPedido.get(indexProductoPedido).cantidad + 1
                    val nuevoImporte = productos[seleccionProducto].precio * nuevaCantidad
                    productosPedido.set(indexProductoPedido,PedidoAdapter.ProductoPedido(nuevaCantidad, productos[seleccionProducto].nombre, nuevoImporte))
                    recyclerPedido.adapter?.notifyItemChanged(indexProductoPedido)
                }
            }
        }
        agregarComentario.setOnClickListener {

        }
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

    private fun loadRecyclerPedidoComensal(){
        recyclerPedido.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        productosPedido = arrayListOf()
        val adapterPedido = PedidoAdapter(productosPedido)
        recyclerPedido.adapter = adapterPedido
    }


    private fun loadRecyclerCategorias(){
        recyclerCategorias.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val dataset = arrayOf("Favoritos","Hamburguesa", "Tortas", "Pizzas", "Burritos", "Desayunos")
        val adapterCategorias = CategoriasAdapter(dataset)
        (recyclerCategorias.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerCategorias.adapter = adapterCategorias

    }

    private fun loadRecyclerProductos(){
        recyclerProductos.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        productos = arrayOf(
            ProductosAdapter.Producto("Papa", 50F),
            ProductosAdapter.Producto("Hamburguesa",60.5F),
            ProductosAdapter.Producto("Torta de Pierna",40F),
            ProductosAdapter.Producto("Enchiladas",45F),
            ProductosAdapter.Producto("Torta Cubana",33F),
            ProductosAdapter.Producto("Limonada Grande",60F),
            ProductosAdapter.Producto("Pizza Mediana",80F))
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