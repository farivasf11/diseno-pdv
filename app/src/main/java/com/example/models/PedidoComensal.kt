package com.example.models

class PedidoComensal (nombre : String){
    var nombreComensal = nombre

    val productos : ArrayList<ElementoPedido> = arrayListOf()

    fun agregarProducto(indexProductoPedido : Int, producto: Producto) : Int{
        if (indexProductoPedido == -1){
            this.productos.add(ElementoPedido(producto))
            return 1
        } else {
            this.productos.get(indexProductoPedido).cantidad++
            return 2
        }
    }
}