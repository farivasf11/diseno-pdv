package com.example.diseno_prueba.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.diseno_prueba.R
import com.example.diseno_pruebas.fragments.InicialCapturaPedido
import com.example.models.Producto
import com.google.android.material.button.MaterialButton

class CapturaPedido : AppCompatActivity(){
    lateinit var toolbar: Toolbar
    lateinit var botonRealizarPedido: MaterialButton
    lateinit var productosTodos: List<Producto>

    val LISTA_PRECIOS = 1
    val LISTA_BUSQUEDA = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_captura_pedido)
        toolbar = findViewById(R.id.toolbar_captura_pedido)
        toolbar.title = "Tomar pedido"

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        botonRealizarPedido = findViewById(R.id.boton_realizar_pedido)
        onGetProductSuccess()

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = InicialCapturaPedido()

        fragmentTransaction.add(R.id.frame_contenedor_captura_pedido, fragment).setReorderingAllowed(false).commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        val fragment = this.supportFragmentManager.findFragmentById(R.id.frame_contenedor_captura_pedido)
        (fragment as? IFragmentsOnBackPressed)?.onBackPressed()?.not()?.let { popBackStack: Boolean ->
            if (!popBackStack){
                super.onBackPressed()
            }
        }
    }

    private fun onGetProductSuccess(){
        productosTodos = listOf(
            Producto("Papa", 50F, "Favoritos"),
            Producto("Hamburguesa",60.5F, "Favoritos"),
            Producto("Torta de Pierna",40F, "Favoritos"),
            Producto("Enchiladas",45F, "Tortas"),
            Producto("Torta Cubana",33F, "Tortas"),
            Producto("Limonada Grande",60F, "Pizzas"),
            Producto("Burrito de adobada",80F, "Burritos"),
            Producto("Burrito de asada",43F, "Burritos"),
            Producto("Burito de desebrada",82F, "Burritos"),
            Producto("Tacos de asada",82F, "Burritos"),
            Producto("Coca Cola 500ml",10F, "Bebidas"),
            Producto("Limonada mineral chica",10F, "Bebidas"),
            Producto("Agua natural 1L",10F, "Bebidas"),
            Producto("Sprite 400ml",10F, "Bebidas"),
            Producto("Pepsi Lata Mediana",50F,"Bebidas"),
            Producto("Jarra de Horchata",50F,"Hamburguesas"),
            Producto("Hamburguesa de pollo",57F,"Hamburguesas"),
            Producto("Hamburguesa doble carne",70F,"Hamburguesas"),
            Producto("Hamburguesa Con Papas",55F,"Hamburguesas"),
            Producto("Hamburguesa especial",36F,"Hamburguesas"))
    }

    class BuscadorProductosViewModel: ViewModel(){
        val palabraCapturada = MutableLiveData<String>()
        fun capturarPalabra(item: String){
            palabraCapturada.value = item
        }
    }

    class CategoriasViewModel: ViewModel(){
        val categoriaSeleccionada = MutableLiveData<String>()
        fun actualizarSeleccionCategoria(item: String){
            categoriaSeleccionada.value = item
        }
    }
    interface IFragmentsOnBackPressed{
        fun onBackPressed(): Boolean
    }
}

