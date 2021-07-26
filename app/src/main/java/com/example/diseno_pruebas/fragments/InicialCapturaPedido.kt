package com.example.diseno_pruebas.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.adapters.CategoriasAdapter
import com.example.adapters.PedidoAdapter
import com.example.adapters.ProductosAdapter
import com.example.diseno_prueba.R
import com.example.diseno_prueba.activities.CapturaPedido
import com.example.models.PedidoComensal
import com.example.models.Producto
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.button.MaterialButton

class InicialCapturaPedido : Fragment(), CapturaPedido.IFragmentsOnBackPressed, View.OnTouchListener {
    lateinit var buscador : EditText
    lateinit var listaProductos: LinearLayout
    lateinit var layoutSheet: LinearLayout
    lateinit var bottomSheet : (BottomSheetBehavior<View>)
    lateinit var coordinatorLayout : CoordinatorLayout
    lateinit var recyclerCategorias : RecyclerView
    lateinit var recyclerProductos : RecyclerView
    lateinit var recyclerPedido : RecyclerView
    lateinit var draggableZone : LinearLayout
    lateinit var layoutPedido : LinearLayout

    lateinit var disminuirComensal : MaterialButton
    lateinit var aumentarComensal : MaterialButton
    lateinit var numeroComensales : TextView
    lateinit var agregarProducto : MaterialButton
    lateinit var agregarComentario : MaterialButton
    lateinit var switchIncluirImpuestos : SwitchCompat

    lateinit var productos : Array<Producto>
    var comensal = PedidoComensal("")
    var comensales = 1
    var incluirImpuestos = false

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
        draggableZone = view.findViewById(R.id.draggable_zone)
        layoutPedido = view.findViewById(R.id.layout_pedido_comensal)

        disminuirComensal = view.findViewById(R.id.disminuirComensal)
        aumentarComensal = view.findViewById(R.id.aumentarComensal)
        numeroComensales = view.findViewById(R.id.numeroComensales)
        agregarProducto = view.findViewById(R.id.boton_agregar_producto)
        agregarComentario = view.findViewById(R.id.boton_comentario_producto)
        switchIncluirImpuestos = view.findViewById(R.id.switch_incluir_impuestos)
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
            vibratePhone()
            val adapter = recyclerProductos.adapter as ProductosAdapter
            val seleccionProducto = adapter?.seleccionActual
            if (seleccionProducto != -1){
                val productoAgregar = productos[seleccionProducto]
                val indexProductoPedido = comensal.productos.indexOfFirst { it.producto.nombre == productoAgregar.nombre}
                when(comensal.agregarProducto(indexProductoPedido, productoAgregar)){
                    1 -> recyclerPedido.adapter?.notifyItemInserted(comensal.productos.size)
                    2 -> recyclerPedido.adapter?.notifyItemChanged(indexProductoPedido)
                }
            }
        }
        agregarComentario.setOnClickListener {
            vibratePhone()
        }

        switchIncluirImpuestos.setOnCheckedChangeListener { buttonView, isChecked ->
            vibratePhone()
            incluirImpuestos = isChecked
            productos.forEach {
                it.incluirImpuestos = incluirImpuestos
            }
            recyclerProductos.adapter?.notifyDataSetChanged()
            recyclerPedido.adapter?.notifyDataSetChanged()
        }

        draggableZone.setOnTouchListener(this)
        layoutPedido.setOnTouchListener(this)
        recyclerPedido.setOnTouchListener(this)

    }

    fun Fragment.vibratePhone() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(100)
        }
    }

    private fun loadBuscador() {
        buscador.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                activity?.supportFragmentManager?.beginTransaction()?.add(R.id.frame_contenedor_captura_pedido, BuscadorProductos())?.commit()
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
        val adapterPedido = PedidoAdapter(comensal.productos)
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
            Producto("Papa", 50F),
            Producto("Hamburguesa",60.5F),
            Producto("Torta de Pierna",40F),
            Producto("Enchiladas",45F),
            Producto("Torta Cubana",33F),
            Producto("Limonada Grande",60F),
            Producto("Pizza Mediana",80F))
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

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (v == draggableZone){
            bottomSheet?.state = BottomSheetBehavior.STATE_EXPANDED
            return true
        }
        if (v == layoutPedido || v == recyclerPedido){
            bottomSheet?.state = BottomSheetBehavior.STATE_COLLAPSED
            return true
        }

        return false
    }

}