package com.example.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diseno_prueba.R

class PedidoAdapter (private val dataset: ArrayList<ProductoPedido>) : RecyclerView.Adapter<PedidoAdapter.ViewHolder>(){

    class ProductoPedido(cantidad: Int, nombre: String, precio: Float){
        var cantidad : Int = cantidad
            get() {
                return field
            }
            set(value) {}
        var nombre : String = nombre
            get() {
                return field
            }
            set(value) {}
        var precio : Float = precio
            get() {
                return field
            }
            set(value) {}
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val cantidad : TextView
        val nombre : TextView
        val precio : TextView
        init {
            cantidad = view.findViewById(R.id.cantidad_producto_pedido)
            nombre = view.findViewById(R.id.nombre_producto_pedido)
            precio = view.findViewById(R.id.precio_producto_pedido)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto_pedido, parent, false)
        val holder = ViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("Cantidad", dataset.get(position).cantidad.toString())
        Log.i("Nombre", dataset.get(position).nombre)
        holder.cantidad.text = dataset.get(position).cantidad.toString()
        holder.nombre.text = dataset.get(position).nombre
        holder.precio.text = "$"+dataset.get(position).precio.toString()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}