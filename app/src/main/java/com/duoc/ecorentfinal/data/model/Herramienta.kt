package com.duoc.ecorentfinal.data.model

data class Herramienta(
    val id: Long = 0,
    val nombre: String,
    val descripcion: String,
    val categoria: String,
    val precioPorDia: Double,  // Para cálculos
    val stock: Int,
    val imagenUri: String? = null,
    val rating: Float = 0.0f,
    val disponible: Boolean = true,
    val fabricanteUrl: String = ""
) {
    // Función para mostrar precio formateado
    fun precioFormateado(): String {
        val formateado = String.format("$%,.0f", precioPorDia)
        return "$formateado / día"
    }

    // Función para mostrar stock
    fun stockFormateado(): String {
        return if (disponible && stock > 0) {
            "📦 Disponible: $stock unidades"
        } else if (stock == 0) {
            "❌ Agotado"
        } else {
            "⚠️ No disponible"
        }
    }
    // FUNCIÓN: Para mostrar fabricante
    fun tieneFabricante(): Boolean = fabricanteUrl.isNotEmpty()

}