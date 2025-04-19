package com.example.fitunifor.administrador.fichas

import android.os.Parcel
import android.os.Parcelable

data class Exercicio(
    val id: Int,
    val nome: String,
    val grupoMuscular: String,
    val imagemUrl: String? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString()
    )

    constructor(nome: String, grupoMuscular: String) : this(
        id = 0,
        nome = nome,
        grupoMuscular = grupoMuscular,
        imagemUrl = null
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nome)
        parcel.writeString(grupoMuscular)
        parcel.writeString(imagemUrl)
    }

    override fun describeContents(): Int = 0

    override fun toString(): String = "$nome ($grupoMuscular)"

    companion object CREATOR : Parcelable.Creator<Exercicio> {
        override fun createFromParcel(parcel: Parcel): Exercicio = Exercicio(parcel)
        override fun newArray(size: Int): Array<Exercicio?> = arrayOfNulls(size)
    }
}