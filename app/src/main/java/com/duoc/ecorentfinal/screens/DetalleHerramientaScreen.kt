package com.duoc.ecorentfinal.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.ecorentfinal.data.entities.HerramientasDataSource
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleHerramientaScreen(
    herramientaId: Long,
    onAgregarAlCarrito: (herramientaId: Long, dias: Int, costoTotal: Double) -> Unit,
    onNavigateBack: () -> Unit
) {
    println("🟢 DEBUG DetalleHerramientaScreen: Abriendo con ID = $herramientaId")

    // Buscar la herramienta real en el DataSource
    val herramientaEncontrada = HerramientasDataSource.herramientas.find { it.id == herramientaId }

    // Si no encontramos la herramienta, usamos datos por defecto
    val herramienta = herramientaEncontrada ?: HerramientasDataSource.herramientas.firstOrNull() ?: run {
        // Datos de emergencia si no hay nada
        com.duoc.ecorentfinal.data.model.Herramienta(
            id = herramientaId,
            nombre = "Herramienta Ejemplo",
            descripcion = "Descripción no disponible",
            categoria = "General",
            precioPorDia = 3000.0,
            stock = 3,
            rating = 4.0f,
            disponible = true,
            fabricanteUrl = ""
        )
    }

    var diasSeleccionados by remember { mutableStateOf(1) }
    val precioPorDia = herramienta.precioPorDia
    val costoTotal = precioPorDia * diasSeleccionados

    // Formateador de moneda
    val formatter = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle de Herramienta") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            // Icono grande de la herramienta
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text("🛠️", fontSize = 64.sp)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Nombre
            Text(
                text = herramienta.nombre,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Rating y stock
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (herramienta.rating > 0) {
                    Text("⭐ ${herramienta.rating}", color = MaterialTheme.colorScheme.secondary)
                    Spacer(modifier = Modifier.width(8.dp))
                }

                Text(
                    text = if (herramienta.disponible && herramienta.stock > 0) {
                        "✅ Disponible"
                    } else if (herramienta.stock == 0) {
                        "❌ Agotado"
                    } else {
                        "⚠️ No disponible"
                    },
                    color = if (herramienta.disponible && herramienta.stock > 0) {
                        Color(0xFF4CAF50)
                    } else {
                        Color(0xFFF44336)
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Descripción
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "📝 Descripción",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = herramienta.descripcion)

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Categoría: ${herramienta.categoria}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Selector de días
            Card {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "📅 Selecciona días de arriendo",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = {
                                if (diasSeleccionados > 1) diasSeleccionados--
                            },
                            modifier = Modifier.size(48.dp),
                            enabled = diasSeleccionados > 1
                        ) {
                            Text("-", fontSize = 20.sp)
                        }

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "$diasSeleccionados",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text("días", fontSize = 14.sp)
                        }

                        OutlinedButton(
                            onClick = { diasSeleccionados++ },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Text("+", fontSize = 20.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "Precio por día: ${formatter.format(precioPorDia)}",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Resumen de costo
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "💰 Resumen de arriendo",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Precio por día
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Precio por día:")
                        Text(
                            formatter.format(precioPorDia),
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Días seleccionados
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Días de arriendo:")
                        Text("$diasSeleccionados", fontWeight = FontWeight.Medium)
                    }

                    Divider(modifier = Modifier.padding(vertical = 12.dp))

                    // Total
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total a pagar:", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(
                            formatter.format(costoTotal),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón agregar al carrito
            Button(
                onClick = {
                    println("🟡 DEBUG: Agregando al carrito - ID: $herramientaId, Días: $diasSeleccionados, Total: $costoTotal")
                    onAgregarAlCarrito(herramientaId, diasSeleccionados, costoTotal)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(12.dp),
                enabled = herramienta.disponible && herramienta.stock > 0
            ) {
                Text(
                    text = if (herramienta.disponible && herramienta.stock > 0) {
                        "🛒 Agregar al Carrito - ${formatter.format(costoTotal)}"
                    } else {
                        "No disponible"
                    },
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}