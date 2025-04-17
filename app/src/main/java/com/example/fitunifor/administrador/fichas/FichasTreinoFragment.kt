package com.example.fitunifor.administrador.fichas

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.fitunifor.R

class FichasTreinoFragment : Fragment(R.layout.fragment_fichas_treino) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardAluno = view.findViewById<CardView>(R.id.card_aluno)

        cardAluno.setOnClickListener {
            // Criar a intenção para abrir a GestaoTreinosActivity
            val intent = Intent(activity, GestaoTreinosActivity::class.java)
            startActivity(intent)
        }
    }

}