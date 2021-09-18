package com.alexis.models

class Producto(nombre: String, precio: Float, categoria: String){
    var nombre: String = nombre

    var precio : Float = precio
        get() =  if (this.incluirImpuestos) (field * 1.16).toFloat().round(2) else field.round(2)

    var categoria: String = categoria

    var esFavorito: Boolean = false

    var incluirImpuestos: Boolean = false

    fun Float.round(decimals: Int = 2): Float = "%.${decimals}f".format(this).toFloat()
}

