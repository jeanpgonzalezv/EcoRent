package com.duoc.ecorentfinal.data.api.model

import com.duoc.ecorentfinal.data.model.Herramienta
import kotlin.random.Random

fun ApiHerramienta.toHerramienta(): Herramienta {
    // 1. CATEGORÍAS REALES
    val categorias = listOf(
        "Herramientas Eléctricas",
        "Herramientas Manuales",
        "Equipos de Jardinería",
        "Maquinaria Pesada",
        "Andamios y Elevación",
        "Equipos de Medición",
        "Herramientas de Corte",
        "Equipos de Soldadura"
    )
    val categoriaIndex = (this.categoriaId % categorias.size).toInt()

    // 2. NOMBRES REALES
    val nombresReales = listOf(
        "Taladro Percutor 800W Profesional",
        "Sierra Circular 180mm 2000W",
        "Martillo Demoledor 1500J",
        "Soldadora Inverter 200A MMA/TIG",
        "Compresor de Aire 100L 2.5HP",
        "Cortadora de Cerámica 800mm",
        "Pulidora Angular 180mm 1400W",
        "Hidrolavadora 2000PSI Gasolina"
    )
    val nombreIndex = (this.id % nombresReales.size).toInt()

    // 3. FABRICANTES
    val fabricantes = listOf("Bosch Professional", "DeWalt", "Makita", "Stanley")
    val fabricanteIndex = (this.id % fabricantes.size).toInt()

    // 4. DESCRIPCIONES
    val descripcionesBase = listOf(
        "Herramienta profesional ${fabricantes[fabricanteIndex]} para trabajos exigentes. ",
        "Equipo industrial de alto rendimiento marca ${fabricantes[fabricanteIndex]}. "
    )
    val descripcionIndex = (this.id % descripcionesBase.size).toInt()

    // 5. PRECIOS
    val precioBase = when (categoriaIndex) {
        0 -> 8000  // Eléctricas
        1 -> 4000  // Manuales
        2 -> 12000 // Jardinería
        3 -> 25000 // Maquinaria
        4 -> 18000 // Andamios
        5 -> 6000  // Medición
        6 -> 9000  // Corte
        else -> 15000 // Soldadura
    }

    // 6. STOCK (1-10)
    val stock = Random.nextInt(1, 11)

    // 7. RATING (3.5-5.0) - ¡CORREGIDO!
    val rating = 3.5f + Random.nextFloat() * 1.5f

    return Herramienta(
        id = this.id,
        nombre = "${nombresReales[nombreIndex]} - ${fabricantes[fabricanteIndex]}",
        descripcion = descripcionesBase[descripcionIndex] +
                "Características: " + this.descripcion.take(80).replace("\n", " ") + "...",
        categoria = categorias[categoriaIndex],
        precioPorDia = (precioBase + (this.id * 500)).toDouble(),
        stock = stock,
        imagenUri = null,
        rating = rating,  // ← ¡SIN String.format!
        disponible = stock > 0,
        fabricanteUrl = "https://www.${fabricantes[fabricanteIndex].lowercase().replace(" ", "").replace("&", "")}.com/producto/${this.id}"
    )
}