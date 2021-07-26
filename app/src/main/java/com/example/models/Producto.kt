package com.example.models

class Producto(nombre: String, precio: Float){
    var nombre: String = nombre

    var precio : Float = precio
        get() =  if (this.incluirImpuestos) (field*1.16).toFloat() else field


    var categoria: String = "Hamburguesas"

    var esFavorito: Boolean = false

    var incluirImpuestos: Boolean = false
}

