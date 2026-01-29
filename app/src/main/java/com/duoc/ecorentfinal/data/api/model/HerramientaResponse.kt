package com.duoc.ecorentfinal.data.api.model

import com.duoc.ecorentfinal.data.model.Herramienta
import kotlin.random.Random

fun ApiHerramienta.toHerramienta(): Herramienta {
    // 1. CATEGORÍAS REALES basadas en userId
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

    // 2. NOMBRES REALES de herramientas basados en id
    val nombresReales = listOf(
        "Taladro Percutor 800W Profesional",
        "Sierra Circular 180mm 2000W",
        "Martillo Demoledor 1500J",
        "Soldadora Inverter 200A MMA/TIG",
        "Compresor de Aire 100L 2.5HP",
        "Cortadora de Cerámica 800mm",
        "Pulidora Angular 180mm 1400W",
        "Hidrolavadora 2000PSI Gasolina",
        "Generador Diésel 5500W Silencioso",
        "Mezcladora de Concreto 180L",
        "Martillo Rotativo SDS-MAX",
        "Lijadora Orbital 125mm",
        "Cepillo Eléctrico 82mm 600W",
        "Grapadora Neumática para Techo",
        "Cizalla para Metales 3mm",
        "Cortadora de Plasma 40A",
        "Rotomartillo SDS-PLUS 5kg",
        "Sierra Sable 1010W Variable",
        "Caladora de Madera 720W",
        "Desbaste Angular 230mm"
    )
    val nombreIndex = (this.id % nombresReales.size).toInt()

    // 3. FABRICANTES REALES
    val fabricantes = listOf(
        "Bosch Professional",
        "DeWalt",
        "Makita",
        "Stanley",
        "Black & Decker",
        "Hitachi Power Tools",
        "Milwaukee",
        "Ingersoll Rand",
        "Hilti",
        "Einhell"
    )
    val fabricanteIndex = (this.id % fabricantes.size).toInt()

    val descripcionesBase = listOf(
        "Herramienta profesional ${fabricantes[fabricanteIndex]} para trabajos exigentes. ",
        "Equipo industrial de alto rendimiento marca ${fabricantes[fabricanteIndex]}. ",
        "Herramienta especializada ${fabricantes[fabricanteIndex]} con certificación de seguridad. ",
        "Maquinaria profesional ${fabricantes[fabricanteIndex]} para construcción y mantenimiento. "
    )
    val descripcionIndex = (this.id % descripcionesBase.size).toInt()

    // 5. PRECIOS REALISTAS (en CLP)
    val precioBase = when (categoriaIndex) {
        0 -> 8000  // Eléctricas
        1 -> 4000  // Manuales
        2 -> 12000 // Jardinería
        3 -> 25000 // Maquinaria Pesada
        4 -> 18000 // Andamios
        5 -> 6000  // Medición
        6 -> 9000  // Corte
        else -> 15000 // Soldadura
    }

    // 6. STOCK ALEATORIO
    val stock = Random.nextInt(1, 11)

    // 7. RATING
    val rating = 3.5f + Random.nextFloat() * 1.5f  // Entre 3.5 y 5.0

    return Herramienta(
        id = this.id,
        nombre = "${nombresReales[nombreIndex]} - ${fabricantes[fabricanteIndex]}",
        descripcion = descripcionesBase[descripcionIndex] +
                "Características: " + this.descripcion.take(80).replace("\n", " ") + "...",
        categoria = categorias[categoriaIndex],
        precioPorDia = (precioBase + (this.id * 500)).toDouble(),
        stock = stock,
        imagenUri = null,
        rating = String.format("%.1f", rating).toFloat(),
        disponible = stock > 0,
        fabricanteUrl = "https://www.${fabricantes[fabricanteIndex].lowercase().replace(" ", "").replace("&", "")}.com/producto/${this.id}"
    )
}