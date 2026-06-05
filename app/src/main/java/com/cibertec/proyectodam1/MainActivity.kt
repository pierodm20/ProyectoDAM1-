package com.cibertec.proyectodam1

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var tvRegistro: TextView
    private lateinit var tvContraseña: TextView
    private lateinit var btnIngresar: MaterialButton
    private lateinit var txtInContraseña: TextInputEditText
    private lateinit var txtInCorreo: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        tvRegistro = findViewById<TextView>(R.id.tvRegistro)
        tvContraseña = findViewById<TextView>(R.id.tvContraseña)
        btnIngresar = findViewById<MaterialButton>(R.id.btnIngresar)
        txtInContraseña = findViewById<TextInputEditText>(R.id.txtInContraseña)
        txtInCorreo = findViewById<TextInputEditText>(R.id.txtInCorreo)

        tvRegistro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        tvContraseña.setOnClickListener {
            val intent = Intent(this, ContrasenaActivity::class.java)
            startActivity(intent)
        }

        btnIngresar.setOnClickListener {
            val usuario = txtInCorreo.text.toString()
            val contraseña = txtInContraseña.text.toString()

            if (usuario.isNotEmpty() && contraseña.isNotEmpty()){
                if (usuario == "admin" && contraseña == "123"){
                    val intent = Intent(this, InicioActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, "Usuario o Contraseña incorrectos", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Ingrese usuario y o contraseña", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}