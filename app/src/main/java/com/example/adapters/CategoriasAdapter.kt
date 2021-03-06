package com.example.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.diseno_prueba.R
import com.example.diseno_prueba.activities.CapturaPedido
import com.google.android.material.button.MaterialButton

class CategoriasAdapter(private val dataset: Array<String>, private val viewModelCategorias: CapturaPedido.CategoriasViewModel) : RecyclerView.Adapter<CategoriasAdapter.ViewHolder>(){
    var seleccionAnterior = -1
    var seleccionActual = -1

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val button : MaterialButton
        init {
            button = view.findViewById(R.id.view_boton_categorias)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_boton_categorias, parent, false)
        val holder = ViewHolder(view)

        holder.itemView.setOnClickListener {
            viewModelCategorias.actualizarSeleccionCategoria(dataset[holder.adapterPosition])
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
        holder.apply {
            button.text = dataset[position]
            button.isSelected = seleccionActual == position
            if (seleccionActual == -1 && seleccionAnterior == -1 && position == 0){
                button.isSelected = true
                seleccionAnterior = 0
            }
        }
    }

    override fun getItemCount(): Int {
        return dataset.size

    }
}