package com.example.fitunifor.administrador.fichas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitunifor.R

class NovoTreinoAlunoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExercicioNoTreinoAdapter
    private val exerciciosAdicionados = mutableListOf<Exercicio>()

    companion object {
        const val REQUEST_CODE_ADICIONAR_EXERCICIO = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_treino_aluno)

        val iconBack = findViewById<ImageView>(R.id.icon_back_gestao_treinos)
        val buttonAdicionarExercicios = findViewById<Button>(R.id.button_adicionar_exercicios_treino)
        val editTextTitulo = findViewById<EditText>(R.id.editTextText3)

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerViewExerciciosTreino)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ExercicioNoTreinoAdapter(
            exerciciosAdicionados,
            onRemoverExercicio = { exercicio ->
                exerciciosAdicionados.remove(exercicio)
                adapter.notifyDataSetChanged()
            },
            onAdicionarSerie = { exercicio ->
                exercicio.adicionarSerie()
                adapter.notifyDataSetChanged()
            }
        )
        recyclerView.adapter = adapter

        iconBack.setOnClickListener {
            navigateBackToGestaoTreinos()
        }

        buttonAdicionarExercicios.setOnClickListener {
            navigateToAdicionarExercicio()
        }
    }

    private fun navigateBackToGestaoTreinos() {
        finish()
    }

    private fun navigateToAdicionarExercicio() {
        try {
            val intent = Intent(this, AdicionarExercicioActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADICIONAR_EXERCICIO)
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Não foi possível abrir a tela de exercícios: ${e.localizedMessage}",
                Toast.LENGTH_LONG
            ).show()
            Log.e("NovoTreinoAluno", "Erro ao navegar", e)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_ADICIONAR_EXERCICIO && resultCode == Activity.RESULT_OK) {
            try {
                val novosExercicios = data?.getParcelableArrayListExtra<Exercicio>("exercicios_selecionados")

                novosExercicios?.let {
                    it.forEach { novoExercicio ->
                        if (!exerciciosAdicionados.any { existente -> existente.id == novoExercicio.id }) {
                            exerciciosAdicionados.add(novoExercicio)
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Erro ao carregar exercícios", Toast.LENGTH_SHORT).show()
                Log.e("NovoTreino", "Erro no onActivityResult", e)
            }
        }
    }

    private fun salvarTreino() {
        val titulo = findViewById<EditText>(R.id.editTextText3).text.toString()

        if (titulo.isEmpty()) {
            Toast.makeText(this, "Digite um título para o treino", Toast.LENGTH_SHORT).show()
            return
        }

        if (exerciciosAdicionados.isEmpty()) {
            Toast.makeText(this, "Adicione pelo menos um exercício", Toast.LENGTH_SHORT).show()
            return
        }

        // Implemente a lógica para salvar o treino aqui
        Toast.makeText(this, "Treino salvo com sucesso!", Toast.LENGTH_SHORT).show()
    }
}