package com.cibertec.proyectodam1

import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class RegistroActivity : AppCompatActivity() {
    private lateinit var txtInNombres: TextInputEditText
    private lateinit var txtInApellidos: TextInputEditText
    private lateinit var txtInDni: TextInputEditText
    private lateinit var txtInTelefono: TextInputEditText
    private lateinit var txtInCorreo: TextInputEditText
    private lateinit var txtInFecha: TextInputEditText
    private lateinit var txtInContraseniaReg: TextInputEditText
    private lateinit var txtInRepetirReg: TextInputEditText
    private lateinit var checkTerCond: CheckBox
    private lateinit var btnRegistrar: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro)

        txtInNombres = findViewById<TextInputEditText>(R.id.txtInNombres)
        txtInApellidos = findViewById<TextInputEditText>(R.id.txtInApellidos)
        txtInDni = findViewById<TextInputEditText>(R.id.txtInDni)
        txtInTelefono = findViewById<TextInputEditText>(R.id.txtInTelefono)
        txtInCorreo = findViewById<TextInputEditText>(R.id.txtInCorreo)
        txtInFecha = findViewById<TextInputEditText>(R.id.txtInFecha)
        txtInContraseniaReg = findViewById<TextInputEditText>(R.id.txtInContraseniaReg)
        txtInRepetirReg = findViewById<TextInputEditText>(R.id.txtInRepetirReg)
        checkTerCond = findViewById<CheckBox>(R.id.checkTerCond)
        btnRegistrar = findViewById<MaterialButton>(R.id.btnRegistrar)

        checkTerCond.setOnCheckedChangeListener { _, isChecked ->
            btnRegistrar.isEnabled = isChecked
        }

        btnRegistrar.setOnClickListener {
            val nombres = txtInNombres.text.toString()
            val apellidos = txtInApellidos.text.toString()
            val dni = txtInDni.text.toString()
            val telefono = txtInTelefono.text.toString()
            val correo = txtInCorreo.text.toString()
            val fecha = txtInFecha.text.toString()
            val contrasenia = txtInContraseniaReg.text.toString()
            val repetir = txtInRepetirReg.text.toString()

            if (nombres.isBlank() || apellidos.isBlank() || dni.isBlank() || telefono.isBlank() || correo.isBlank() || fecha.isBlank() || contrasenia.isBlank() || repetir.isBlank()){
                mostrarMensaje("Los campos no pueden estar vacios")
                return@setOnClickListener
            }
23
            val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val fechaLocalDate = try {
                LocalDate.parse(fecha, formato)
            } catch (e: Exception) {
                mostrarMensaje("Formato de fecha inválido. Usa dd/mm/yyyy")
                return@setOnClickListener // Detenemos la ejecución aquí
            }

            if (validarDatos(nombres, apellidos, dni, telefono, correo, fechaLocalDate, contrasenia, repetir, checkTerCond.isChecked)){
                registroUsuario(nombres, apellidos, dni, telefono, correo, fechaLocalDate, contrasenia)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun registroUsuario(nombres: String, apellidos: String, dni: String, telefono: String, correo: String, fecha: LocalDate, contrasenia: String){
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseDatabase.getInstance().reference

        auth.createUserWithEmailAndPassword(correo, contrasenia)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    auth.currentUser?.let { user ->
                        val uid = user.uid
                        val usuarioMap = mapOf(
                            "nombre" to nombres,
                            "apellido" to apellidos,
                            "dni" to dni,
                            "telefono" to telefono,
                            "correo" to correo,
                            "fecha" to fecha.toString()
                        )
                        db.child("usuarios").child(uid).setValue(usuarioMap)
                            .addOnSuccessListener {
                                val db = FirebaseFirestore.getInstance()
                                db.collection("usuarios").document(uid).set(usuarioMap)
                                    .addOnSuccessListener {
                                        irPantallaPrincipal()
                                    }
                                    .addOnFailureListener { e ->
                                        mostrarMensaje("Error: ${e.message}")
                                    }
                            }
                            .addOnFailureListener { e ->
                                mostrarMensaje("Error al registrar usuario: ${e.message}")
                            }
                    }
                }else{
                    mostrarMensaje("Error en el registro: ${task.exception?.message}")                }

            }
    }

    fun irPantallaPrincipal(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        mostrarMensaje("Usuario registrado correctamente")
        finish()
    }

    fun mostrarMensaje(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun validarDatos(nombres: String, apellidos: String, dni: String, telefono: String, correo: String, fecha: LocalDate, contrasenia: String, repetir:String, checkBox: Boolean): Boolean {
        return when {
            nombres.length < 3 -> {
                mostrarMensaje("El nombre debe tener al menos 3 caracteres")
                false
            }
            apellidos.length < 3 -> {
                mostrarMensaje("El apellido debe tener al menos 3 caracteres")
                false
            }
            dni.length != 8  -> {
                mostrarMensaje("El dni debe tener al 8 digitos")
                false
            }
            telefono.length > 20 -> {
                mostrarMensaje("El telefono debe tener menos 20 caracteres")
                false
            }
            !correo.contains("@") || !correo.contains(".") -> {
                mostrarMensaje("Correo inválido")
                false
            }
            contrasenia.length < 5 -> {
                mostrarMensaje("La contraseña debe tener al menos 5 caracteres")
                false
            }
            contrasenia != repetir -> {
                mostrarMensaje("Las contraseñas no coinciden")
                false
            }
            !checkBox ->{
                mostrarMensaje("Debes que aceptar los terminos y condiciones")
                false
            }
            // Validar que la fecha no sea futura (ejemplo de regla de negocio)
            fecha.isAfter(LocalDate.now()) -> {
                mostrarMensaje("La fecha no puede ser futura")
                false
            }
            else -> true
        }
    }
}