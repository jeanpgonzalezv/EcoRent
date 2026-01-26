package com.duoc.ecorentfinal.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.ecorentfinal.data.entities.HerramientasDataSource
import com.duoc.ecorentfinal.data.model.Herramienta

import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.net.Uri
import androidx.compose.material3.TextButton

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

@Composable
fun ProductosScreen(
    onNavigateBack: () -> Unit,
    onArrendarHerramienta: (Long) -> Unit
) {


    val herramientas = HerramientasDataSource.herramientas

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "🛠️ Herramientas Disponibles",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "${herramientas.size} productos en catálogo",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Lista de herramientas
            items(herramientas) { herramienta ->
                HerramientaCard(herramienta = herramienta,
                    onArrendarHerramienta = onArrendarHerramienta)
            }

            // Pie de página
            item {
                Column(
                    modifier = Modifier
                        .padding(vertical = 24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Catálogo cargado desde colección de datos",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Total: ${herramientas.size} herramientas",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}


// 3. Función REUTILIZABLE para mostrar cualquier herramienta
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
            // Nombre
            Text(
                text = herramienta.nombre,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )

            // Descripción
            Text(
                text = herramienta.descripcion,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Fila para precio y stock
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Precio
                Text(
                    text = herramienta.precioFormateado(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                // Stock
                Text(
                    text = herramienta.stockFormateado(),
                    fontSize = 12.sp,
                    color = when {
                        herramienta.disponible && herramienta.stock > 0 -> Color(0xFF4CAF50)
                        herramienta.stock == 0 -> Color(0xFFF44336)
                        else -> Color(0xFFFF9800)
                    }
                )
            }

            // Rating (si tiene)
            if (herramienta.rating > 0.0f) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "⭐ ${herramienta.rating}",
                    fontSize = 11.sp,
                    color = Color(0xFFFF9800)
                )
            }

            // Botón Arrendar (SIEMPRE VISIBLE)
            Spacer(modifier = Modifier.height(12.dp))

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
                        "No disponible"
                    }
                )
            }

            // Enlace al fabricante (si tiene)
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
                        text = "🌐 Especificaciones técnicas",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}