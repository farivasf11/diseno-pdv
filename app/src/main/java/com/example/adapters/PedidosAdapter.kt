package com.example.adapters

import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diseno_prueba.R
import com.example.models.PedidoComensal

class PedidosAdapter(private val actividad: AppCompatActivity, private val comensales: List<PedidoComensal>, private val expandido: Boolean): RecyclerView.Adapter<PedidosAdapter.ViewPagerViewHolder>(){
    inner class ViewPagerViewHolder(view: View): RecyclerView.ViewHolder(view){
        val recyclerPedido: RecyclerView
        init {
            recyclerPedido = view.findViewById(R.id.recycler_pedido)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pedido_comensal, parent, false)
        val holder =  ViewPagerViewHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.recyclerPedido.layoutManager = LinearLayoutManager(actividad, LinearLayoutManager.VERTICAL, false)
        val adapterPedido = PedidoAdapter(comensales.get(position).productos, expandido)
        holder.recyclerPedido.adapter = adapterPedido
    }

    override fun getItemCount(): Int {
        return comensales.size
    }
}