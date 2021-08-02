package com.example.diseno_pruebas.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
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
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.button.MaterialButton

class InicialCapturaPedido : Fragment(), CapturaPedido.IFragmentsOnBackPressed, View.OnClickListener, View.OnTouchListener {

    val viewModelBuscador: CapturaPedido.BuscadorProductosViewModel by activityViewModels()
    val viewModelCategorias: CapturaPedido.CategoriasViewModel by activityViewModels()

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
    lateinit var cardPedido : CardView

    lateinit var disminuirComensal : MaterialButton
    lateinit var aumentarComensal : MaterialButton
    lateinit var numeroComensales : TextView
    lateinit var agregarProducto : MaterialButton
    lateinit var agregarComentario : MaterialButton
    lateinit var switchIncluirImpuestos : SwitchCompat

    lateinit var productosPedido: TextView
    lateinit var importePedido: TextView

    lateinit var actividad: CapturaPedido
    lateinit var productosListar: List<Producto>
    lateinit var productosTodos: List<Producto>
    lateinit var categorias: Array<String>
    var comensal = PedidoComensal("")
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
        setObserverModeloBuscador()
        setObserverModeloCategorias()
    }

    private fun setObserverModeloCategorias() {
        viewModelCategorias.actualizarSeleccionCategoria(categorias[0])
        viewModelCategorias.categoriaSeleccionada.observe(viewLifecycleOwner, {
            val nombreCategoria = it
            productosListar = productosTodos.filter { it.categoria == nombreCategoria}
            val adapterProductos = ProductosAdapter(productosListar, actividad.LISTA_PRECIOS, recyclerProductos)
            recyclerProductos.adapter = adapterProductos
        })
    }

    private fun setObserverModeloBuscador() {
        viewModelBuscador.palabraCapturada.observe(viewLifecycleOwner, Observer<String>{
            buscador.setText(it)
            val params : ViewGroup.LayoutParams = buscador.layoutParams
            if (!it.isEmpty()){
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                recyclerCategorias.visibility = View.GONE
                if (!buscador.text.toString().isEmpty()){
                    productosListar = productosTodos.filter { it.nombre.contains(buscador.text.toString(), true) }
                    val adapterProductos = ProductosAdapter(productosListar, actividad.LISTA_PRECIOS, recyclerProductos)
                    recyclerProductos.adapter = adapterProductos
                }
                buscador.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search , 0, R.drawable.clear_icon, 0)
            } else {
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                recyclerCategorias.visibility = View.VISIBLE
                viewModelCategorias.actualizarSeleccionCategoria(categorias[0])
                buscador.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, 0, 0)
            }
            buscador.layoutParams = params
        })
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
        cardPedido = view.findViewById(R.id.card_pedido)

        disminuirComensal = view.findViewById(R.id.disminuirComensal)
        aumentarComensal = view.findViewById(R.id.aumentarComensal)
        numeroComensales = view.findViewById(R.id.numeroComensales)
        agregarProducto = view.findViewById(R.id.boton_agregar_producto)
        agregarComentario = view.findViewById(R.id.boton_comentario_producto)
        switchIncluirImpuestos = view.findViewById(R.id.switch_incluir_impuestos)
        productosPedido = view.findViewById(R.id.productos_pedido)
        importePedido = view.findViewById(R.id.importe_pedido)
        actividad = activity as CapturaPedido
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
                val productoAgregar = productosListar[seleccionProducto]
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
            productosTodos.forEach {
                it.incluirImpuestos = isChecked
            }
            recyclerProductos.adapter?.notifyDataSetChanged()
            recyclerPedido.adapter?.notifyDataSetChanged()
        }

        draggableZone.setOnClickListener(this)

        recyclerPedido.setOnTouchListener(this)
        cardPedido.setOnTouchListener(this)
    }

    fun Fragment.vibratePhone() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(100)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun loadBuscador() {
        buscador.setCompoundDrawablesWithIntrinsicBounds(R.drawable.search, 0, 0, 0)
        buscador.setOnFocusChangeListener(View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                activity?.supportFragmentManager?.beginTransaction()?.add(R.id.frame_contenedor_captura_pedido, BuscadorProductos.newInstance(buscador.text.toString()))?.setReorderingAllowed(false)?.addToBackStack("BUSCADOR_PRODUCTOS")?.commit()
                buscador.clearFocus()
            }
        })
        buscador.setOnTouchListener { v, event ->
            val DRAWABLE_RIGHT = 2
            if (event!!.action == MotionEvent.ACTION_DOWN) {
                if (buscador.getCompoundDrawables()[DRAWABLE_RIGHT] != null){
                    if (event.getRawX() >= (buscador.getRight() - buscador.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        viewModelBuscador.capturarPalabra("")
                        buscador.clearFocus()
                    }
                }
            }
            false
        }
    }

    private fun loadBottomSheet(){
        bottomSheet= BottomSheetBehavior.from(layoutSheet)
        bottomSheet.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            isGestureInsetBottomIgnored = false
            isDraggable = false
            addBottomSheetCallback(object: BottomSheetCallback(){
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_COLLAPSED){

                    }
                    if (newState == BottomSheetBehavior.STATE_EXPANDED){

                    }
                }
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }

            })
        }
    }

    private fun loadRecyclerPedidoComensal(){
        recyclerPedido.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val adapterPedido = PedidoAdapter(comensal.productos, false)
        recyclerPedido.adapter = adapterPedido
    }


    private fun loadRecyclerCategorias(){
        recyclerCategorias.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        categorias = arrayOf("Favoritos","Hamburguesas", "Tortas", "Pizzas", "Bebidas", "Burritos", "Desayunos")
        val adapterCategorias = CategoriasAdapter(categorias, viewModelCategorias, recyclerCategorias)
        (recyclerCategorias.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerCategorias.adapter = adapterCategorias
    }

    private fun loadRecyclerProductos(){
        productosTodos = actividad.productosTodos
        recyclerProductos.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        (recyclerProductos.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
    }

    companion object {
        fun newInstance() =
            InicialCapturaPedido().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onClick(v: View?) {
        if (v == draggableZone){
            val adapterPedido = PedidoAdapter(comensal.productos, false)
            recyclerPedido.adapter = adapterPedido

            val params : ViewGroup.LayoutParams = layoutPedido.layoutParams
            params.height = ((200  * (getResources().getDisplayMetrics().density)).toInt());
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutPedido.layoutParams = params

            bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (v == recyclerPedido && comensal.productos.size > 0) {
            val adapterPedido = PedidoAdapter(comensal.productos, true)
            recyclerPedido.adapter = adapterPedido
            layoutPedido.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
            return true
        }
        return false
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}