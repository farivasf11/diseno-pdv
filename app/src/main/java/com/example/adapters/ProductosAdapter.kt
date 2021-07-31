package com.example.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.diseno_prueba.R
import com.example.models.Producto

class ProductosAdapter (private val productos: List<Producto>, private val tipoLista: Int) : RecyclerView.Adapter<ProductosAdapter.ViewHolder>(){
    var seleccionAnterior = -1
    var seleccionActual = -1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val nombre : TextView
        val precio : TextView
        val layout : LinearLayout
        init {
            nombre = view.findViewById(R.id.nombre_producto_item)
            precio = view.findViewById(R.id.precio_producto_item)
            layout = view.findViewById(R.id.layout_producto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemLista: Int = when(tipoLista){
            1 ->  R.layout.item_listado_productos_agregar
            2 ->  R.layout.item_listado_productos_busqueda
            else -> R.layout.item_listado_productos_agregar
        }

        val view = LayoutInflater.from(parent.context).inflate(itemLista, parent, false)
        val holder = ViewHolder(view)
        holder.itemView.setOnClickListener {
            if (tipoLista == 1){
                seleccionActual = holder.adapterPosition
                if (seleccionAnterior == -1){
                    seleccionAnterior = seleccionActual
                } else {
                    notifyItemChanged(seleccionAnterior)
                    seleccionAnterior = seleccionActual
                }
                notifyItemChanged(seleccionActual)
            }
            if (tipoLista ==2){
                holder.itemView.isFocusable = true
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            nombre.text = productos.get(position).nombre
            precio.text = "$"+productos.get(position).precio.toString()
            layout.isSelected = seleccionActual == position
        }
    }

    override fun getItemCount(): Int {
        return productos.size
    }


}