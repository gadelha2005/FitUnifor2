package com.example.fitunifor.administrador.fichas

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.fitunifor.R
import com.example.fitunifor.aluno.IAActivity

class GestaoTreinosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestao_treinos)

        val icon_back = findViewById<ImageView>(R.id.icon_back_painel_administrativo)
        val card_novo_treino = findViewById<CardView>(R.id.card_novo_treino)

        icon_back.setOnClickListener {
            navigateToBackPainelAdministrativo()
        }

        card_novo_treino.setOnClickListener {
            navigateToNovoTreinoAluno()
        }


    }
    private fun navigateToBackPainelAdministrativo(){
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private  fun navigateToNovoTreinoAluno(){
        try {
            val intent = Intent(this, NovoTreinoAlunoActivity::class.java).apply {
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