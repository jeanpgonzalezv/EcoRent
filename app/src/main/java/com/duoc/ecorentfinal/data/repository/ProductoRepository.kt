package com.duoc.ecorentfinal.data.repository

import com.duoc.ecorentfinal.data.dataBase.ProductoDao
import com.duoc.ecorentfinal.data.entities.ProductoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ProductoRepository(private val productoDao: ProductoDao) {

    // Obtener todos los productos disponibles
    fun getAllProductos(): Flow<List<ProductoEntity>> {
        return productoDao.getAllProductos()
    }

    // Obtener producto por ID
    suspend fun getProductoById(id: Long): ProductoEntity? {
        return withContext(Dispatchers.IO) {
            productoDao.getProductoById(id)
        }
    }

    // Obtener productos por categoría
    fun getProductosPorCategoria(categoria: String): Flow<List<ProductoEntity>> {
        return productoDao.getProductosPorCategoria(categoria)
    }

    // Insertar producto
    suspend fun insertProducto(producto: ProductoEntity): Long {
        return withContext(Dispatchers.IO) {
            productoDao.insertProducto(producto)
        }
    }

    // Actualizar producto
    suspend fun updateProducto(producto: ProductoEntity) {
        withContext(Dispatchers.IO) {
            productoDao.updateProducto(producto)
        }
    }

    // Eliminar producto
    suspend fun deleteProducto(producto: ProductoEntity) {
        withContext(Dispatchers.IO) {
            productoDao.deleteProducto(producto)
        }
    }

    // Reducir stock
    suspend fun reducirStock(productoId: Long, cantidad: Int = 1) {
        withContext(Dispatchers.IO) {
            productoDao.reducirStock(productoId, cantidad)
        }
    }
}