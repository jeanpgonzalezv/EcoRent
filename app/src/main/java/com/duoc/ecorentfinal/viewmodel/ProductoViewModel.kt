package com.duoc.ecorentfinal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duoc.ecorentfinal.data.model.Herramienta
import com.duoc.ecorentfinal.data.repository.HerramientasApiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductosViewModel : ViewModel() {
    private val repository = HerramientasApiRepository()

    sealed class ProductosState {
        object Loading : ProductosState()
        data class Success(val herramientas: List<Herramienta>) : ProductosState()
        data class Error(val mensaje: String) : ProductosState()
        object Empty : ProductosState()
    }

    private val _productosState = MutableStateFlow<ProductosState>(ProductosState.Empty)
    val productosState: StateFlow<ProductosState> = _productosState.asStateFlow()

    fun cargarHerramientas() {
        _productosState.value = ProductosState.Loading

        viewModelScope.launch {
            try {
                val herramientas = repository.getHerramientasFromApi()
                if (herramientas.isNotEmpty()) {
                    _productosState.value = ProductosState.Success(herramientas)
                } else {
                    _productosState.value = ProductosState.Empty
                }
            } catch (e: Exception) {
                _productosState.value = ProductosState.Error("Error: ${e.message}")
            }
        }
    }
}