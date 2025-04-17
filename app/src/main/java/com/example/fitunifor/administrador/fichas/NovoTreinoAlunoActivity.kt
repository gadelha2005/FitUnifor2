package com.example.fitunifor.administrador.fichas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fitunifor.R

class NovoTreinoAlunoActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_treino_aluno)

        val icon_back = findViewById<ImageView>(R.id.icon_back_gestao_treinos)
        val button_adicionar_exercicios_treino = findViewById<Button>(R.id.button_adicionar_exercicios_treino)

        icon_back.setOnClickListener {
            navigateBackToGestaoTreinos()
        }

        button_adicionar_exercicios_treino.setOnClickListener {
            navigateToAdicionarExercicio()
        }
    }

    private fun navigateBackToGestaoTreinos(){
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private  fun navigateToAdicionarExercicio(){
        try {
            val intent = Intent(this, AdicionarExercicioActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        } catch (e: Exception) {
            Toast.makeText(this,
                "Não foi possível ir para a tela de ia\n${e.localizedMessage}",
                Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }
}

