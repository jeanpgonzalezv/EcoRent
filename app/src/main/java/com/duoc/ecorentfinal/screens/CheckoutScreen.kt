package com.duoc.ecorentfinal.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// NO importar viewModel aquí
import com.duoc.ecorentfinal.viewmodel.CarritoViewModel
import com.duoc.ecorentfinal.viewmodel.ItemCarrito
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    carritoViewModel: CarritoViewModel, // ← RECIBIR como parámetro (CAMBIAR ESTO)
    onNavigateBack: () -> Unit,
    onConfirmarPago: () -> Unit
) {
    println("💳💳💳 CHECKOUTSCREEN: Iniciando pantalla")
    println("💳💳💳 CHECKOUTSCREEN: ViewModel recibido - hashCode: ${carritoViewModel.hashCode()}")

    // Obtener los items DIRECTAMENTE del ViewModel recibido
    val items by carritoViewModel.itemsCarrito.collectAsState()
    val total by carritoViewModel.totalCarrito.collectAsState()

    // DEBUG para ver qué hay en el carrito
    LaunchedEffect(items) {
        println("📋 CHECKOUT: Items en carrito: ${items.size}")
        println("📋 CHECKOUT: ViewModel hashCode: ${carritoViewModel.hashCode()}")
        if (items.isNotEmpty()) {
            println("📋 CHECKOUT: Contenido del carrito:")
            items.forEachIndexed { index, item ->
                println("   ${index + 1}. ${item.nombre} - $${item.costoTotal}")
            }
        } else {
            println("📋 CHECKOUT: Carrito VACÍO")
            // Mostrar más información de debug
            println("📋 CHECKOUT: ¿El ViewModel es el correcto?")
        }
    }

    // Estado para los datos del formulario
    var nombre by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var tarjeta by remember { mutableStateOf("") }
    var fechaVencimiento by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var aceptaTerminos by remember { mutableStateOf(false) }

    val formatter = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Finalizar Compra") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                tonalElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Total a pagar:",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            formatter.format(total),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            // Validar que todos los campos estén completos
                            if (nombre.isNotBlank() && direccion.isNotBlank() &&
                                tarjeta.isNotBlank() && fechaVencimiento.isNotBlank() &&
                                cvv.isNotBlank() && aceptaTerminos) {

                                println("✅ CHECKOUT: Todos los campos válidos")
                                println("✅ CHECKOUT: Limpiando carrito...")

                                // LIMPIAR EL CARRITO AQUÍ
                                carritoViewModel.limpiarCarrito()

                                println("✅ CHECKOUT: Carrito limpiado, navegando a confirmación")

                                // Luego llamar a la función de navegación
                                onConfirmarPago()
                            } else {
                                println("⚠️ CHECKOUT: Campos incompletos")
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        ),
                        enabled = nombre.isNotBlank() && direccion.isNotBlank() &&
                                tarjeta.isNotBlank() && fechaVencimiento.isNotBlank() &&
                                cvv.isNotBlank() && aceptaTerminos && total > 0
                    ) {
                        Text(
                            "💳 Confirmar Pago",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            // Resumen del pedido - Usar items DIRECTAMENTE
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "📦 Resumen del Pedido (${items.size} items)",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )

                        // Mostrar el hashCode para debug
                        if (items.isEmpty()) {
                            Text(
                                "hash: ${carritoViewModel.hashCode().toString().takeLast(4)}",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (items.isEmpty()) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp)
                        ) {
                            Text(
                                "🚫 No hay items en el pedido",
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.error,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                "ViewModel: ${carritoViewModel.hashCode()}",
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Text(
                                "¿El carrito estaba vacío al llegar aquí?",
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                    } else {
                        items.forEachIndexed { index, item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            "${index + 1}. ${item.nombre}",
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(
                                            formatter.format(item.costoTotal),
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            "${item.dias} día" + if (item.dias > 1) "s" else "",
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                        Text(
                                            "${formatter.format(item.precioPorDia)}/día",
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Divider(modifier = Modifier.padding(vertical = 16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total:", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text(
                            formatter.format(total),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Información de envío
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(Icons.Default.LocationOn, contentDescription = null)
                        Text(
                            "📍 Información de Envío",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre completo") },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = MaterialTheme.shapes.medium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = direccion,
                        onValueChange = { direccion = it },
                        label = { Text("Dirección de envío") },
                        leadingIcon = {
                            Icon(Icons.Default.LocationOn, contentDescription = null)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = MaterialTheme.shapes.medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Información de pago
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "💳 Información de Pago",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = tarjeta,
                        onValueChange = { tarjeta = it },
                        label = { Text("Número de tarjeta") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        placeholder = { Text("1234 5678 9012 3456") },
                        shape = MaterialTheme.shapes.medium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedTextField(
                            value = fechaVencimiento,
                            onValueChange = { fechaVencimiento = it },
                            label = { Text("MM/AA") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            placeholder = { Text("12/25") },
                            shape = MaterialTheme.shapes.medium
                        )

                        OutlinedTextField(
                            value = cvv,
                            onValueChange = { cvv = it },
                            label = { Text("CVV") },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            placeholder = { Text("123") },
                            shape = MaterialTheme.shapes.medium
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "💡 Los datos de pago son de ejemplo. No se procesarán pagos reales.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Términos y condiciones
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = aceptaTerminos,
                        onCheckedChange = { aceptaTerminos = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.primary
                        )
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        "Acepto los términos y condiciones del servicio",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(80.dp)) // Espacio extra para el bottom bar
        }
    }
}