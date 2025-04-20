package com.example.fitunifor.administrador.fichas

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitunifor.R

class NovoTreinoAlunoActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExercicioNoTreinoAdapter
    private val exerciciosAdicionados = mutableListOf<Exercicio>()
    private var treinoEditando: Treino? = null

    companion object {
        const val REQUEST_CODE_ADICIONAR_EXERCICIO = 1001
        const val EXTRA_TREINO_EDICAO = "treino_edicao"
        const val EXTRA_TREINO_ATUALIZADO = "treino_atualizado"
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_novo_treino_aluno)

        // 1. Primeiro inicializa o RecyclerView e Adapter
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

        // 2. Depois verifica se está editando um treino
        treinoEditando = intent.getParcelableExtra(EXTRA_TREINO_EDICAO)
        if (treinoEditando != null) {
            carregarTreinoExistente(treinoEditando!!)
            findViewById<TextView>(R.id.text_salvar_treino).text = "Salvar"
            findViewById<TextView>(R.id.textView46).text = "Editar Treino"
        }

        // 3. Configura os listeners
        findViewById<TextView>(R.id.text_salvar_treino).setOnClickListener {
            salvarTreino()
        }

        findViewById<ImageView>(R.id.icon_back_gestao_treinos).setOnClickListener {
            navigateBackToGestaoTreinos()
        }

        findViewById<Button>(R.id.button_adicionar_exercicios_treino).setOnClickListener {
            navigateToAdicionarExercicio()
        }
    }

    private fun carregarTreinoExistente(treino: Treino) {
        // Garante que o adapter está inicializado
        if (!::adapter.isInitialized) {
            Log.e("NovoTreinoAluno", "Adapter não inicializado!")
            return
        }

        findViewById<EditText>(R.id.editTextText3).setText(treino.titulo)
        exerciciosAdicionados.clear()
        exerciciosAdicionados.addAll(treino.exercicios)
        adapter.notifyDataSetChanged()
    }

    private fun navigateBackToGestaoTreinos() {
        finish()
    }

    private fun navigateToAdicionarExercicio() {
        try {
            val intent = Intent(this, AdicionarExercicioActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADICIONAR_EXERCICIO)
        } catch (e: Exception) {
            Toast.makeText(this, "Não foi possível abrir a tela de exercícios: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
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

        if (treinoEditando != null) {
            // Modo edição - não precisa selecionar dia novamente
            val treinoAtualizado = treinoEditando!!.copy(
                titulo = titulo,
                exercicios = ArrayList(exerciciosAdicionados)
            )

            val resultIntent = Intent().apply {
                putExtra(EXTRA_TREINO_ATUALIZADO, treinoAtualizado)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        } else {
            // Modo criação - mostra diálogo para selecionar dia
            val dialogFragment = SelecionarDiaTreinoDialogFragment().apply {
                setOnDiaSelecionadoListener(object : SelecionarDiaTreinoDialogFragment.OnDiaSelecionadoListener {
                    override fun onDiaSelecionado(dia: String) {
                        val treinoNovo = Treino(
                            id = System.currentTimeMillis().toInt(),
                            titulo = titulo,
                            diaDaSemana = dia,
                            exercicios = ArrayList(exerciciosAdicionados)
                        )

                        val resultIntent = Intent().apply {
                            putExtra("treino_salvo", treinoNovo)
                        }
                        setResult(Activity.RESULT_OK, resultIntent)
                        finish()
                    }
                })
            }
            dialogFragment.show(supportFragmentManager, "SelecionarDiaDialog")
        }
    }
}