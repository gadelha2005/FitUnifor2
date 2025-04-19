package com.example.fitunifor.administrador.fichas

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.fitunifor.R

class SelecionarDiaTreinoDialogFragment : DialogFragment() {

    private var diaSelecionado: String? = null
    private lateinit var checkBoxes: List<CheckBox>
    private lateinit var botaoConfirmar: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_selecionar_dia_treino, container, false)

        // Mapeia os CheckBoxes
        val checkSegunda = view.findViewById<CheckBox>(R.id.check_box_segunda)
        val checkTerca = view.findViewById<CheckBox>(R.id.check_box_terca)
        val checkQuarta = view.findViewById<CheckBox>(R.id.check_box_quarta)
        val checkQuinta = view.findViewById<CheckBox>(R.id.check_box_quinta)
        val checkSexta = view.findViewById<CheckBox>(R.id.check_box_sexta)
        val checkSabado = view.findViewById<CheckBox>(R.id.check_box_sabado)
        val iconClose = view.findViewById<ImageView>(R.id.icon_close_selecionar_dia)

        botaoConfirmar = view.findViewById(R.id.botao_confirmar)
        botaoConfirmar.visibility = View.GONE // Oculta inicialmente

        checkBoxes = listOf(
            checkSegunda,
            checkTerca,
            checkQuarta,
            checkQuinta,
            checkSexta,
            checkSabado
        )

        val nomesDias = listOf("Segunda", "Terça", "Quarta", "Quinta", "Sexta", "Sábado")

        checkBoxes.forEachIndexed { index, checkBox ->
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // Desmarca os outros checkboxes
                    checkBoxes.forEachIndexed { i, otherCheckBox ->
                        if (i != index) otherCheckBox.isChecked = false
                    }
                    diaSelecionado = nomesDias[index]
                    botaoConfirmar.visibility = View.VISIBLE
                } else {
                    // Se desmarcar o selecionado, esconde o botão e reseta seleção
                    if (diaSelecionado == nomesDias[index]) {
                        diaSelecionado = null
                        botaoConfirmar.visibility = View.GONE
                    }
                }
            }
        }

        botaoConfirmar.setOnClickListener {
            diaSelecionado?.let {
                Toast.makeText(requireContext(), "Dia selecionado: $it", Toast.LENGTH_SHORT).show()
                dismiss()
                // Enviar o dia selecionado para a Activity
                (activity as? NovoTreinoAlunoActivity)?.let { activity ->
                    Toast.makeText(activity, "Dia $it selecionado para o treino", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Adiciona o comportamento de fechar ao clicar no ícone
        iconClose.setOnClickListener {
            dismiss() // Fecha o DialogFragment e retorna à tela anterior
        }

        return view
    }
}

