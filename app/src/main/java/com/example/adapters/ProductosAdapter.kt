package com.example.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diseno_prueba.R
import com.example.models.Producto

class ProductosAdapter (private val dataset: Array<Producto>) : RecyclerView.Adapter<ProductosAdapter.ViewHolder>(){
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_listado_productos_agregar,parent, false)
        val holder = ViewHolder(view)
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
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nombre.text = dataset[position].nombre
        holder.precio.text = "$"+dataset[position].precio.toString()
        holder.layout.isSelected = seleccionActual == position
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


}