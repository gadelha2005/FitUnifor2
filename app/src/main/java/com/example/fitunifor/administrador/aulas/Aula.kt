package com.example.fitunifor.administrador.aulas

import java.util.UUID

data class Aula(
    val id: String = UUID.randomUUID().toString(), // ID único
    val nome: String,
    val professor: String,
    val diaSemana: String,
    val horario: String,
    val maxAlunos: Int,
    val alunosMatriculados: Int = 0
)
