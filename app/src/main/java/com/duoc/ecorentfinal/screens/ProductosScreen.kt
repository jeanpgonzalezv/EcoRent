package com.duoc.ecorentfinal.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.duoc.ecorentfinal.data.model.Herramienta
import com.duoc.ecorentfinal.viewmodel.ProductosViewModel
import androidx.compose.foundation.background

@Composable
fun ProductosScreen(
    onNavigateBack: () -> Unit,
    onArrendarHerramienta: (Long) -> Unit
) {
    // Obtener ViewModel
    val productosViewModel: ProductosViewModel = viewModel()

    // Observar el estado
    val productosState by productosViewModel.productosState.collectAsState()

    // Cargar datos al iniciar (solo una vez)
    LaunchedEffect(Unit) {
        productosViewModel.cargarHerramientas()
    }

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (productosState) {
                is ProductosViewModel.ProductosState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("📡 Cargando herramientas desde API...")
                        }
                    }
                }

                is ProductosViewModel.ProductosState.Success -> {
                    val herramientas = (productosState as ProductosViewModel.ProductosState.Success).herramientas

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        // Título
                        Text(
                            text = "🛠️ Herramientas desde API",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "${herramientas.size} productos cargados desde API",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Lista de herramientas
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(herramientas) { herramienta ->
                                HerramientaCard(
                                    herramienta = herramienta,
                                    onArrendarHerramienta = onArrendarHerramienta
                                )
                            }
                        }
                    }
                }

                is ProductosViewModel.ProductosState.Error -> {
                    val error = (productosState as ProductosViewModel.ProductosState.Error).mensaje

                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("❌ Error al cargar datos",
                                color = Color.Red,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold)

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(error,
                                color = Color.Red.copy(alpha = 0.8f),
                                textAlign = TextAlign.Center)

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = { productosViewModel.cargarHerramientas() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text("🔄 Reintentar")
                            }
                        }
                    }
                }

                is ProductosViewModel.ProductosState.Empty -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("📭 No hay herramientas disponibles",
                                fontSize = 18.sp)

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = { productosViewModel.cargarHerramientas() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                )
                            ) {
                                Text("Cargar desde API")
                            }
                        }
                    }
                }
            }
        }
    }
}

// FUNCIÓN HerramientaCard
@Composable
fun HerramientaCard(
    herramienta: Herramienta,
    onArrendarHerramienta: (Long) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Fila: Categoría + Rating
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Categoría con badge
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "📦 ${herramienta.categoria}",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Rating
                Text(
                    text = "⭐ ${herramienta.rating}",
                    fontSize = 12.sp,
                    color = Color(0xFFFF9800),
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Nombre
            Text(
                text = herramienta.nombre,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Descripcion
            Text(
                text = herramienta.descripcion,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.secondary,
                lineHeight = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Fila: Precio + Stock
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Precio
                Column {
                    Text(
                        text = "💰 Precio por día:",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = herramienta.precioFormateado(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                // Stock
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "📦 Disponibilidad:",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = if (herramienta.stock > 3) "🟢 En stock"
                        else if (herramienta.stock > 0) "🟡 Últimas unidades"
                        else "🔴 Agotado",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = when {
                            herramienta.stock > 3 -> Color(0xFF4CAF50)
                            herramienta.stock > 0 -> Color(0xFFFF9800)
                            else -> Color(0xFFF44336)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botón Arrendar
            Button(
                onClick = { onArrendarHerramienta(herramienta.id) },
                modifier = Modifier.fillMaxWidth(),
                enabled = herramienta.disponible && herramienta.stock > 0,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (herramienta.disponible && herramienta.stock > 0) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                    }
                )
            ) {
                Text(
                    text = if (herramienta.disponible && herramienta.stock > 0) {
                        "🛒 Arrendar ahora"
                    } else {
                        "🚫 No disponible"
                    }
                )
            }

            // Enlace al fabricante
            if (herramienta.fabricanteUrl.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))

                val context = LocalContext.current

                TextButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(herramienta.fabricanteUrl))
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "🌐 Ver especificaciones técnicas",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}