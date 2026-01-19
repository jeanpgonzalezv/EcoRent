package com.duoc.ecorentfinal.data.dataBase

import androidx.room.*
import com.duoc.ecorentfinal.data.entities.ProductoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {

    // CREATE
    @Insert
    suspend fun insertProducto(producto: ProductoEntity): Long

    // READ
    @Query("SELECT * FROM productos WHERE disponible = 1 ORDER BY nombre")
    fun getAllProductos(): Flow<List<ProductoEntity>>

    @Query("SELECT * FROM productos WHERE id = :id")
    suspend fun getProductoById(id: Long): ProductoEntity?

    @Query("SELECT * FROM productos WHERE categoria = :categoria AND disponible = 1")
    fun getProductosPorCategoria(categoria: String): Flow<List<ProductoEntity>>

    // UPDATE
    @Update
    suspend fun updateProducto(producto: ProductoEntity)

    // DELETE
    @Delete
    suspend fun deleteProducto(producto: ProductoEntity)

    // OPERACIONES ESPECIALES
    @Query("UPDATE productos SET stock = stock - :cantidad WHERE id = :productoId")
    suspend fun reducirStock(productoId: Long, cantidad: Int = 1)

    @Query("UPDATE productos SET disponible = :disponible WHERE id = :productoId")
    suspend fun actualizarDisponibilidad(productoId: Long, disponible: Boolean)
}