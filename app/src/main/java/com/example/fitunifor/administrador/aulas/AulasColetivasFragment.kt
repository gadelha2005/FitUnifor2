package com.example.fitunifor.administrador.aulas

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fitunifor.R
import com.example.fitunifor.databinding.FragmentAulasColetivasBinding

class AulasColetivasFragment : Fragment(R.layout.fragment_aulas_coletivas) {

    private var _binding: FragmentAulasColetivasBinding? = null
    private val binding get() = _binding!!
    private lateinit var aulaAdapter: AulaAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAulasColetivasBinding.bind(view)

        setupRecyclerView()
        setupListeners()
    }

    private fun setupRecyclerView() {
        aulaAdapter = AulaAdapter(
            mutableListOf(),
            onEditarClick = { aula -> editarAula(aula) },
            onRemoverClick = { posicao -> removerAula(posicao) }
        )

        binding.recyclerAulas.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = aulaAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupListeners() {
        binding.buttonAdicionarAula.setOnClickListener {
            showNovaAulaDialog()
        }

        binding.editTextBuscarAula.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtrarAulas(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun showNovaAulaDialog() {
        NovaAulaDialogFragment().apply {
            setListener(object : NovaAulaDialogFragment.AulaDialogListener {
                override fun onAulaSalva(aula: Aula) {
                    aulaAdapter.adicionarAula(aula)
                }
            })
        }.show(childFragmentManager, "NovaAulaDialog")
    }

    private fun filtrarAulas(texto: String) {
        // Implementação do filtro movida para o Adapter se necessário
        // Ou mantenha a lógica aqui conforme sua preferência
    }

    private fun editarAula(aula: Aula) {
        // Implemente a edição da aula
    }

    private fun removerAula(posicao: Int) {
        aulaAdapter.removerAula(posicao)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}