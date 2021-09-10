package com.example.diseno_pruebas.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.gesture.Gesture
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.cardview.widget.CardView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.adapters.CategoriasAdapter
import com.example.adapters.PedidoAdapter
import com.example.adapters.PedidosAdapter
import com.example.adapters.ProductosAdapter
import com.example.diseno_prueba.R
import com.example.diseno_prueba.activities.CapturaPedido
import com.example.models.PedidoComensal
import com.example.models.Producto
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.button.MaterialButton

class InicialCapturaPedido : Fragment(), CapturaPedido.IFragmentsOnBackPressed,
    View.OnClickListener, View.OnTouchListener {

    val viewModelBuscador: CapturaPedido.BuscadorProductosViewModel by activityViewModels()
    val viewModelCategorias: CapturaPedido.CategoriasViewModel by activityViewModels()

    lateinit var buscador: EditText
    lateinit var listaProductos: LinearLayout
    lateinit var layoutSheet: CardView
    lateinit var bottomSheet: (BottomSheetBehavior<View>)
    lateinit var coordinatorLayout: CoordinatorLayout
    lateinit var recyclerCategorias: RecyclerView
    lateinit var recyclerProductos: RecyclerView
    lateinit var draggableZone: LinearLayout
    lateinit var layoutPedido: LinearLayout
    lateinit var disminuirComensal: MaterialButton
    lateinit var aumentarComensal: MaterialButton
    lateinit var numeroComensales: TextView
    lateinit var agregarProducto: MaterialButton
    lateinit var agregarComentario: MaterialButton
    lateinit var atrasPedido: MaterialButton
    lateinit var avanzarPedido: MaterialButton
    lateinit var switchIncluirImpuestos: SwitchCompat
    lateinit var viewPagerPedidos: ViewPager2

    lateinit var productosPedido: TextView
    lateinit var importePedido: TextView
    lateinit var nombreComensal: TextView

    lateinit var actividad: CapturaPedido
    lateinit var productosListar: List<Producto>
    lateinit var productosTodos: List<Producto>
    lateinit var categorias: Array<String>

    val pedidosComensales: ArrayList<PedidoComensal> = arrayListOf()
    var comensal = PedidoComensal("Comensal 1")
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
        loadViewPagerPedidos()
        setOnClickListeners()
        setObserverModeloBuscador()
        setObserverModeloCategorias()
    }

    private fun loadViewPagerPedidos() {
        pedidosComensales.add(comensal)
        val adapterPedidos = PedidosAdapter(actividad, pedidosComensales, false)
        viewPagerPedidos.adapter = adapterPedidos
        viewPagerPedidos.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                nombreComensal.setText(pedidosComensales.get(position).nombreComensal)
//                if (bottomSheet.state == BottomSheetBehavior.STATE_COLLAPSED){
//                    viewPagerPedidos.adapter?.notifyItemChanged(position)
//                }
            }
        })
    }

    private fun setObserverModeloCategorias() {
        viewModelCategorias.actualizarSeleccionCategoria(categorias[0])
        viewModelCategorias.categoriaSeleccionada.observe(viewLifecycleOwner, {
            val nombreCategoria = it
            productosListar = productosTodos.filter { it.categoria == nombreCategoria }
            val adapterProductos =
                ProductosAdapter(productosListar, actividad.LISTA_PRECIOS, recyclerProductos)
            recyclerProductos.adapter = adapterProductos
        })
    }

    private fun setObserverModeloBuscador() {
        viewModelBuscador.palabraCapturada.observe(viewLifecycleOwner, {
            buscador.setText(it)
            val params: ViewGroup.LayoutParams = buscador.layoutParams
            if (!it.isEmpty()) {
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                recyclerCategorias.visibility = View.GONE
                if (!buscador.text.toString().isEmpty()) {
                    productosListar =
                        productosTodos.filter { it.nombre.contains(buscador.text.toString(), true) }
                    val adapterProductos = ProductosAdapter(
                        productosListar,
                        actividad.LISTA_PRECIOS,
                        recyclerProductos
                    )
                    recyclerProductos.adapter = adapterProductos
                }
                buscador.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.search,
                    0,
                    R.drawable.clear_icon,
                    0
                )
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

    private fun findViews(view: View) {
        buscador = view.findViewById(R.id.buscador_productos)
        layoutSheet = view.findViewById(R.id.sheet)
        recyclerCategorias = view.findViewById(R.id.recycler_categorias)
        recyclerProductos = view.findViewById(R.id.recycler_listado_productos)
        listaProductos = view.findViewById(R.id.lista_productos)
        coordinatorLayout = view.findViewById(R.id.coordinator_layout_agregar_productos)
        draggableZone = view.findViewById(R.id.draggable_zone)
        layoutPedido = view.findViewById(R.id.layout_pedido_comensal)
        viewPagerPedidos = view.findViewById(R.id.view_pager_pedidos)
        nombreComensal = view.findViewById(R.id.nombre_comensal)
        disminuirComensal = view.findViewById(R.id.disminuirComensal)
        aumentarComensal = view.findViewById(R.id.aumentarComensal)
        numeroComensales = view.findViewById(R.id.numeroComensales)
        agregarProducto = view.findViewById(R.id.boton_agregar_producto)
        agregarComentario = view.findViewById(R.id.boton_comentario_producto)
        atrasPedido = view.findViewById(R.id.atras_pedido)
        avanzarPedido = view.findViewById(R.id.avanzar_pedido)
        switchIncluirImpuestos = view.findViewById(R.id.switch_incluir_impuestos)
        productosPedido = view.findViewById(R.id.productos_pedido)
        importePedido = view.findViewById(R.id.importe_pedido)
        actividad = activity as CapturaPedido
    }

    private fun setOnClickListeners() {
        disminuirComensal.setOnClickListener {
            val indexComensal = pedidosComensales.size - 1
            if (comensales > 1) {
                if (pedidosComensales.get(indexComensal).productos.isEmpty()){
                    comensales--
                    numeroComensales.text = comensales.toString()
                    pedidosComensales.removeAt(indexComensal)
                    viewPagerPedidos.adapter?.notifyItemRemoved(pedidosComensales.size)
                } else {
                    Toast.makeText(actividad, "Hay productos agregados para el comensal ${pedidosComensales.size}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        aumentarComensal.setOnClickListener {
            comensales++
            numeroComensales.text = comensales.toString()
            val nuevoComensal = PedidoComensal("Comensal ${comensales}")
            pedidosComensales.add(nuevoComensal)
            viewPagerPedidos.adapter?.notifyItemInserted(pedidosComensales.size)
        }

        agregarProducto.setOnClickListener {
            val adapter = recyclerProductos.adapter as ProductosAdapter
            val seleccionProducto = adapter?.seleccionActual
            if (seleccionProducto != -1) {
                val productoAgregar = productosListar[seleccionProducto]
                val itemSelectedPedidos = viewPagerPedidos.currentItem
                val indexProductoPedido = pedidosComensales.get(itemSelectedPedidos).productos.indexOfFirst { it.producto.nombre == productoAgregar.nombre }
                when (pedidosComensales.get(itemSelectedPedidos).agregarProducto(indexProductoPedido, productoAgregar)) {
                    1 -> viewPagerPedidos.adapter?.notifyItemChanged(itemSelectedPedidos)
                    else -> viewPagerPedidos.adapter?.notifyItemChanged(itemSelectedPedidos)
                }
                vibratePhone()
            }
        }
        agregarComentario.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.add(
                R.id.frame_contenedor_captura_pedido,
                MasOpcionesAgregar()
            )?.setReorderingAllowed(false)?.addToBackStack("MAS_OPCIONES")?.commit()
            vibratePhone()
        }

        switchIncluirImpuestos.setOnCheckedChangeListener { buttonView, isChecked ->
            vibratePhone()
            productosTodos.forEach {
                it.incluirImpuestos = isChecked
            }
            recyclerProductos.adapter?.notifyDataSetChanged()
            //recyclerPedido.adapter?.notifyDataSetChanged()
            //Falta actualizar esto en viewpager
        }
        atrasPedido.setOnClickListener{
            val nuevaPagina = viewPagerPedidos.currentItem - 1
            viewPagerPedidos.setCurrentItem(nuevaPagina, true)
        }
        avanzarPedido.setOnClickListener{
            val nuevaPagina = viewPagerPedidos.currentItem + 1
            viewPagerPedidos.setCurrentItem(nuevaPagina, true)
        }

        val mDetector = GestureDetector(actividad, object :
            GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent?): Boolean {
                Toast.makeText(actividad, "Doble Tap ${pedidosComensales.size}", Toast.LENGTH_SHORT).show()
                return true
            }
        })
        draggableZone.setOnClickListener(this)
        viewPagerPedidos.setOnTouchListener { v, event -> mDetector.onTouchEvent(event) }
        actividad.botonRealizarPedido.setOnTouchListener { v, event -> mDetector.onTouchEvent(event) }
        //recyclerPedido.setOnTouchListener(this)
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
            if (hasFocus) {
                activity?.supportFragmentManager?.beginTransaction()?.add(
                    R.id.frame_contenedor_captura_pedido,
                    BuscadorProductos.newInstance(buscador.text.toString())
                )?.setReorderingAllowed(false)?.addToBackStack("BUSCADOR_PRODUCTOS")?.commit()
                buscador.clearFocus()
            }
        })
        buscador.setOnTouchListener { v, event ->
            val DRAWABLE_RIGHT = 2
            if (event!!.action == MotionEvent.ACTION_DOWN) {
                if (buscador.getCompoundDrawables()[DRAWABLE_RIGHT] != null) {
                    if (event.getRawX() >= (buscador.getRight() - buscador.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds()
                            .width())
                    ) {
                        viewModelBuscador.capturarPalabra("")
                        buscador.clearFocus()
                    }
                }
            }
            false
        }
    }

    private fun loadBottomSheet() {
        bottomSheet = BottomSheetBehavior.from(layoutSheet)
        bottomSheet.apply {
            state = BottomSheetBehavior.STATE_EXPANDED
            isGestureInsetBottomIgnored = false
            isDraggable = true
            addBottomSheetCallback(object : BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    val currentItem = viewPagerPedidos.currentItem
                    if (newState == BottomSheetBehavior.STATE_COLLAPSED && pedidosComensales.get(currentItem).productos.size > 0) {
                        pedidoExpandido(currentItem)
                    }
                    if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                        pedidoColapsado(currentItem)
                    }
                }
                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }
            })
        }
    }

    private fun pedidoExpandido(currentItem: Int){
        layoutPedido.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        val adapterPedidos = PedidosAdapter(actividad, pedidosComensales, true)
        viewPagerPedidos.adapter = adapterPedidos
        viewPagerPedidos.setCurrentItem(currentItem, false)
    }

    private fun pedidoColapsado(currentItem: Int){
        val params: ViewGroup.LayoutParams = layoutPedido.layoutParams
        params.height = ((200 * (getResources().getDisplayMetrics().density)).toInt());
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutPedido.layoutParams = params
        val adapterPedidos = PedidosAdapter(actividad, pedidosComensales, false)
        viewPagerPedidos.adapter = adapterPedidos
        viewPagerPedidos.setCurrentItem(currentItem, false)
    }

    private fun loadRecyclerCategorias() {
        recyclerCategorias.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        categorias = arrayOf("Favoritos","Hamburguesas","Tortas","Pizzas","Bebidas","Burritos","Desayunos")
        val adapterCategorias = CategoriasAdapter(categorias, viewModelCategorias, recyclerCategorias)
        (recyclerCategorias.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        recyclerCategorias.adapter = adapterCategorias
    }

    private fun loadRecyclerProductos() {
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
        if (v == draggableZone ) {
//            val adapterPedido = PedidoAdapter(comensal.productos, false)
//            recyclerPedido.adapter = adapterPedido
            val params: ViewGroup.LayoutParams = layoutPedido.layoutParams
            params.height = ((200 * (getResources().getDisplayMetrics().density)).toInt());
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutPedido.layoutParams = params
            bottomSheet.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        return false
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}