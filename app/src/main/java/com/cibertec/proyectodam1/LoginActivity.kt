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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    private lateinit var tvRegistro: TextView
    private lateinit var tvContrasenia: TextView
    private lateinit var btnIngresar: MaterialButton
    private lateinit var txtInContrasenia: TextInputEditText
    private lateinit var txtInCorreo: TextInputEditText
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        tvRegistro = findViewById<TextView>(R.id.tvRegistro)
        tvContrasenia = findViewById<TextView>(R.id.tvContrasenia)
        btnIngresar = findViewById<MaterialButton>(R.id.btnIngresar)
        txtInContrasenia = findViewById<TextInputEditText>(R.id.txtInContrasenia)
        txtInCorreo = findViewById<TextInputEditText>(R.id.txtInCorreo)

        tvRegistro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        tvContrasenia.setOnClickListener {
            val intent = Intent(this, ContrasenaActivity::class.java)
            startActivity(intent)
        }

        btnIngresar.setOnClickListener {
            val correo = txtInCorreo.text.toString()
            val contrasenia = txtInContrasenia.text.toString()

            if(correo.isBlank() || contrasenia.isBlank()){
                mostrarMensaje("Los campos no pueden estar vacio")
                return@setOnClickListener
            }

            if (validar(correo, contrasenia)){
                loginUsuario(correo, contrasenia)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onStart() {
        super.onStart()
        val uid = auth.currentUser

        if (uid != null) {
            val intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    fun loginUsuario(correo: String, contrasenia: String){
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(correo, contrasenia)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    accesoLogin()
                }else{
                    mostrarMensaje("Error al iniciar sesión: ${task.exception?.message}")
                }
            }
    }

    fun validar(correo: String, contrasenia: String): Boolean{
        return when{
            !correo.contains("@") || !correo.contains(".") -> {
                mostrarMensaje("Correo invalido")
                false
            }
            contrasenia.length > 8 -> {
                mostrarMensaje("Contraseña tiene que tener 8 digitos")
                false
            }
            else -> true
        }
    }

    fun accesoLogin(){
        val intent = Intent(this, InicioActivity::class.java)
        startActivity(intent)
        mostrarMensaje("Acceso permitido")
        finish()
    }
    fun mostrarMensaje(mensaje: String){
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}