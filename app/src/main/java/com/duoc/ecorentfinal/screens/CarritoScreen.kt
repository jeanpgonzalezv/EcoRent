package com.duoc.ecorentfinal.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// ¡NO importar viewModel aquí!
import com.duoc.ecorentfinal.viewmodel.CarritoViewModel
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    carritoViewModel: CarritoViewModel, // ← RECIBIR como parámetro
    onNavigateBack: () -> Unit,
    onProcederPago: () -> Unit
) {
    println("🎪🎪🎪 CARRITO SCREEN: Iniciando")
    println("🎪🎪🎪 CARRITO SCREEN: ViewModel recibido - hashCode: ${carritoViewModel.hashCode()}")

    val items = carritoViewModel.itemsCarrito.collectAsState().value
    val total = carritoViewModel.totalCarrito.collectAsState().value

    // DEBUG cada vez que cambian los items
    LaunchedEffect(items) {
        println("📊📊📊 CARRITO SCREEN - Estado actualizado:")
        println("📊 Items count: ${items.size}")
        println("📊 Items: $items")
        println("📊 Total: $total")
        println("📊 ViewModel hashCode: ${carritoViewModel.hashCode()}")
    }

    val formatter = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Mi Carrito (${items.size})")
                    }
                },
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
        ) {
            if (items.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = "Carrito vacío",
                        modifier = Modifier.size(100.dp),
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        "Tu carrito está vacío",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Agrega herramientas para arrendar",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            println("🔙 Volviendo a herramientas")
                            onNavigateBack()
                        },
                        modifier = Modifier.padding(horizontal = 32.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Ver Herramientas", fontSize = 16.sp)
                    }

                    // Botón DEBUG para probar
                    Spacer(modifier = Modifier.height(16.dp))
                    TextButton(
                        onClick = {
                            println("🐛 DEBUG: Forzar recarga")
                            println("🐛 Items actuales: ${carritoViewModel.itemsCarrito.value.size}")
                        }
                    ) {
                        Text("DEBUG: Ver estado", fontSize = 12.sp)
                    }
                }
            } else {
                // LISTA DE ITEMS (cuando hay algo en el carrito)
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items) { item ->
                        Card(
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            item.nombre,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 18.sp
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            "${item.dias} día" + if (item.dias > 1) "s" else "",
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }

                                    IconButton(
                                        onClick = {
                                            println("🗑️ Eliminando: ${item.nombre}")
                                            carritoViewModel.eliminarDelCarrito(item.productoId)
                                        },
                                        modifier = Modifier.size(48.dp)
                                    ) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Eliminar",
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Precio por día:", fontSize = 14.sp)
                                    Text(
                                        formatter.format(item.precioPorDia),
                                        fontWeight = FontWeight.Medium
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Subtotal:", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                    Text(
                                        formatter.format(item.costoTotal),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }

                // TOTAL Y BOTÓN DE PAGO
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text(
                                formatter.format(total),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = {
                                println("💰 Procediendo al pago")
                                onProcederPago()
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            shape = RoundedCornerShape(12.dp),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 4.dp,
                                pressedElevation = 8.dp
                            )
                        ) {
                            Text(
                                "✅ Proceder al Pago - ${formatter.format(total)}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        TextButton(
                            onClick = onNavigateBack,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("➕ Agregar más herramientas")
                        }
                    }
                }
            }
        }
    }
}