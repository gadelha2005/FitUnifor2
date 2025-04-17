package com.example.fitunifor.administrador.fichas

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.fitunifor.R

class AdicionarExercicioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_exercicio)

        val icon_back = findViewById<ImageView>(R.id.icon_back_novo_treino_aluno)

        icon_back.setOnClickListener {
            navigateBackToNovoTreinoAluno()
        }

    }
    private fun navigateBackToNovoTreinoAluno(){
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}