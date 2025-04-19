package com.example.fitunifor.administrador.fichas

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitunifor.R

class AdicionarExercicioActivity : AppCompatActivity() {

    private lateinit var adapter: ExercicioAdapter
    private val exerciciosSelecionados = mutableListOf<Exercicio>()
    private lateinit var btnAdicionar: CardView
    private lateinit var textQuantidade: TextView
    private lateinit var recyclerView: RecyclerView

    private val todosExercicios = mutableListOf(
        Exercicio(1, "Supino Reto", "Peito"),
        Exercicio(2, "Agachamento Livre", "Pernas"),
        Exercicio(3, "Barra Fixa", "Costas"),
        Exercicio(4, "Levantamento Terra", "Costas/Pernas"),
        Exercicio(5, "Rosca Direta", "Bíceps"),
        Exercicio(6, "Tríceps Corda", "Tríceps"),
        Exercicio(7, "Leg Press", "Pernas"),
        Exercicio(8, "Crucifixo", "Peito"),
        Exercicio(9, "Puxada Alta", "Costas")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_exercicio)

        // Inicializa as views
        btnAdicionar = findViewById(R.id.btn_adicionar_exercicios)
        textQuantidade = findViewById(R.id.text_quantidade_exercicios)
        recyclerView = findViewById(R.id.recyclerViewExercicios)

        setupRecyclerView()
        setupBusca()
        setupBotaoVoltar()
        setupBotaoAdicionar()
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = ExercicioAdapter(todosExercicios) { exercicio, isChecked ->
            if (isChecked) {
                exerciciosSelecionados.add(exercicio)
            } else {
                exerciciosSelecionados.remove(exercicio)
            }
            atualizarBotaoAdicionar()
        }

        recyclerView.adapter = adapter
    }

    private fun atualizarBotaoAdicionar() {
        val quantidade = exerciciosSelecionados.size
        if (quantidade > 0) {
            btnAdicionar.visibility = View.VISIBLE
            textQuantidade.text = resources.getQuantityString(
                R.plurals.texto_exercicios_selecionados,
                quantidade,
                quantidade
            )
        } else {
            btnAdicionar.visibility = View.GONE
        }
    }

    private fun setupBusca() {
        val editTextBusca = findViewById<EditText>(R.id.editTextText4)
        editTextBusca.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                filtrarExercicios(editTextBusca.text.toString())
                true
            } else {
                false
            }
        }
    }

    private fun filtrarExercicios(termo: String) {
        val listaFiltrada = if (termo.isEmpty()) {
            todosExercicios
        } else {
            todosExercicios.filter {
                it.nome.contains(termo, ignoreCase = true) ||
                        it.grupoMuscular.contains(termo, ignoreCase = true)
            }
        }
        adapter.atualizarLista(listaFiltrada)
    }

    private fun setupBotaoVoltar() {
        findViewById<ImageView>(R.id.icon_back_novo_treino_aluno).setOnClickListener {
            finish()
        }
    }

    private fun setupBotaoAdicionar() {
        btnAdicionar.setOnClickListener {
            val resultIntent = Intent().apply {
                putParcelableArrayListExtra("exercicios_selecionados", ArrayList(exerciciosSelecionados))
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}