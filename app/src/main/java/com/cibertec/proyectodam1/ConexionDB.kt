package com.cibertec.proyectodam1

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class ConexionDB(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // // nombre de la bd
        private const val DATABASE_NAME = "ReservaHoteles.db"
        private const val DATABASE_VERSION = 1
    }

    // Este método crea las tablas e inyecta la data por única vez al instalar la app
    override fun onCreate(db: SQLiteDatabase) {

        // 1. tabla de usuarios para el registro y login
        db.execSQL("""
            CREATE TABLE USUARIO (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                usuario TEXT NOT NULL,
                correo TEXT NOT NULL UNIQUE,
                contraseña TEXT NOT NULL
            )
        """.trimIndent())

        // 2. tabla hotel con los mismos campos del modelo
        db.execSQL("""
            CREATE TABLE HOTEL (
                id_hotel INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                ciudad TEXT NOT NULL,
                estrellas INTEGER NOT NULL,
                precioXnoche REAL NOT NULL,
                imagen TEXT
            )
        """.trimIndent())

        // 3. aca meto los 10 hoteles de frente para el recyclerview
        db.execSQL("INSERT INTO HOTEL (nombre, ciudad, estrellas, precioXnoche, imagen) VALUES ('JW Marriott Hotel Lima', 'Lima', 5, 250.00, 'https://images.unsplash.com/photo-1566073771259-6a8506099945?w=500&auto=format&fit=crop&q=60')")
        db.execSQL("INSERT INTO HOTEL (nombre, ciudad, estrellas, precioXnoche, imagen) VALUES ('Casa Andina Select Miraflores', 'Lima', 4, 130.00, 'https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?w=500&auto=format&fit=crop&q=60')")
        db.execSQL("INSERT INTO HOTEL (nombre, ciudad, estrellas, precioXnoche, imagen) VALUES ('Belmond Miraflores Park', 'Lima', 5, 320.00, 'https://images.unsplash.com/photo-1542314831-068cd1dbfeeb?w=500&auto=format&fit=crop&q=60')")
        db.execSQL("INSERT INTO HOTEL (nombre, ciudad, estrellas, precioXnoche, imagen) VALUES ('Palacio del Inka', 'Cusco', 5, 280.00, 'https://images.unsplash.com/photo-1571896349842-33c89424de2d?w=500&auto=format&fit=crop&q=60')")
        db.execSQL("INSERT INTO HOTEL (nombre, ciudad, estrellas, precioXnoche, imagen) VALUES ('Tambo del Inka Resort & Spa', 'Urubamba', 5, 310.00, 'https://images.unsplash.com/photo-1584132967334-10e028bd69f7?w=500&auto=format&fit=crop&q=60')")
        db.execSQL("INSERT INTO HOTEL (nombre, ciudad, estrellas, precioXnoche, imagen) VALUES ('Arawi Miraflores Prime', 'Lima', 4, 95.00, 'https://images.unsplash.com/photo-1590490360182-c33d57733427?w=500&auto=format&fit=crop&q=60')")
        db.execSQL("INSERT INTO HOTEL (nombre, ciudad, estrellas, precioXnoche, imagen) VALUES ('Hotel Costa del Sol Wyndham', 'Arequipa', 4, 110.00, 'https://images.unsplash.com/photo-1551882547-ff40c63fe5fa?w=500&auto=format&fit=crop&q=60')")
        db.execSQL("INSERT INTO HOTEL (nombre, ciudad, estrellas, precioXnoche, imagen) VALUES ('Aranwa Cusco Boutique Hotel', 'Cusco', 5, 210.00, 'https://images.unsplash.com/photo-1611892440504-42a792e24d32?w=500&auto=format&fit=crop&q=60')")
        db.execSQL("INSERT INTO HOTEL (nombre, ciudad, estrellas, precioXnoche, imagen) VALUES ('Ibis Larco Miraflores', 'Lima', 3, 65.00, 'https://images.unsplash.com/photo-1618773928121-c32242e63f39?w=500&auto=format&fit=crop&q=60')")
        db.execSQL("INSERT INTO HOTEL (nombre, ciudad, estrellas, precioXnoche, imagen) VALUES ('DoubleTree by Hilton', 'Ica', 4, 145.00, 'https://images.unsplash.com/photo-1445019980597-93fa8acb246c?w=500&auto=format&fit=crop&q=60')")
    }

    // por si se actualiza la bd o algo
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS USUARIO")
        db.execSQL("DROP TABLE IF EXISTS HOTEL")
        onCreate(db)
    }
}