package com.example.fitunifor.administrador.aulas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitunifor.R
import com.example.fitunifor.databinding.FragmentAulaAdminBinding


class AulaAdminFragment : Fragment() {
    private lateinit var binding: FragmentAulaAdminBinding
    private lateinit var aulaAdapter: AulaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAulaAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        aulaAdapter = AulaAdapter(
            mutableListOf(), // Lista vazia inicial
            onEditarClick = { aula -> editarAula(aula) },
            onRemoverClick = { posicao -> removerAula(posicao) }
        )

        binding.recyclerAulas.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = aulaAdapter
        }
    }

    fun adicionarAula(aula: Aula) {
        aulaAdapter.adicionarAula(aula)
    }

    private fun editarAula(aula: Aula) {
        // Implemente a edição aqui (pode abrir o mesmo Dialog de adição)
    }

    private fun removerAula(posicao: Int) {
        aulaAdapter.removerAula(posicao)
    }
}