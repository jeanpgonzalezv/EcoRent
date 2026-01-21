package com.duoc.ecorentfinal.data.dataBase

import androidx.room.*
import com.duoc.ecorentfinal.data.entities.AlquilerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlquilerDao {

    @Insert
    suspend fun insertAlquiler(alquiler: AlquilerEntity): Long

    @Query("SELECT * FROM alquileres WHERE usuarioId = :usuarioId ORDER BY fechaCreacion DESC")
    fun getAlquileresPorUsuario(usuarioId: Long): Flow<List<AlquilerEntity>>

    @Query("SELECT * FROM alquileres WHERE estado = :estado")
    fun getAlquileresPorEstado(estado: String): Flow<List<AlquilerEntity>>

    @Update
    suspend fun updateAlquiler(alquiler: AlquilerEntity)

    @Query("UPDATE alquileres SET estado = :estado WHERE id = :alquilerId")
    suspend fun actualizarEstado(alquilerId: Long, estado: String)
}