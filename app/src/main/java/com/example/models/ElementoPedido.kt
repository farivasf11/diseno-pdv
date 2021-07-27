package com.example.models

class ElementoPedido(producto: Producto) {
    var cantidad : Int = 1

    val producto : Producto = producto

    val importe get() =  this.cantidad * producto.precio

    var expandido :  Boolean = false
}