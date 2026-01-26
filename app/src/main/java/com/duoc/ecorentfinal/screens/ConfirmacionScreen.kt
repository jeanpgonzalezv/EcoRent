package com.duoc.ecorentfinal.screens



import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.duoc.ecorentfinal.viewmodel.CarritoViewModel
import kotlinx.coroutines.delay

@Composable
fun ConfirmacionScreen(
    onVolverAInicio: () -> Unit
) {
    val carritoViewModel: CarritoViewModel = viewModel()
    val items = carritoViewModel.itemsCarrito.collectAsState().value

    var showContent by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (showContent) 1f else 0.5f,
        animationSpec = tween(durationMillis = 500)
    )

    LaunchedEffect(Unit) {
        // Limpiar carrito después de 2 segundos
        delay(2000)
        carritoViewModel.limpiarCarrito()
        delay(300)
        showContent = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.CheckCircle,
            contentDescription = "Confirmado",
            modifier = Modifier
                .size(120.dp)
                .scale(scale),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            "✅ ¡Pago Confirmado!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Tu arriendo ha sido procesado exitosamente.",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(horizontal = 24.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            "Recibirás un correo con los detalles y confirmación.",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f),
            modifier = Modifier.padding(horizontal = 24.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        if (items.isNotEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("📋 Resumen:", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("${items.size} herramienta(s) arrendada(s)")
                    Text("Total pagado: $${"%.0f".format(items.sumOf { it.costoTotal })}")
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onVolverAInicio,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(Icons.Default.Home, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Volver al Inicio", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}