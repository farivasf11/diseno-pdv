package com.example.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diseno_prueba.R
import com.google.android.material.button.MaterialButton

class CategoriasAdapter(private val dataset: Array<String>) : RecyclerView.Adapter<CategoriasAdapter.ViewHolder>(){
    var seleccionAnterior = -1
    var seleccionActual = -1
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{
        val button : MaterialButton
        init {
            button = view.findViewById(R.id.view_boton_categorias)
            button.setOnClickListener { this }
        }

        override fun onClick(v: View?) {
            if (adapterPosition == RecyclerView.NO_POSITION) return
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriasAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_boton_categorias, parent, false)
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

    override fun onBindViewHolder(holder: CategoriasAdapter.ViewHolder, position: Int) {
        holder.button.text = dataset[position]
        holder.button.isSelected = seleccionActual == position
    }

    override fun getItemCount(): Int {
        return dataset.size

    }
}