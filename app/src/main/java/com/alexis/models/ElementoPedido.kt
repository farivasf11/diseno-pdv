package com.alexis.models

class ElementoPedido(producto: Producto) {
    var cantidad : Int = 1

    val producto : Producto = producto

    val importe get() =  (this.cantidad * producto.precio).round()

    var expandido :  Boolean = false

    fun Float.round(decimals: Int = 2): Float = "%.${decimals}f".format(this).toFloat()
}