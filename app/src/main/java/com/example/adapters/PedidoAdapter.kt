package com.example.adapters

import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diseno_prueba.R
import com.example.models.ElementoPedido

class PedidoAdapter (private val dataset: ArrayList<ElementoPedido>, private val expandido: Boolean) : RecyclerView.Adapter<PedidoAdapter.ViewHolder>(){
    var seleccionAnterior = -1
    var seleccionActual = -1

    val elementos : ArrayList<ElementoPedido> = dataset

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val cantidad : TextView
        val nombre : TextView
        val precio : TextView
        val separador : View
        val layoutOpciones: LinearLayout
        val layoutProducto: LinearLayout

        init {
            cantidad = view.findViewById(R.id.cantidad_producto_pedido)
            nombre = view.findViewById(R.id.nombre_producto_pedido)
            precio = view.findViewById(R.id.precio_producto_pedido)
            separador = view.findViewById(R.id.separador_producto)
            layoutOpciones = view.findViewById(R.id.opciones_adicionales)
            layoutProducto = view.findViewById(R.id.layout_producto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view : View
        if (viewType == 1){
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto_pedido, parent, false)
        } else {
            view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto_pedido_expanded, parent, false)
        }
        val holder = ViewHolder(view)

        if (viewType == 2){
            holder.itemView.setOnClickListener {
                seleccionActual = holder.adapterPosition
                if (seleccionAnterior == -1){
                    seleccionAnterior = seleccionActual
                } else {
                    notifyItemChanged(seleccionAnterior)
                    seleccionAnterior = seleccionActual
                }
                notifyItemChanged(seleccionActual)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cantidad.text = dataset.get(position).cantidad.toString()
        holder.nombre.text = dataset.get(position).producto.nombre
        holder.precio.text = "$"+dataset.get(position).importe.toString()
        holder.layoutProducto.isSelected = seleccionActual == position
        holder.layoutOpciones.visibility = if (seleccionActual == position) View.VISIBLE else View.GONE
        if (position + 1 == itemCount) holder.separador.visibility = View.GONE
    }

    override fun getItemViewType(position: Int): Int {
        if (expandido){
            return 2
        } else{
            return 1
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}