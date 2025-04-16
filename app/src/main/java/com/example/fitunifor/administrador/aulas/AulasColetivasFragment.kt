package com.example.fitunifor.administrador.aulas

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.fitunifor.R
import com.example.fitunifor.databinding.FragmentAulasColetivasBinding

class AulasColetivasFragment : Fragment(R.layout.fragment_aulas_coletivas) {

    private var _binding: FragmentAulasColetivasBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAulasColetivasBinding.bind(view)

        setupListeners()
        setupChildFragment()
    }

    private fun setupListeners() {
        binding.buttonAdicionarAula.setOnClickListener {
            showNovaAulaDialog()
        }
    }

    private fun showNovaAulaDialog() {
        NovaAulaDialogFragment().apply {
            setListener(object : NovaAulaDialogFragment.AulaDialogListener {
                override fun onAulaSalva(aula: Aula) {
                    getAulaAdminFragment()?.adicionarAula(aula)
                }
            })
        }.show(childFragmentManager, "NovaAulaDialog")
    }

    private fun setupChildFragment() {
        if (childFragmentManager.findFragmentById(R.id.container_aula_admin) == null) {
            childFragmentManager.beginTransaction()
                .replace(R.id.container_aula_admin, AulaAdminFragment())
                .commit()
        }
    }

    private fun getAulaAdminFragment(): AulaAdminFragment? {
        return childFragmentManager.findFragmentById(R.id.container_aula_admin) as? AulaAdminFragment
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}