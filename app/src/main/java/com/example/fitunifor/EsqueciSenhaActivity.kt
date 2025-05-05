package com.example.fitunifor

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class EsqueciSenhaActivity : AppCompatActivity() {

    private lateinit var editEmail: EditText
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_esqueci_senha)

        // Inicializa Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        editEmail = findViewById(R.id.text2_email)

        // Botão Voltar ao Login
        findViewById<Button>(R.id.button_voltar_login).setOnClickListener {
            finish()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        // Botão Enviar para Email com validação
        findViewById<Button>(R.id.button_enviar_para_email).setOnClickListener {
            validarEmailESalvar()
        }
    }

    private fun validarEmailESalvar() {
        val email = editEmail.text.toString().trim()

        when {
            email.isEmpty() -> {
                editEmail.error = "Digite seu email"
                editEmail.requestFocus()
                showAlert("Campo obrigatório", "Por favor, preencha o email")
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                editEmail.error = "Email inválido"
                editEmail.requestFocus()
                showAlert("Email inválido", "Por favor, digite um email válido")
            }
            else -> {
                // Email válido, enviar email de redefinição
                enviarEmailRedefinicaoSenha(email)
            }
        }
    }

    private fun enviarEmailRedefinicaoSenha(email: String) {
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    showSuccessDialog(email)
                } else {
                    Toast.makeText(
                        this,
                        "Falha ao enviar email: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun showSuccessDialog(email: String) {
        AlertDialog.Builder(this)
            .setTitle("Email enviado")
            .setMessage("Enviamos um link para redefinir sua senha para o email $email. Por favor, verifique sua caixa de entrada.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
            .setCancelable(false)
            .create()
            .show()
    }

    private fun showAlert(title: String, message: String) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }
}