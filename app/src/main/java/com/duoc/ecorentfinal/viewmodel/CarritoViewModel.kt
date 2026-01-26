package com.duoc.ecorentfinal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ItemCarrito(
    val productoId: Long,
    val nombre: String,
    val precioPorDia: Double,
    val dias: Int,
    val costoTotal: Double,
    val imagenUri: String? = null
)

class CarritoViewModel : ViewModel() {
    // Usar companion object para asegurar una sola fuente de verdad
    companion object {
        private val _itemsCarrito = MutableStateFlow<List<ItemCarrito>>(emptyList())
    }

    private val _itemsCarrito = MutableStateFlow<List<ItemCarrito>>(emptyList())
    val itemsCarrito: StateFlow<List<ItemCarrito>> = _itemsCarrito.asStateFlow()

    private val _totalCarrito = MutableStateFlow(0.0)
    val totalCarrito: StateFlow<Double> = _totalCarrito

    init {
        println("🔥 VIEWMODEL INICIALIZADO: hashCode=${this.hashCode()}")
    }

    fun agregarAlCarrito(item: ItemCarrito) {
        println("🛒🛒🛒 VIEWMODEL.agregarAlCarrito() llamado con: ${item.nombre}")
        println("🛒🛒🛒 ViewModel hashCode: ${this.hashCode()}")

        viewModelScope.launch {
            val nuevaLista = _itemsCarrito.value.toMutableList()
            nuevaLista.add(item)
            _itemsCarrito.value = nuevaLista

            // Actualizar total
            val nuevoTotal = nuevaLista.sumOf { it.costoTotal }
            _totalCarrito.value = nuevoTotal

            // DEBUG IMPORTANTE
            println("✅✅✅ VIEWMODEL: Item AGREGADO exitosamente!")
            println("✅✅✅ Nombre: ${item.nombre}")
            println("✅✅✅ Total items ahora: ${_itemsCarrito.value.size}")
            println("✅✅✅ Lista actual: ${_itemsCarrito.value}")
            println("✅✅✅ Total carrito: $nuevoTotal")
        }
    }

    fun eliminarDelCarrito(productoId: Long) {
        viewModelScope.launch {
            val nuevaLista = _itemsCarrito.value.filter { it.productoId != productoId }
            _itemsCarrito.value = nuevaLista
            _totalCarrito.value = nuevaLista.sumOf { it.costoTotal }
        }
    }

    fun limpiarCarrito() {
        viewModelScope.launch {
            _itemsCarrito.value = emptyList()
            _totalCarrito.value = 0.0
            println("🧹 VIEWMODEL: Carrito limpiado")
        }
    }
}