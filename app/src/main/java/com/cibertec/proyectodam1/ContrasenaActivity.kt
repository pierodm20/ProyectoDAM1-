package com.cibertec.proyectodam1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth

class ContrasenaActivity : AppCompatActivity() {
    private lateinit var txtInCorreo: TextInputEditText
    private lateinit var myvar: String;
    private lateinit var btnEnviarCorreo: MaterialButton
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contrasena)

        txtInCorreo = findViewById<TextInputEditText>(R.id.txtInCorreo)
        btnEnviarCorreo = findViewById<MaterialButton>(R.id.btnEnviarCorreo)
        auth = FirebaseAuth.getInstance()

        btnEnviarCorreo.setOnClickListener {
            val correo = txtInCorreo.text.toString()

            if(correo.isBlank()){
                mostrarMensaje("Porfavor ingrese un correo valido")
                return@setOnClickListener
            }

            if (validar(correo)){
                recuperarContrasenia(correo)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun recuperarContrasenia(correo: String){
        auth.sendPasswordResetEmail(correo)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mostrarMensaje("Correo de recuperación enviado. Porfavor revise su correo")
                    finish()
                } else {
                    val error = task.exception?.message ?: "Error desconocido"
                    mostrarMensaje("Error: ${error}")
                }
            }
    }

    fun mostrarMensaje(mensaje: String){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
    fun validar(correo: String): Boolean{
        return when{
            !correo.contains("@") || !correo.contains(".") -> {
                mostrarMensaje("Correo invalido")
                false
            }
            else -> true
        }
    }
}