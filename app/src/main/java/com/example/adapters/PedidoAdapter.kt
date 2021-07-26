package com.example.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diseno_prueba.R
import com.example.models.ElementoPedido

class PedidoAdapter (private val dataset: ArrayList<ElementoPedido>) : RecyclerView.Adapter<PedidoAdapter.ViewHolder>(){

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
        holder.cantidad.text = dataset.get(position).cantidad.toString()
        holder.nombre.text = dataset.get(position).producto.nombre
        holder.precio.text = "$"+dataset.get(position).importe.toString()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}