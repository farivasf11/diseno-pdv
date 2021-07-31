package com.example.models

class Producto(nombre: String, precio: Float, categoria: String){
    var nombre: String = nombre

    var precio : Float = precio
        get() =  if (this.incluirImpuestos) (field*1.16).toFloat() else field


    var categoria: String = categoria

    var esFavorito: Boolean = false

    var incluirImpuestos: Boolean = false
}

