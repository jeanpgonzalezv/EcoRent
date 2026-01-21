package com.duoc.ecorentfinal.data.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.duoc.ecorentfinal.data.entities.ProductoEntity
import com.duoc.ecorentfinal.data.entities.UsuarioEntity
import com.duoc.ecorentfinal.data.entities.AlquilerEntity

@Database(
    entities = [
        ProductoEntity::class,
        UsuarioEntity::class,
        AlquilerEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class EcoRentDatabase : RoomDatabase() {

    abstract fun productoDao(): ProductoDao
    abstract fun usuarioDao(): UsuarioDao
    abstract fun alquilerDao(): AlquilerDao

    companion object {
        @Volatile
        private var INSTANCE: EcoRentDatabase? = null

        fun getDatabase(context: Context): EcoRentDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EcoRentDatabase::class.java,
                    "ecorent_database"
                )
                    .fallbackToDestructiveMigration()  // Borra y recrea si hay cambios
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}