package com.example.fitunifor.administrador.fichas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitunifor.R

class GestaoTreinosActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TreinoAdapter
    private val listaTreinos = mutableListOf<Treino>()

    companion object {
        private const val REQUEST_CODE_NOVO_TREINO = 1001
        private const val REQUEST_CODE_EDITAR_TREINO = 1002
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gestao_treinos)

        // Configuração do RecyclerView
        recyclerView = findViewById(R.id.recycler_view_treinos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = TreinoAdapter(
            listaTreinos,
            onEditarClick = { treino ->
                abrirTelaEdicaoTreino(treino)
            },
            onRemoverClick = { treino ->
                listaTreinos.remove(treino)
                adapter.notifyDataSetChanged()
            }
        )

        recyclerView.adapter = adapter

        // Botão de novo treino
        findViewById<CardView>(R.id.card_novo_treino).setOnClickListener {
            val intent = Intent(this, NovoTreinoAlunoActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_NOVO_TREINO)
        }

        // Botão voltar
        findViewById<ImageView>(R.id.icon_back_painel_administrativo).setOnClickListener {
            finish()
        }
    }

    // Na GestaoTreinosActivity
    private fun abrirTelaEdicaoTreino(treino: Treino) {
        Log.d("GestaoTreinos", "Iniciando edição do treino ID: ${treino.id}")
        try {
            val intent = Intent(this, NovoTreinoAlunoActivity::class.java).apply {
                putExtra(NovoTreinoAlunoActivity.EXTRA_TREINO_EDICAO, treino)
            }
            startActivityForResult(intent, REQUEST_CODE_EDITAR_TREINO)
        } catch (e: Exception) {
            Log.e("GestaoTreinos", "Erro ao abrir edição", e)
            Toast.makeText(this, "Erro ao abrir edição", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_CODE_NOVO_TREINO -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.getParcelableExtra<Treino>("treino_salvo")?.let { novoTreino ->
                        listaTreinos.add(novoTreino)
                        adapter.notifyItemInserted(listaTreinos.size - 1)
                    }
                }
            }
            REQUEST_CODE_EDITAR_TREINO -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.getParcelableExtra<Treino>(NovoTreinoAlunoActivity.EXTRA_TREINO_ATUALIZADO)?.let { treinoAtualizado ->
                        val index = listaTreinos.indexOfFirst { it.id == treinoAtualizado.id }
                        if (index != -1) {
                            listaTreinos[index] = treinoAtualizado
                            adapter.notifyItemChanged(index)
                        }
                    }
                }
            }
        }
    }
}